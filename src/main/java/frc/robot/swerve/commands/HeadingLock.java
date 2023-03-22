// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import java.util.function.DoubleSupplier;

public class HeadingLock extends CommandBase {
    /** Creates a new HeadingLock. */
    double currentHeading = 0;

    SwerveDrive driveCommand;

    public HeadingLock(DoubleSupplier fwdPositiveSupplier, DoubleSupplier leftPositiveSupplier) {
        driveCommand =
                new SwerveDrive(fwdPositiveSupplier, leftPositiveSupplier, () -> getSteering());
        addRequirements(Robot.swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        currentHeading = Robot.swerve.getRotation().getRadians();
        Robot.swerve.resetRotationController();
        driveCommand.initialize();
    }

    public double getSteering() {
        return Robot.swerve.calculateRotationController(() -> currentHeading);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        driveCommand.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveCommand.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return driveCommand.isFinished();
    }
}