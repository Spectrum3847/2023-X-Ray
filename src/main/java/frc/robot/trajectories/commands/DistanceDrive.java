// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trajectories.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.swerve.commands.HeadingLock;

public class DistanceDrive extends CommandBase {
    double distance = 0;
    Command driveCommand;
    /** Creates a new DistanceDrive. */
    public DistanceDrive(double distance) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
        this.distance = distance;
        driveCommand =
                new HeadingLock(
                        () -> Robot.pilotGamepad.getDriveFwdPositive(),
                        () -> getCalculatedY(),
                        () -> Robot.pilotGamepad.getPilotScalar());
    }

    private double getCalculatedY() {
        return Robot.trajectories
                .calculateYSupplier(Robot.trajectories.targetYposition)
                .getAsDouble();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.trajectories.resetTargetYDistance();
        Robot.trajectories.addTargetYDistance(distance);
        driveCommand.initialize();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        driveCommand.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {}
        driveCommand.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
