package frc.robot.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;

public class Odometry {

    public SwerveDriveOdometry swerveOdometry;
    private Swerve swerve;

    public Odometry(Swerve s) {
        swerve = s;
        swerveOdometry =
                new SwerveDriveOdometry(
                        swerve.config.swerveKinematics,
                        swerve.gyro.getRawYaw(),
                        swerve.getPositions());
    }

    public SwerveDriveOdometry getSwerveDriveOdometry() {
        return swerveOdometry;
    }

    public void update() {
        swerveOdometry.update(swerve.gyro.getRawYaw(), swerve.getPositions());
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(swerve.gyro.getRawYaw(), swerve.getPositions(), pose);
    }

    public Pose2d getPoseMeters() {
        return swerveOdometry.getPoseMeters();
    }

    public Translation2d getTranslationMeters() {
        return swerveOdometry.getPoseMeters().getTranslation();
    }

    public Rotation2d getRotation() {
        return getPoseMeters().getRotation();
    }

    public void resetHeading(Rotation2d newHeading) {
        resetOdometry(new Pose2d(getTranslationMeters(), newHeading));
    }
}
