// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;

public class ConeIntake extends CommandBase {
    boolean velocityLimitReached = false;
    int count = 0;
    int thresholdCount = 0;
    boolean runMotors, runOnce = true;
    boolean isShelfIntake = false;
    double initialX, finalX = 0;

    /** Creates a new CubeIntake. */
    public ConeIntake() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    public ConeIntake(boolean isShelfIntake) {
        this();
        this.isShelfIntake = isShelfIntake;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        velocityLimitReached = false;
        count = 0;
        thresholdCount = 0;
        runMotors = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.intake.getFrontRPM() > 3500) {
            if (!velocityLimitReached && thresholdCount >= 8) {
                count++;
                velocityLimitReached = true;
            }
            thresholdCount++;
        } else if (Robot.intake.getFrontRPM() < 3500) {
            velocityLimitReached = false;
        }

        if (count >= 1 && !velocityLimitReached) {
            runMotors = false;
        }

        if (runMotors) {
            Robot.intake.setVelocities(
                    3000, Intake.config.frontIntakeSpeed, Intake.config.launcherIntakeSpeed);
        } else {
            if (isShelfIntake) {
                if (runOnce) {
                    initialX = Robot.pose.getEstimatedPose().getX();
                    runOnce = false;
                }
                Robot.pilotGamepad.rumble(0.5);
                finalX = Robot.pose.getEstimatedPose().getX();

                if (Math.abs(initialX - finalX) > 0.3) {
                    Robot.pilotGamepad.rumble(0);
                    Robot.operatorGamepad.rumble(0.5);
                }

            } else {
                Robot.operatorGamepad.rumble(0.5);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.operatorGamepad.rumble(0);
        Robot.pilotGamepad.rumble(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
