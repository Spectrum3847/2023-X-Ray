// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class LockSwerve extends CommandBase {
    SwerveModuleState[] swerveModuleStates;
    SwerveModuleState[] stopModuleStates;

    /** Creates a new LockSwerve. */
    public LockSwerve() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);

        double minSpeed = 0.01 * Robot.swerve.config.tuning.maxVelocity;

        // Set the angles and minimum speeds to use when locking the swerve base
        swerveModuleStates =
                new SwerveModuleState[] {
                    new SwerveModuleState(minSpeed, Rotation2d.fromDegrees(225)),
                    new SwerveModuleState(minSpeed, Rotation2d.fromDegrees(135)),
                    new SwerveModuleState(minSpeed, Rotation2d.fromDegrees(315)),
                    new SwerveModuleState(minSpeed, Rotation2d.fromDegrees(45))
                };

        stopModuleStates =
                new SwerveModuleState[] {
                    new SwerveModuleState(0, Rotation2d.fromDegrees(225)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(135)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(315)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(45))
                };
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.swerve.setModuleStates(swerveModuleStates);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.swerve.setModuleStates(stopModuleStates);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
