package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.vision.LimelightHelpers.LimelightTarget_Fiducial;
import java.text.DecimalFormat;

public class Vision extends SubsystemBase {
    public PhotonVision photonVision;
    public Pose2d botPose;
    public boolean visionIntegrated, visionConnected = false;
    /** For LEDs */
    public boolean poseOverriden = false;
    /** For Pilot Gamepad */
    public boolean canUseAutoPilot = false;

    public double horizontalOffset, verticalOffset;

    private Pose3d botPose3d; // Uses the limelight rotation instead of the gyro rotation
    private Pair<Pose3d, Double> photonVisionPose;
    private int targetSeenCount;
    private boolean targetSeen, visionStarted, initialized = false;

    private LimelightHelpers.LimelightResults jsonResults;

    // testing
    private final DecimalFormat df = new DecimalFormat();

    public Vision() {
        setName("Vision");
        botPose = new Pose2d(0, 0, new Rotation2d(Units.degreesToRadians(0)));
        botPose3d = new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0));
        targetSeenCount = 0;
        horizontalOffset = 0;
        verticalOffset = 0;

        LimelightHelpers.setLEDMode_ForceOff(null);

        /* PhotonVision Setup -- uncomment if running PhotonVision*/
        // photonVision = new PhotonVision();

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    @Override
    public void periodic() {
        /* update feed status by looking for an empty json */
        visionConnected =
                !NetworkTableInstance.getDefault()
                        .getTable("limelight")
                        .getEntry("json")
                        .getString("")
                        .equals("");
        checkTargetHistory();
        jsonResults = LimelightHelpers.getLatestResults("");
        horizontalOffset = LimelightHelpers.getTX("");
        verticalOffset = LimelightHelpers.getTY("");
        // this method can call update() if vision pose estimation needs to be updated in
        // Vision.java
    }

    /**
     * Vision Pose Estimation
     *
     * <p>Limelight pose logic:
     *
     * <p>Sets odometry pose to be vision estimate at the start of {@link Robot#teleopInit} and
     * {@link Robot#disabledInit} so odometry has correct starting pose. Will not override odometry
     * with vision if limelight does not see targets. Adds vision estimate to pose estimator using
     * standard deviation values if 1) odometry has been overridden by vision at least once and 2)
     * vision estimate is within 1 meter of odometry
     */
    public void update() {
        /* Limelight Pose Estimation Retrieval */
        double latency = 0;
        double[] poseArray = LimelightHelpers.getLimelightNTDoubleArray(null, "botpose");

        if (poseArray.length > 0) {
            latency =
                    (DriverStation.getAlliance() == Alliance.Blue)
                            ? LimelightHelpers.getBotPose_wpiBlue(null)[6]
                            : LimelightHelpers.getBotPose_wpiRed(null)[
                                    6]; // may need to add LimelightHelpers json parsing delay?
            botPose3d = chooseAlliance();
            botPose = toPose2d(botPose3d);
            /* Adding Limelight estimate if in teleop enabled */
            if (DriverStation.isTeleopEnabled()) {
                if (visionAccurate()) {
                    // if (!FollowOnTheFlyPath.OTF) poseOverriden = true;
                    canUseAutoPilot = true;
                    // } else if (isEstimateReady(botPose) && FollowOnTheFlyPath.OTF) {
                    // this can't be done in the command itself because of how addVisionMeasurement
                    // is called internally
                    Robot.pose.addVisionMeasurement(botPose, latency);
                    poseOverriden = false;
                    canUseAutoPilot = true;
                } else {
                    poseOverriden = false;
                    // if (!FollowOnTheFlyPath.OTF) canUseAutoPilot = false;
                }
            } else {
                poseOverriden = false;
                canUseAutoPilot = false;
            }
        }

        /* PhotonVision Pose Estimation Retrieval */
        if (photonVision != null) {
            photonVision.update();
            photonVisionPose = photonVision.currentPose;
            Pose2d photonVisionPose2d = photonVisionPose.getFirst().toPose2d();
            /* Adding PhotonVision estimate to pose */
            if (isValidPose(photonVisionPose2d)) {
                Robot.pose.addVisionMeasurement(
                        photonVisionPose2d,
                        getTimestampSeconds(photonVisionPose.getSecond().doubleValue()));
            }
        }
        printDebug(poseArray);
    }

    /**
     * REQUIRES ACCURATE POSE ESTIMATION. Uses trigonometric functions to calculate the angle
     * between the robot heading and the angle required to face the hybrid spot. Will return 0 if
     * the robot cannot see an apriltag.
     *
     * @param hybridSpot 0-8 representing the 9 different hybrid spots for launching cubes to hybrid
     *     nodes
     * @return angle between robot heading and hybrid spot in degrees
     */
    public double getThetaToHybrid(int hybridSpot) {
        if (botPose.getX() == 0 && botPose.getY() == 0) {
            DriverStation.reportWarning(
                    "Vision cannot localize! Move camera in view of a tag", false);
        }
        Transform2d transform = getTransformToHybrid(hybridSpot);
        double hyp = Math.hypot(transform.getX(), transform.getY());
        double beta = Math.toDegrees(Math.asin(transform.getX() / hyp));
        // double headingInScope; -- may have to get rotation in scope of -180 to 180 if using gryo
        double omega = Robot.pose.getEstimatedPose().getRotation().getDegrees() + 90;
        double theta = 360 - (omega + beta);
        /* if theta is greater than 360 subtract 360 so you dont turn over a full rotation */
        if (theta > 360) {
            theta -= 360;
            System.out.println("needed new theta: " + theta);
        }

        aimingPrintDebug(transform, hyp, beta, omega, theta);
        System.out.println("Aiming at node " + hybridSpot);

        return theta
                - 25; // this is the predictable offset behind the chargestation. The error seems to
        // be predictable probably meaning the trig is wrong
    }

    /** Resets estimated pose to vision pose */
    public void resetEstimatedPose() {
        Robot.pose.resetPoseEstimate(botPose);
    }

    /** @return if vision should be trusted more than estimated pose */
    public boolean visionAccurate() {
        return isValidPose(botPose) && (isInMap() || multipleTargetsInView());
    }

    public boolean isInMap() {
        return ((botPose.getX() > 1.8 && botPose.getX() < 2.5)
                && (botPose.getY() > 0.1 && botPose.getY() < 5.49));
    }

    /**
     * Helper function for {@link Vision#getThetaToHybrid}
     *
     * @param hybridSpot 0-8 representing the 9 different hybrid spots for launching cubes to hybrid
     *     nodes
     * @return Transform2d representing the x and y distance components between the robot and the
     *     hybrid spot
     */
    private Transform2d getTransformToHybrid(int hybridSpot) {
        Pose2d hybridPose = VisionConfig.hybridSpots[hybridSpot];
        return Robot.pose.getEstimatedPose().minus(hybridPose);
    }

    /** @return whether the camera sees multiple tags or not */
    public boolean multipleTargetsInView() {
        if (jsonResults == null) {
            return false;
        }
        LimelightTarget_Fiducial[] tags = jsonResults.targetingResults.targets_Fiducials;
        if (tags.length > 1) {
            return true;
        }
        return false;
    }

    public double getDistanceToTarget() {
        double angleToGoal = Units.degreesToRadians(VisionConfig.limelightAngle + verticalOffset);
        return (VisionConfig.tagHeight - VisionConfig.limelightLensHeight) / Math.tan(angleToGoal);
    }

    /**
     * Returns the corresponding limelight pose for the alliance in DriverStation.java
     *
     * @return Pose3d corresponding to blue or red alliance
     */
    public Pose3d chooseAlliance() {
        if (DriverStation.getAlliance() == Alliance.Blue) {
            return LimelightHelpers.getBotPose3d_wpiBlue(null);
        } else if (DriverStation.getAlliance() == Alliance.Red) {
            return LimelightHelpers.getBotPose3d_wpiRed(null);
        }
        DriverStation.reportWarning("Invalid Team", false);
        return null;
    }

    /** @return whether or not vision sees a tag */
    public boolean isValidPose(Pose2d pose) {
        /* Disregard Vision if there are no targets in view */
        if (!LimelightHelpers.getTV(null)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Comparing vision pose against odometry pose. Does not account for difference in rotation.
     * Will return false vision if it sees no targets or if the vision estimated pose is too far
     * from the odometry estimate
     *
     * @return whether or not pose should be added to estimate or not
     */
    public boolean isEstimateReady(Pose2d pose) {
        /* Disregard Vision if there are no targets in view */
        if (!LimelightHelpers.getTV(null)) {
            return false;
        }

        /* Disregard Vision if odometry has not been set to vision pose yet in teleopInit*/
        Pose2d odometryPose = Robot.swerve.getPoseMeters();
        if (odometryPose.getX() <= 0.3
                && odometryPose.getY() <= 0.3
                && odometryPose.getRotation().getDegrees() <= 1) {
            return false;
        }
        return (Math.abs(pose.getX() - odometryPose.getX()) <= 1)
                && (Math.abs(pose.getY() - odometryPose.getY())
                        <= 1); // this can be tuned to find a threshold that helps us remove
        // jumping
        // vision poses
    }

    /**
     * Converts a vision pose3d object to a pose2d object Also replaces the rotational component to
     * be the gyro rotation as this stays consistent throughout the match and does not need to be
     * overriden by vision
     *
     * @param pose3d vision pose3d
     * @return modified pose2d
     */
    public Pose2d toPose2d(Pose3d pose3d) {
        Pose2d pose2d = botPose3d.toPose2d();
        return new Pose2d(pose2d.getTranslation(), Robot.pose.getHeading());
    }

    /**
     * Gets the camera capture time in seconds.
     *
     * @param latencyMillis the latency of the camera in milliseconds
     * @return the camera capture time in seconds
     */
    public double getTimestampSeconds(double latencyMillis) {
        return Timer.getFPGATimestamp() - (latencyMillis / 1000d);
    }

    /**
     * Creates Pose3d object from raw limelight values sent through NetworkTables
     *
     * @param cameraConfig the camera config
     * @return the camera pair
     */
    public Pose3d createBotPose3d(double[] values) {
        return new Pose3d(
                new Translation3d(values[0], values[1], values[2]),
                new Rotation3d(
                        Units.degreesToRadians(values[3]),
                        Units.degreesToRadians(values[4]),
                        Units.degreesToRadians(values[5])));
    }

    /**
     * Gets if limelight has seen a target at least once. Attempts to disregard erroneous targets by
     * checking how many loops the target has been seen in
     *
     * @return true if limelight has seen a valid target at least once
     */
    public void checkTargetHistory() {
        // may need to use a sepearate count to avoid a consecutive loop check
        if (LimelightHelpers.getTV(null)) {
            targetSeenCount++;
        } else {
            targetSeenCount = 0;
        }
        targetSeen = targetSeenCount > 2; // has been seen for 3 loops
    }

    public double getHorizontalOffset() {
        return horizontalOffset;
    }

    public double getVerticalOffset() {
        return verticalOffset;
    }

    public double getClosestTagID() {
        return LimelightHelpers.getFiducialID("");
    }

    /**
     * Prints the vision, estimated, and odometry pose to SmartDashboard
     *
     * @param values the array of limelight raw values
     */
    public void printDebug(double[] poseArray) {
        if (poseArray.length > 0) {
            SmartDashboard.putString("LimelightX", df.format(botPose3d.getTranslation().getX()));
            SmartDashboard.putString("LimelightY", df.format(botPose3d.getTranslation().getY()));
            SmartDashboard.putString("LimelightZ", df.format(botPose3d.getTranslation().getZ()));
            SmartDashboard.putString(
                    "LimelightRoll",
                    df.format(Units.radiansToDegrees(botPose3d.getRotation().getX())));
            SmartDashboard.putString(
                    "LimelightPitch",
                    df.format(Units.radiansToDegrees(botPose3d.getRotation().getY())));
            SmartDashboard.putString(
                    "LimelightYaw",
                    df.format(Units.radiansToDegrees(botPose3d.getRotation().getZ())));
        }
        SmartDashboard.putString("EstimatedPoseX", df.format(Robot.pose.getEstimatedPose().getX()));
        SmartDashboard.putString("EstimatedPoseY", df.format(Robot.pose.getEstimatedPose().getY()));
        SmartDashboard.putString(
                "EstimatedPoseTheta", df.format(Robot.pose.getHeading().getDegrees()));
    }

    /**
     * Prints useful debug information for theta aiming calculations
     *
     * @param transform
     * @param hyp
     * @param beta
     * @param omega
     * @param theta
     */
    private void aimingPrintDebug(
            Transform2d transform, double hyp, double beta, double omega, double theta) {
        System.out.println(
                " pose theta: "
                        + df.format(Robot.pose.getEstimatedPose().getRotation().getDegrees()));
        System.out.print(
                "transform x: "
                        + df.format(transform.getX())
                        + " y: "
                        + df.format(transform.getY()));
        System.out.print(" hypotenuse: " + df.format(hyp));
        System.out.print(" beta: " + df.format(beta));
        System.out.print(" omega: " + df.format(omega));
        System.out.println(" theta: " + df.format(theta));
        if (theta > 360) {
            theta -= 360;
            System.out.println(" needed new theta: " + df.format(theta));
        }
    }
}
