// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.Intake;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.commands.PilotCommands;

public class CubeIntake extends CommandBase {
    boolean velocityLimitReached = false;
    int count = 0;
    int thresholdCount = 0;
    boolean runMotors = true;
    double startTime = 0;

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
        thresholdCount = 0;
        runMotors = true;
        startTime = Timer.getFPGATimestamp();

        Robot.intake.setCurrentLimits(Intake.config.currentLimit, Intake.config.threshold);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // if (Robot.intake.getFrontRPM() > 3000) {
        //     if (!velocityLimitReached && thresholdCount >= 8) {
        //         count++;
        //         velocityLimitReached = true;
        //     }
        //     thresholdCount++;
        // } else if (Robot.intake.getFrontRPM() <= 3000) {
        //     velocityLimitReached = false;
        // }

        // if (count >= 2) {
        //     runMotors = false;
        // }

        if (!Robot.intake.getCubeSensor()) {
            // Robot.intake.setVelocities(
            //        3000, Intake.config.frontIntakeSpeed, Intake.config.launcherIntakeSpeed);
            Robot.intake.setPercentOutputs(0.5, 1.0, -0.4);

            // Robot.operatorGamepad.rumble(0);
            // Robot.pilotGamepad.rumble(0);
        } else {
            Robot.intake.stopAll();
            /*ElevatorCommands.hopElevator().schedule();
            FourBarCommands.home().schedule();*/

            if (!RobotState.isAutonomous()) {
                if (Timer.getFPGATimestamp() - startTime > 1.5) {
                    PilotCommands.rumble(0.5, 1).schedule();
                    OperatorCommands.rumble(0.5, 1).schedule();
                }
                // Robot.operatorGamepad.rumble(0.5);
                // Robot.pilotGamepad.rumble(0.5);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // Robot.intake.setCurrentLimits(Intake.config.currentLimit, Intake.config.threshold);

        Robot.intake.stopAll();
        // Robot.operatorGamepad.rumble(0);
        // Robot.pilotGamepad.rumble(0);
        if (!RobotState.isAutonomous()) {
            ElevatorCommands.hopElevator().withTimeout(1).schedule();
            FourBarCommands.home().withTimeout(1).schedule();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Robot.intake.getCubeSensor() && Timer.getFPGATimestamp() - startTime > 1.5;
    }
}
