// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.fourbar.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.fourbar.FourBar;

public class ZeroFourBarRoutine extends CommandBase {
    /** Creates a new ZerofourBar. */
    public ZeroFourBarRoutine() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.fourBar);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // Turn off soft limits
        Robot.fourBar.softLimitsFalse();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Set fourBar to slowly lower
        Robot.fourBar.setManualOutput(FourBar.config.zeroSpeed);
        Robot.fourBar.zeroFourBar();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // Set fourBar position to zero
        // enable soft limits
        Robot.fourBar.resetSensorPosition(-1100);
        Robot.fourBar.softLimitsTrue();
        Robot.fourBar.setMMPosition(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
