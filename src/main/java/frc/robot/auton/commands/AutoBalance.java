// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.commands.SwerveDrive;

public class AutoBalance extends CommandBase {
    public double currentAngle = 100;
    private double currentRate = 100;
    private double angleOffset;
    private boolean shouldDrive;
    Command driveCommand;

    public AutoBalance() {
        addRequirements(Robot.swerve);
    }

    @Override
    public void initialize() {
        if (DriverStation.isAutonomous()) {
            angleOffset = Robot.swerve.gyro.getAngleOffset();
        } else {
            angleOffset = AutonConfig.gryoOffset;
        }
        Robot.swerve.resetRotationController();
        driveCommand =
                new SwerveDrive(
                        () -> driveSpeed(),
                        () -> 0.0,
                        () -> Robot.swerve.calculateRotationController(() -> Math.PI));
        driveCommand.initialize();
        if (Math.abs(currentAngle()) <= AutonConfig.stopDrivingAngle) {
            shouldDrive = false;
        } else {
            shouldDrive = true;
        }
    }

    @Override
    public void execute() {
        currentAngle = currentAngle();
        currentRate =
                Robot.swerve.getRotation().getCos() * Robot.swerve.gyro.getPitchRate()
                        + Robot.swerve.getRotation().getSin() * Robot.swerve.gyro.getRollRate();

        boolean shouldStop =
                (currentAngle < 0.0 && currentRate > AutonConfig.stopDrivingRate)
                        || (currentAngle > 0.0 && currentRate < -AutonConfig.stopDrivingRate)
                        || (Math.abs(currentAngle) <= AutonConfig.stopDrivingAngle);

        if (shouldStop) {
            Robot.swerve.stop();
        } else if (shouldDrive == false) {
            Robot.swerve.stop();
        } else {
            driveCommand.execute();
        }
    }

    private double driveSpeed() {
        return AutonConfig.balanceDriveSpeed * (currentAngle > 0.0 ? -1.0 : 1.0);
    }

    private double currentAngle() {
        return Robot.swerve.gyro.getRawPitch().getDegrees() * Robot.swerve.getRotation().getCos()
                + Robot.swerve.gyro.getRawRoll().getDegrees() * Robot.swerve.getRotation().getSin()
                - angleOffset;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.swerve.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(currentAngle) <= AutonConfig.stopDrivingAngle;
    }
}
