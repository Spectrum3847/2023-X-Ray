// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.fourbar.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class FourBarHoldPosition extends CommandBase {
    double position = 0;

    /** Creates a new FourBarHoldPosition. */
    public FourBarHoldPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.fourBar);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        position = Robot.fourBar.getPosition();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.fourBar.setMMPosition(position);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.fourBar.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
