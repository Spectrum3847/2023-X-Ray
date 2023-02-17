package frc.robot.operator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.IntakeCommands;

public class OperatorCommands {
    public static void setupDefaultCommand() {}

    /* Intaking Commands */

    public static Command coneStandingIntake() {
        return IntakeCommands.intake()
                .alongWith(
                        ElevatorCommands.coneStandingIntake(),
                        FourBarCommands.coneStandingIntake());
    }

    /* Position Commands */

    public static Command coneIntake() {
        return IntakeCommands.intake()
                .alongWith(ElevatorCommands.coneIntake(), FourBarCommands.coneIntake());
    }

    public static Command coneMid() {
        return ElevatorCommands.coneMid().alongWith(FourBarCommands.coneMid());
    }

    public static Command coneTop() {
        return ElevatorCommands.coneTop().alongWith(FourBarCommands.coneTop());
    }

    public static Command coneShelfIntake() {
        return IntakeCommands.intake()
                .alongWith(ElevatorCommands.coneShelf(), FourBarCommands.coneShelf());
    }

    public static Command cubeIntake() {
        return IntakeCommands.intake()
                .alongWith(ElevatorCommands.cubeIntake(), FourBarCommands.cubeIntake());
    }

    public static Command cubeMid() {
        return IntakeCommands.midCubeSpinUp().alongWith(homeSystems());
    }

    public static Command cubeTop() {
        return IntakeCommands.topCubeSpinUp().alongWith(homeSystems());
    }

    /** Goes to 0 */
    public static Command homeSystems() {
        return ElevatorCommands.home().alongWith(FourBarCommands.home());
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
