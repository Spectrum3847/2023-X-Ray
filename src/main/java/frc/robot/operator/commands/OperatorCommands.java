package frc.robot.operator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.IntakeCommands;

public class OperatorCommands {
    public static void setupDefaultCommand() {}

    public static Command cubeIntake() {
        return ElevatorCommands.setMMPosition(Elevator.config.cubeIntake)
                .alongWith(FourBarCommands.setMMPosition(FourBar.config.cubeIntake));
    }

    public static Command coneIntake() {
        return ElevatorCommands.setMMPosition(Elevator.config.coneIntake)
                .alongWith(FourBarCommands.setMMPosition(FourBar.config.coneIntake));
        // return IntakeCommands.intake()
        //        .alongWith(ElevatorCommands.setConeIntake(), FourBarCommands.setConeIntake());
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
        return ElevatorCommands.setMMPosition(Elevator.config.coneMid)
                .alongWith(FourBarCommands.setMMPosition(FourBar.config.coneMid));
    }

    public static Command coneTop() {
        return ElevatorCommands.setMMPosition(Elevator.config.coneTop)
                .alongWith(FourBarCommands.setMMPosition(FourBar.config.coneTop));
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
