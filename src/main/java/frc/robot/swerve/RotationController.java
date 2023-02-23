package frc.robot.swerve;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import frc.robot.swerve.configTemplates.SwerveConfig;

public class RotationController {
    Swerve swerve;
    SwerveConfig config;
    ProfiledPIDController controller;
    Constraints constraints;

    public RotationController(Swerve swerve) {
        this.swerve = swerve;
        config = swerve.config;
        constraints =
                new Constraints(
                        config.tuning.maxAngularVelocity, config.tuning.MaxAngularAcceleration);
        controller =
                new ProfiledPIDController(
                        config.tuning.kPRotationController,
                        config.tuning.kIRotationController,
                        config.tuning.kDRotationController,
                        constraints);

        controller.enableContinuousInput(-Math.PI, Math.PI);
        controller.setTolerance(1);
    }

    public double calculate(double goalRadians) {
        return controller.calculate(swerve.getRotation().getRadians(), goalRadians);
    }

    public boolean atSetpoint() {
        return controller.atSetpoint();
    }

    public void reset() {
        controller.reset(swerve.getRotation().getRadians());
    }
}
