package frc.robot.operator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.IntakeCommands;

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
        return ElevatorCommands.coneIntake().alongWith(FourBarCommands.coneIntake());
    }

    public static Command coneMid() {
        return ElevatorCommands.coneMid().alongWith(FourBarCommands.coneMid());
    }

    public static Command coneTop() {
        return ElevatorCommands.coneTop().alongWith(FourBarCommands.coneTop());
    }

    public static Command coneShelf() {
        return ElevatorCommands.coneShelf().alongWith(FourBarCommands.coneShelf());
    }

    public static Command cubeIntake() {
        return ElevatorCommands.cubeIntake().alongWith(FourBarCommands.cubeIntake());
    }

    public static Command cubeMid() {
        return ElevatorCommands.cubeMid().alongWith(FourBarCommands.cubeMid());
    }

    public static Command cubeTop() {
        return ElevatorCommands.cubeTop().alongWith(FourBarCommands.cubeTop());
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
