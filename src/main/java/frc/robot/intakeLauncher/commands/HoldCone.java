// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;

public class HoldCone extends CommandBase {
    double frontPos = 0;
    double timer = 0;
    double startTime = 0;
    double lowerPercentOutput = 0.3;
    double frontPercentOutput = 0.2;

    /** Creates a new holdRollerPositions. */
    public HoldCone() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        frontPos = Robot.intake.frontMotor.getPosition();
        startTime = Timer.getFPGATimestamp();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.elevator.getPosition()
                >= Elevator.inchesToFalcon(Elevator.config.holdConeHeight)) {
            lowerPercentOutput = 0.3;
            frontPercentOutput = 0.2;
        } else {
            lowerPercentOutput = 0.1;
            frontPercentOutput = 0.1;
        }

        double time =
                Timer.getFPGATimestamp(); // If frontPos has moved 200 ticks then run intake full
        // speed and 1/4 sec to timer
        if (Robot.intake.frontMotor.getPosition() < frontPos - 200) {
            Robot.intake.setPercentOutputs(lowerPercentOutput, frontPercentOutput, 0.0);
            timer = time + 0.25;
            // While timer is still within 1/4 sec of starting keep running intake
        } else if (time - timer < 0) {
            Robot.intake.setPercentOutputs(lowerPercentOutput, frontPercentOutput, 0.0);
            // Once 1/4 sec is up stop the motor and reset frontPos to current one for a 1/4sec
        } else if (time - timer < 0.25) {
            Robot.intake.stopAll();
            frontPos = Robot.intake.frontMotor.getPosition();
            // After all that just wait for cone to slip again
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
