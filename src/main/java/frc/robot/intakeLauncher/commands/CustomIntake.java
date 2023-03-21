// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.Intake;

public class CustomIntake extends CommandBase {
    GamePiece gamePiece;
    boolean velocityLimitReached = false;
    int count = 0;
    int thresholdCount = 0;
    boolean runMotors = true;
    int currentLimit, currentThreshold, lowerVelocity = 0;
    double freeSpeedLevel = 0;

    /** Creates a new CubeIntake. */
    public CustomIntake(GamePiece gamePieceType) {
        // Use addRequirements() here to declare subsystem dependencies.
        gamePiece = gamePieceType;
        if (gamePieceType == GamePiece.CUBE) {
            configureCube(); // cube config
        } else {
            configureCone(); // cone config
        }
        addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        velocityLimitReached = false;
        count = 0;
        thresholdCount = 0;
        runMotors = true;

        if (gamePiece == GamePiece.CUBE) {
            Robot.intake.setCurrentLimits(currentLimit, currentThreshold);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("CustomIntake: " + gamePiece);
        if (Robot.intake.getFrontRPM() > freeSpeedLevel) {
            if (!velocityLimitReached && thresholdCount >= 8) {
                count++;
                velocityLimitReached = true;
            }
            thresholdCount++;
        } else if (Robot.intake.getFrontRPM() <= freeSpeedLevel) {
            velocityLimitReached = false;
        }

        if (count >= 2) {
            runMotors = false;
        }

        if (runMotors) {
            Robot.intake.setVelocities(
                    lowerVelocity,
                    Intake.config.frontIntakeSpeed,
                    Intake.config.launcherIntakeSpeed);
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
        if (gamePiece == GamePiece.CUBE && !RobotState.isAutonomous()) {
            ElevatorCommands.hopElevator().schedule();
            FourBarCommands.home().schedule();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    private void configureCube() {
        currentLimit = 60;
        currentThreshold = 60;
        freeSpeedLevel = 3600;
        lowerVelocity = 3000;
    }

    private void configureCone() {
        freeSpeedLevel = 3600;
        lowerVelocity = 3000;
    }

    public enum GamePiece {
        CONE,
        CUBE
    }
}
