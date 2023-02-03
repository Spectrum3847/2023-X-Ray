package frc.robot.operator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.FourBarCommands;
import frc.robot.intakeLauncher.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class OperatorCommands {
    public static void setupDefaultCommand() {}

    public static Command cubeIntake() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.cubeIntake),
                        FourBarCommands.setMMPosition(FourBar.config.cubeIntake));
    }

    public static Command coneIntake() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.coneIntake),
                        FourBarCommands.setMMPosition(FourBar.config.coneIntake));
    }

    public static Command coneStandingIntake() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.coneStandingIntake),
                        FourBarCommands.setMMPosition(FourBar.config.coneStandingIntake));
    }

    public static Command coneShelf() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.coneShelf),
                        FourBarCommands.setMMPosition(FourBar.config.coneShelf));
    }

    public static Command cubeMid() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.cubeMid),
                        FourBarCommands.setMMPosition(FourBar.config.cubeMid));
    }

    public static Command cubeTop() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.cubeTop),
                        FourBarCommands.setMMPosition(FourBar.config.cubeTop));
    }

    public static Command coneMid() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.coneMid),
                        FourBarCommands.setMMPosition(FourBar.config.coneMid));
    }

    public static Command coneTop() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.setMMPosition(Elevator.config.coneTop),
                        FourBarCommands.setMMPosition(FourBar.config.coneTop));
    }

    public static Command manualElevator() {
        return new RunCommand(
                () -> Robot.elevator.setManualOutput(Robot.operatorGamepad.elevatorManual()),
                Robot.elevator);
    }

    public static Command manualFourBar() {
        return new RunCommand(
                () -> Robot.fourBar.setManualOutput(Robot.operatorGamepad.fourBarManual()),
                Robot.fourBar);
    }
}
