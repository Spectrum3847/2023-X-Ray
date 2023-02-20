package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import java.text.DecimalFormat;

public class Vision extends SubsystemBase {
    public PhotonVision photonVision;
    public Pose2d botPose; // TODO: this may need to be estimated pose instead of botpose L:113

    private Pose3d botPose3d;
    private Pair<Pose3d, Double> photonVisionPose;

    // testing
    private final DecimalFormat df = new DecimalFormat();

    public Vision() {
        setName("Vision");
        botPose = new Pose2d(0, 0, new Rotation2d(0));
        botPose3d = new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0));
        LimelightHelpers.setLEDMode_ForceOff(null);

        /* PhotonVision Setup -- uncomment if running PhotonVision*/
        // photonVision = new PhotonVision();

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    @Override
    public void periodic() {
        // System.out.println(getThetaToHybrid(0));

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
            botPose = botPose3d.toPose2d();
            /* Adding Limelight estimate to pose if within 1 meter of odometry*/
            if (isValidPose(Robot.vision.botPose)) {
                Robot.pose.addVisionMeasurement(botPose, getTimestampSeconds(latency));
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
     * @param hybridSpot 0-8 representing the 9 different hybrid spots for launching cubes to hybrid
     *     nodes
     * @return angle between robot heading and hybrid spot in degrees
     */
    public double getThetaToHybrid(int hybridSpot) {
        if (botPose.getX() == 0 && botPose.getY() == 0) {
            DriverStation.reportWarning(
                    "Vision cannot localize! Move camera in view of a tag", false);
            return 0;
        }
        Pose2d hybridPose = VisionConfig.hybridSpots[hybridSpot];
        return botPose.relativeTo(hybridPose).getRotation().getDegrees(); //+ Robot.swerve.getHeading().getDegrees()?
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

    /**
     * Comparing vision pose against odometry pose. Does not account for difference in rotation.
     * Will return false vision if it sees no targets or if the vision estimated pose is too far
     * from the odometry estimate
     *
     * @return whether or not pose should be added to estimate or not
     */
    public boolean isValidPose(Pose2d pose) {
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
                && (Math.abs(pose.getY() - odometryPose.getY()) <= 1);
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
        SmartDashboard.putString("EstimatedPoseX", df.format(Robot.pose.getLocation().getX()));
        SmartDashboard.putString("EstimatedPoseY", df.format(Robot.pose.getLocation().getY()));
        SmartDashboard.putString(
                "EstimatedPoseTheta", df.format(Robot.pose.getHeading().getDegrees()));
        SmartDashboard.putString(
                "Odometry X", df.format(Robot.swerve.odometry.getPoseMeters().getX()));
        SmartDashboard.putString(
                "Odometry Y", df.format(Robot.swerve.odometry.getPoseMeters().getY()));
        SmartDashboard.putString(
                "Odometry Theta",
                df.format(Robot.swerve.odometry.getPoseMeters().getRotation().getDegrees()));
    }
}
