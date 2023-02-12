package frc.robot.pose;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.swerve.SwerveConfig;

/** Reports our expected, desired, and actual poses to dashboards */
public class Pose extends SubsystemBase {
    PoseConfig config;
    PoseTelemetry telemetry;
    Pose2d odometryPose = new Pose2d();
    Pose2d desiredPose = new Pose2d();
    Pose2d estimatePose = new Pose2d();

    private final SwerveDrivePoseEstimator poseEstimator;

    public Pose() {
        config = new PoseConfig();
        telemetry = new PoseTelemetry(this);

        poseEstimator =
                new SwerveDrivePoseEstimator(
                        SwerveConfig.swerveKinematics,
                        Robot.swerve.getHeading(),
                        Robot.swerve.getPositions(),
                        new Pose2d(),
                        createStateStdDevs(
                                config.kPositionStdDevX,
                                config.kPositionStdDevY,
                                config.kPositionStdDevTheta),
                        createVisionMeasurementStdDevs(
                                config.kVisionStdDevX,
                                config.kVisionStdDevY,
                                config.kVisionStdDevTheta));
    }

    @Override
    public void periodic() {
        updateOdometryEstimate();
        /* Adding Limelight estimate to pose if within 1 meter of odometry*/
        if (Robot.vision.isValidPose(Robot.vision.botPose)) {
            Robot.pose.updateOdometryEstimate();
            Robot.pose.addVisionMeasurement(
                    Robot.vision.botPose,
                    Robot.vision.getTimestampSeconds(Robot.vision.getLatency()));
        }
        setEstimatedPose(getPosition());
        setOdometryPose(Robot.swerve.getPoseMeters());

        // updatePose("DesiredPose", desiredPose);
        telemetry.updatePoseOnField("OdometryPose", odometryPose);
        telemetry.updatePoseOnField("EstimatedPose", estimatePose);
    }

    /** Sets the Odometry Pose to the given pose */
    public void setOdometryPose(Pose2d pose) {
        odometryPose = pose;
    }

    /** Sets the desired pose of the robot */
    public void setDesiredPose(Pose2d pose) {
        desiredPose = pose;
    }

    /** Sets the estimated pose to the given pose */
    public void setEstimatedPose(Pose2d pose) {
        estimatePose = pose;
    }

    /** Updates the field relative position of the robot. */
    public void updateOdometryEstimate() {
        poseEstimator.update(Robot.swerve.getHeading(), Robot.swerve.getPositions());
    }

    /**
     * reset the pose estimator
     *
     * @param poseMeters
     */
    public void resetPoseEstimate(Pose2d poseMeters) {
        Robot.swerve.odometry.resetOdometry(poseMeters);
        poseEstimator.resetPosition(
                Robot.swerve.getHeading(), Robot.swerve.getPositions(), poseMeters);
    }

    public void resetHeading(Rotation2d angle) {
        Robot.swerve.odometry.resetHeading(angle);
        resetPoseEstimate(new Pose2d(estimatePose.getTranslation(), angle));
    }

    public void resetLocationEstimate(Translation2d translation) {
        resetPoseEstimate(new Pose2d(translation, estimatePose.getRotation()));
    }

    /**
     * Gets the pose of the robot at the current time as estimated by the poseEstimator.
     *
     * @return The estimated robot pose in meters.
     */
    public Pose2d getPosition() {
        return poseEstimator.getEstimatedPosition();
    }

    /**
     * Get the heading of the robot estimated by the poseEstimator. Use this in most places we would
     * use the gyro.
     *
     * @return
     */
    public Rotation2d getHeading() {
        return estimatePose.getRotation();
    }

    public Translation2d getLocation() {
        return estimatePose.getTranslation();
    }

    /**
     * Creates a vector of standard deviations for the states. Standard deviations of model states.
     * Increase these numbers to trust your model's state estimates less.
     *
     * @param x in meters
     * @param y in meters
     * @param theta in degrees
     * @return the Vector of standard deviations need for the poseEstimator
     */
    public Vector<N3> createStateStdDevs(double x, double y, double theta) {
        return VecBuilder.fill(x, y, Units.degreesToRadians(theta));
    }

    /**
     * Creates a vector of standard deviations for the local measurements. Standard deviations of
     * encoder and gyro rate measurements. Increase these numbers to trust sensor readings from
     * encoders and gyros less.
     *
     * @param theta in degrees per second
     * @param s std for all module positions in meters per sec
     * @return the Vector of standard deviations need for the poseEstimator
     */
    public Vector<N5> createLocalMeasurementStdDevs(double theta, double p) {
        return VecBuilder.fill(Units.degreesToRadians(theta), p, p, p, p);
    }

    /**
     * Creates a vector of standard deviations for the vision measurements. Standard deviations of
     * global measurements from vision. Increase these numbers to trust global measurements from
     * vision less.
     *
     * @param x in meters
     * @param y in meters
     * @param theta in degrees
     * @return the Vector of standard deviations need for the poseEstimator
     */
    public Vector<N3> createVisionMeasurementStdDevs(double x, double y, double theta) {
        return VecBuilder.fill(x, y, Units.degreesToRadians(theta));
    }

    /**
     * Add a vision measurement to the PoseEstimator. This will correct the odometry pose estimate
     * while still accounting for measurement noise.
     *
     * <p>This method can be called as infrequently as you want, as long as you are calling {@link
     * SwerveDrivePoseEstimator#update} every loop.
     *
     * <p>To promote stability of the pose estimate and make it robust to bad vision data, we
     * recommend only adding vision measurements that are already within one meter or so of the
     * current pose estimate.
     *
     * @param visionRobotPoseMeters The pose of the robot as measured by the vision camera.
     * @param timestampSeconds The timestamp of the vision measurement in seconds. Note that if you
     *     don't use your own time source by calling {@link SwerveDrivePoseEstimator#updateWithTime}
     *     then you must use a timestamp with an epoch since FPGA startup (i.e. the epoch of this
     *     timestamp is the same epoch as Timer.getFPGATimestamp.) This means that you should use
     *     Timer.getFPGATimestamp as your time source or sync the epochs.
     */
    public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds) {
        poseEstimator.addVisionMeasurement(visionRobotPoseMeters, timestampSeconds);
    }
}
