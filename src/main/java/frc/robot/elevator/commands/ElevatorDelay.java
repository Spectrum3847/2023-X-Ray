// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.ElevatorConfig;
import frc.robot.leds.commands.OneColorLEDCommand;

public class ElevatorDelay extends CommandBase {
    private double safePos;
    private double finalPos;
    private double conditionalPercent;
    private boolean simpleDelay;
    private boolean isFromGamepad;
    private boolean stopHoming = false;

    /**
     * Homes the elevator and stops after {@link ElevatorConfig#homeTimeout} once it reaches a
     * {@link ElevatorConfig#homeThreshold} from 0
     */
    private boolean isGoingHome;

    private boolean reachedThreshold;
    private double timestamp;

    /**
     * Creates a new ElevatorDelay. Elevator will move to safePos, wait for FourBar to be at
     * conditional percentage, then move to finalPos
     *
     * @param safePos Falcon Units: Elevator position that won't hit anything
     * @param finalPos Falcon Units: position that Elevator will go to after FourBar is at
     *     conditionalPercent
     * @param conditionalPercent percentage that FourBar must be at before Elevator will move to
     *     finalPos ex: 40% = 40 not .40
     */
    public ElevatorDelay(double safePos, double finalPos, int conditionalPercent) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.safePos = safePos;
        this.finalPos = finalPos;
        this.conditionalPercent = conditionalPercent;
        this.simpleDelay = false;
        this.isFromGamepad = false;
        homeCheck(finalPos);
        this.setName("ElevatorDelay");
        addRequirements(Robot.elevator);
    }

    /**
     * Creates a new ElevatorDelay. Elevator will move to safePos, wait for FourBar to be at
     * conditional percentage, then move to finalPos
     *
     * @param safePos Falcon Units: Elevator position that won't hit anything
     * @param finalPos Falcon Units: position that Elevator will go to after FourBar is at
     *     conditionalPercent
     * @param conditionalPercent percentage that FourBar must be at before Elevator will move to
     *     finalPos ex: 40% = 40 not .40
     */
    public ElevatorDelay(
            double safePos, double finalPos, int conditionalPercent, boolean isFromGamepad) {
        this(safePos, finalPos, conditionalPercent);
        this.isFromGamepad = isFromGamepad;
    }

    /**
     * Creates a new ElevatorDelay. Wait for FourBar to be at conditional percentage, then move to
     * finalPos
     *
     * @param finalPos Falcon Units: position that Elevator will go to after FourBar is at
     *     conditionalPercent
     * @param conditionalPercent percentage that FourBar must be at before Elevator will move to
     *     finalPos ex: 40% = 40 not .40
     */
    public ElevatorDelay(double finalPos, int conditionalPercent) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.finalPos = finalPos;
        this.conditionalPercent = conditionalPercent;
        this.simpleDelay = true;
        this.isFromGamepad = false;
        homeCheck(finalPos);
        this.setName("ElevatorDelay");
        addRequirements(Robot.elevator);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        homeCheck(finalPos);
        stopHoming = false;
        reachedThreshold = false;
    }

    /* Called every time the scheduler runs while the command is scheduled.
     *
     * if elevator is going down and fourbar is out too far and elevator is greater than safepos then go to safe position
     * otherwise just go straight down
     */
    @Override
    public void execute() {
        if (simpleDelay) {
            if (Robot.fourBar.getPosition() < Robot.fourBar.percentToFalcon(conditionalPercent)) {
                Robot.elevator.setMMPosition(finalPos);
            }
        } else {
            if (Robot.elevator.getPosition() > finalPos
                    && Robot.fourBar.getPosition()
                            > Robot.fourBar.percentToFalcon(conditionalPercent)
                    && Robot.elevator.getPosition() > safePos) {
                Robot.elevator.setMMPosition(safePos);

            } else {
                Robot.elevator.setMMPosition(finalPos);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (isGoingHome && isFromGamepad && stopHoming) {
            // new OneColorLEDCommand(Color.kBlueViolet, "Finish ElevatorHome", 50, 4).schedule();
        } else {
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (isGoingHome) {
            if (Robot.elevator.getPosition() <= Elevator.config.homeThreshold
                    && !reachedThreshold) {
                reachedThreshold = true;
                timestamp = Timer.getFPGATimestamp();
            }
            if (reachedThreshold
                    && (Timer.getFPGATimestamp() - timestamp) >= Elevator.config.homeTimeout) {
                stopHoming = true;
            }
            return stopHoming;
        } else {
            return false;
        }
    }

    private void homeCheck(double finalPos) {
        reachedThreshold = false;
        timestamp = 0;
        if (finalPos == 0) {
            isGoingHome = true;
        } else {
            isGoingHome = false;
        }
    }
}
