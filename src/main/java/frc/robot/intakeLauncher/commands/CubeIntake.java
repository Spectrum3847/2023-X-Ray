// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;

public class CubeIntake extends CommandBase {
    boolean currentLimitReached = false;
    int count = 0;
    double timer = 0;
    boolean runMotors = true;
    /** Creates a new CubeIntake. */
    public CubeIntake() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        currentLimitReached = false;
        count = 0;
        timer = 0;
        runMotors = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.intake.getFrontCurrent() > 50 && currentLimitReached == false) {
            count++;
            currentLimitReached = true;
        } else if (Robot.intake.getFrontCurrent() <= 50) {
            currentLimitReached = false;
        }

        if (count >= 2 && timer == 0) {
            timer = Timer.getFPGATimestamp();
        }

        if (Timer.getFPGATimestamp() - timer > 0.8  && timer != 0) {
            runMotors = false;
        }

        if (runMotors) {
            Robot.intake.setVelocities(
                    Intake.config.lowerIntakeSpeed,
                    Intake.config.frontIntakeSpeed,
                    Intake.config.launcherIntakeSpeed);
        } else {
            Robot.intake.stopAll();
        }
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
