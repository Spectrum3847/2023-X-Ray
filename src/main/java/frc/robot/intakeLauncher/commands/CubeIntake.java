// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;

public class CubeIntake extends CommandBase {
    boolean velocityLimitReached = false;
    int count = 0;
    boolean runMotors = true;
    /** Creates a new CubeIntake. */
    public CubeIntake() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        velocityLimitReached = false;
        count = 0;
        runMotors = true;

        Robot.intake.setCurrentLimits(60, 60);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.intake.getFrontRPM() > 3800 && velocityLimitReached == false) {
            count++;
            velocityLimitReached = true;
        } else if (Robot.intake.getFrontRPM() <= 3600) {
            velocityLimitReached = false;
        }

        if (count >= 2) {
            runMotors = false;
        }

        if (runMotors) {
            Robot.intake.setVelocities(
                    3000, Intake.config.frontIntakeSpeed, Intake.config.launcherIntakeSpeed);
        } else {
            Robot.intake.stopAll();
            Robot.operatorGamepad.rumble(0.5);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.intake.setCurrentLimits(Intake.config.currentLimit, Intake.config.threshold);
        Robot.operatorGamepad.rumble(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
