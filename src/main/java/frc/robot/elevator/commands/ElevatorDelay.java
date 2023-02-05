// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ElevatorDelay extends CommandBase {
    private double safePos;
    private double finalPos;
    private double conditionalPercent;
    /**
     * Creates a new ElevatorDelay. Elevator will move to safePos, wait for FourBar to be at
     * conditional percentage, then move to finalPos
     *
     * @param safePos Elevator position that won't hit anything
     * @param finalPos position that Elevator will go to after FourBar is at conditionalPercent
     * @param conditionalPercent percentage that FourBar must be at before Elevator will move to
     *     finalPos ex: 40% = 40 not .40
     */
    public ElevatorDelay(double safePos, double finalPos, int conditionalPercent) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.safePos = safePos;
        this.finalPos = finalPos;
        this.conditionalPercent = conditionalPercent;
        addRequirements(Robot.elevator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Robot.elevator.setMMPosition(safePos);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Robot.fourBar.getPosition() <= Robot.fourBar.percentToFalcon(conditionalPercent)) {
            Robot.elevator.setMMPosition(finalPos);
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
