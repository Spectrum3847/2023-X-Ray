// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ElevatorHoldPosition extends CommandBase {
    private double position = 0;
    /** Creates a new ElevatorHoldPosition. */
    public ElevatorHoldPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.elevator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        position = Robot.elevator.getPosition();
        // Robot.elevator.setEncoder(Elevator.config.maxExtension);
        // Robot.elevator.setMMPosition(0);
        // moves elevator down to lowest position
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.elevator.setMMPosition(position);
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
