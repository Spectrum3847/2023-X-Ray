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
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import java.text.DecimalFormat;

public class Vision extends SubsystemBase {
    public PhotonVision photonVision;
    public Pose2d botPose;

    private NetworkTable table;
    private DoubleArraySubscriber poseSub;
    private Pose3d botPose3d;
    private Pair<Pose3d, Double> photonVisionPose;

    // testing
    private final DecimalFormat df = new DecimalFormat();

    public Vision() {
        setName("Vision");
        botPose = new Pose2d(0, 0, new Rotation2d(0));
        botPose3d = new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0));
        table = NetworkTableInstance.getDefault().getTable("limelight");
        /* Creating bot pose sub using set alliance color */
        poseSub = chooseAlliance().subscribe(new double[] {});
        table.getEntry("ledMode").setValue(0);

        /* PhotonVision Setup -- uncomment if running PhotonVision*/
        // photonVision = new PhotonVision();

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    @Override
    public void periodic() {
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
        double latency = table.getEntry("tl").getDouble(0);
        double[] subbedPose = poseSub.get();
        if (subbedPose.length > 0) {
            botPose3d = createBotPose3d(subbedPose);
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

        printDebug(subbedPose);
    }

    /**
     * Creates Pose3d object from raw limelight values sent through NetworkTables
     *
     * @return Pose3d object representing the robot's pose
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
     * Returns the corresponding limelight pose for the current alliance color set in Vision.java
     *
     * @return NetworkTableEntry either botpose_wpiblue (blue driverstation origin) or
     *     botpose_wpired (red driverstation origin)
     */
    public DoubleArrayTopic chooseAlliance() {
        if (DriverStation.getAlliance() == Alliance.Blue) {
            return table.getDoubleArrayTopic("botpose_wpiblue");
        } else if (DriverStation.getAlliance() == Alliance.Red) {
            return table.getDoubleArrayTopic("botpose_wpired");
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
        if (table.getEntry("tv").getDouble(0.0) != 1) {
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
     * Prints the vision, estimated, and odometry pose to SmartDashboard
     *
     * @param values the array of limelight raw values
     */
    public void printDebug(double[] values) {
        if (values.length == 0) return;
        SmartDashboard.putString("LimelightX", df.format(values[0]));
        SmartDashboard.putString("LimelightY", df.format(values[1]));
        SmartDashboard.putString("LimelightZ", df.format(values[2]));
        SmartDashboard.putString("LimelightRoll", df.format(values[3]));
        SmartDashboard.putString("LimelightPitch", df.format(values[4]));
        SmartDashboard.putString("LimelightYaw", df.format(values[5]));

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
