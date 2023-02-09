package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import java.text.DecimalFormat;

public class Vision extends SubsystemBase {
    public PhotonVision photonVision;
    public Pose2d botPose;

    private NetworkTable table;
    private NetworkTableEntry tx, ty, ta, tl;
    private DoubleArraySubscriber poseSub;
    private Pose3d botPose3d;
    private Pair<Pose3d, Double> photonVisionPose;
    private boolean allianceColor = true; // temporary solution -- true is blue || false is red

    // testing
    private final DecimalFormat df = new DecimalFormat();

    
    public Vision() {
        setName("Vision");
        botPose = new Pose2d(0, 0, new Rotation2d(0));
        botPose3d = new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0));
        table = NetworkTableInstance.getDefault().getTable("limelight");
        /* Creating bot pose sub using set alliance color */
        poseSub = chooseAlliance().subscribe(new double[] {});
        table.getEntry("ledMode")
                .setValue(0); // 0 will use the LED Mode set in the pipeline || 1 is force off

        /* Limelight NetworkTable Retrieval */
        tx = table.getEntry("tx"); // offset from camera in degrees
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tl = table.getEntry("tl");

        /* PhotonVision Setup -- uncomment if running PhotonVision*/
        // photonVision = new PhotonVision();

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    @Override
    public void periodic() {
        /* Limelight Pose Estimation
         *
         * Current logic:
         * Sets odometry pose to be vision estimate at the start in teleopInit and disabledInit so odometry has correct starting pose
         * Will not override odometry with vision if limelight does not see targets (vision thinks it's at origin which doesn't help us)
         * Will add vision estimate to pose estimator using standard deviation values if 1. odometry has been overridden by vision at least once and 2. vision estimate is within 1 meter of odometry
         */
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);
        double latency = tl.getDouble(0.0);

        double[] subbedPose = poseSub.get();
        if (subbedPose.length > 0) {
            SmartDashboard.putString("LimelightX", df.format(subbedPose[0]));
            SmartDashboard.putString("LimelightY", df.format(subbedPose[1]));
            SmartDashboard.putString("LimelightZ", df.format(subbedPose[2]));
            SmartDashboard.putString("LimelightRoll", df.format(subbedPose[3]));
            SmartDashboard.putString("LimelightPitch", df.format(subbedPose[4]));
            SmartDashboard.putString("LimelightYaw", df.format(subbedPose[5]));

            /* Creating Transform3d object from raw values*/
            botPose3d =
                    new Pose3d(
                            new Translation3d(subbedPose[0], subbedPose[1], subbedPose[2]),
                            new Rotation3d(
                                    Units.degreesToRadians(subbedPose[3]),
                                    Units.degreesToRadians(subbedPose[4]),
                                    Units.degreesToRadians(subbedPose[5])));
            botPose = botPose3d.toPose2d();
            /* Adding Limelight estimate to pose if within 1 meter of odometry*/
            if (isValidPose(botPose)) {
                Robot.pose.addVisionMeasurement(botPose, getTimestampSeconds(latency));
            }
        }

        SmartDashboard.putString("tagX", df.format(x));
        SmartDashboard.putString("tagY", df.format(y));
        SmartDashboard.putString("tagArea", df.format(area));

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

        // testing || printing estimatedPose to smartDashboard
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

    /**
     * Returns the corresponding limelight pose for the current alliance color set in Vision.java
     *
     * @return NetworkTableEntry either botpose_wpiblue (blue driverstation origin) or
     *     botpose_wpired (red driverstation origin)
     */
    public DoubleArrayTopic chooseAlliance() {
        if (allianceColor) {
            return table.getDoubleArrayTopic("botpose_wpiblue");
        } else {
            return table.getDoubleArrayTopic("botpose_wpired");
        }
    }

    /**
     * Comparing vision pose against odometry pose. Does not account for difference in rotation.
     *
     * @return whether or not the vision estimated pose is within 1 meter of the odometry estimated
     *     pose
     */
    public boolean isValidPose(Pose2d pose) {
        Pose2d odometryPose = Robot.swerve.getPoseMeters();
        /* Disregard Vision if odometry has not been set to vision pose yet in teleopInit*/
        if (odometryPose.getX() <= 0.3
                && odometryPose.getY() <= 0.3
                && odometryPose.getRotation().getDegrees() <= 1) {
            return false;
        } else {
            return (Math.abs(pose.getX() - odometryPose.getX()) <= 1)
                    && (Math.abs(pose.getY() - odometryPose.getY()) <= 1);
        }
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

    public void printDebug() {
        RobotTelemetry.print(
                poseSub.getTopic().toString()
                        + ": \n\tX: "
                        + botPose3d.getX()
                        + " || Y: "
                        + botPose3d.getY()
                        + " || Z: "
                        + botPose3d.getZ()
                        + " || Roll: "
                        + botPose3d.getRotation().getX()
                        + " || Pitch: "
                        + botPose3d.getRotation().getY()
                        + " || Yaw: "
                        + botPose3d.getRotation().getZ());
    }
}
