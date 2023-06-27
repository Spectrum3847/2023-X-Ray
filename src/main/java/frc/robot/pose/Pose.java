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
                        Robot.swerve.config.swerveKinematics,
                        Robot.swerve.getRotation(),
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
        // Robot.vision.update();
        setEstimatedPose(getPosition());
        setOdometryPose(Robot.swerve.getPoseMeters());

        // telemetry.updatePoseOnField("VisionPose", Robot.vision.botPose);
        telemetry.updatePoseOnField("OdometryPose", odometryPose);
        telemetry.updatePoseOnField("EstimatedPose", estimatePose);
    }

    // /**
    //  * @return true if estimated pose is on the chargestation by using the field-space
    // chargestation
    //  *     dimensions
    //  */
    // public boolean isOnChargeStation() {
    //     return ((getBestPose().getX() > 2.9 && getBestPose().getX() < 4.8)
    //             && (getBestPose().getY() > 1.54 && getBestPose().getY() < 3.99));
    // }

    // /**
    //  * Returns the most accurate pose. If we are not confident that vision is accurate,
    //  * estimatedPose is considered to be most accurate.
    //  *
    //  * @return vision pose or estimated pose
    //  */
    // public Pose2d getBestPose() {
    //     if (Robot.vision.visionAccurate()) {
    //         return Robot.vision.botPose;
    //     } else {
    //         return estimatePose;
    //     }
    // }

    /** Sets the Odometry Pose to the given pose */
    public void setOdometryPose(Pose2d pose) {
        odometryPose = pose;
    }

    public Pose2d getOdometryPose() {
        return odometryPose;
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
        poseEstimator.update(Robot.swerve.getRotation(), Robot.swerve.getPositions());
    }

    /**
     * reset the pose estimator
     *
     * @param poseMeters
     */
    public void resetPoseEstimate(Pose2d poseMeters) {
        Robot.swerve.odometry.resetOdometry(poseMeters);
        poseEstimator.resetPosition(
                Robot.swerve.getRotation(), Robot.swerve.getPositions(), poseMeters);
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

    public Pose2d getEstimatedPose() {
        return estimatePose;
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
