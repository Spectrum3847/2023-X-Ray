// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.commands.PilotCommands;

public class ConeIntake extends CommandBase {
    double velocityThreshold = 2100;
    boolean velocityLimitReached = false;
    int count = 0;
    int thresholdCount = 0;
    boolean runMotors, runOnce = true;
    boolean isShelfIntake = false;
    double initialX, finalX = 0;
    Command operatorRumble;

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
        Robot.intake.setCurrentLimits(10, 10);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.intake.getFrontRPM() > velocityThreshold) {
            if (!velocityLimitReached && thresholdCount >= 8) {
                count++;
                velocityLimitReached = true;
            }
            thresholdCount++;
        } else if (Robot.intake.getFrontRPM() < velocityThreshold) {
            velocityLimitReached = false;
        }

        if (count >= 1 && !velocityLimitReached) {
            runMotors = false;
        }

        if (runMotors) {
            Robot.intake.setVelocities(
                    Intake.config.lowerIntakeSpeed,
                    Intake.config.frontIntakeSpeed,
                    Intake.config.launcherIntakeSpeed);
        } else {
            if (isShelfIntake) {
                if (runOnce) {
                    initialX = Robot.pose.getEstimatedPose().getX();
                    runOnce = false;
                }
                Robot.pilotGamepad.rumble(0.5);
                Robot.operatorGamepad.rumble(0.5);
                finalX = Robot.pose.getEstimatedPose().getX();

                // if (Math.abs(initialX - finalX) > 0.8) {
                //     Robot.pilotGamepad.rumble(0);
                //     Robot.operatorGamepad.rumble(0);
                //     OperatorCommands.homeSystems().withTimeout(1.5).schedule();
                // }

            } else {
                if (!DriverStation.isAutonomous()) {
                    OperatorCommands.rumble(0.5, 1).schedule();
                    PilotCommands.rumble(0.5, 1).schedule();
                    OperatorCommands.finishConeIntake();
                }
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.operatorGamepad.rumble(0);
        Robot.pilotGamepad.rumble(0);
        Robot.intake.setCurrentLimits(Intake.config.currentLimit, Intake.config.threshold);
        // OperatorCommands.homeSystems().withTimeout(1.5).schedule();
        // operatorRumble.cancel();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
