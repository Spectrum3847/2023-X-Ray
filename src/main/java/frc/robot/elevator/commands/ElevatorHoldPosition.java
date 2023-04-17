// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;

public class ElevatorHoldPosition extends CommandBase {
    private double holdPosition = 0;
    /** Creates a new ElevatorHoldPosition. */
    public ElevatorHoldPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.elevator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        holdPosition = Robot.elevator.getPosition();

        // Robot.elevator.setEncoder(Elevator.config.maxExtension);
        // Robot.elevator.setMMPosition(0);
        // moves elevator down to lowest position
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double currentPosition = Robot.elevator.getPosition();
        if (Math.abs(holdPosition - currentPosition) <= 5000) {
            Robot.elevator.setMMPosition(holdPosition);
        } else {
            DriverStation.reportError(
                    "ElevatorHoldPosition tried to go too far away from current position. Current Position: "
                            + Elevator.falconToInches(currentPosition)
                            + " || Hold Position: "
                            + Elevator.falconToInches(holdPosition),
                    false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.elevator.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
