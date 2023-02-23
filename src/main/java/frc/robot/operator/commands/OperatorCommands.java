package frc.robot.operator.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.fourbar.commands.FourBarDelay;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.BlinkLEDCommand;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.operator.OperatorConfig;

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

    public static Command coneHybrid() {
        return IntakeCommands.slowIntake()
                .alongWith(ElevatorCommands.coneHybrid(), FourBarCommands.coneHybrid());
    }

    public static Command coneMid() {
        return IntakeCommands.slowIntake()
                .alongWith(ElevatorCommands.coneMid(), FourBarCommands.coneMid());
    }

    public static Command coneTop() {
        return IntakeCommands.slowIntake()
                .alongWith(
                        ElevatorCommands.coneTop(),
                        new FourBarDelay(
                                FourBar.config.safePositionForElevator,
                                FourBar.config.coneTop,
                                Elevator.config.safePositionForFourBar));
    }

    public static Command coneShelfIntake() {
        return IntakeCommands.intake()
                .alongWith(ElevatorCommands.coneShelf(), FourBarCommands.coneShelf());
    }

    public static Command cubeIntake() {
        return IntakeCommands.intake()
                .alongWith(ElevatorCommands.cubeIntake(), FourBarCommands.cubeIntake());
    }

    public static Command cubeHybrid() {
        return IntakeCommands.hybridSpinUp().alongWith(homeSystems());
    }

    public static Command cubeMid() {
        return IntakeCommands.midCubeSpinUp().alongWith(homeSystems());
    }

    public static Command cubeTop() {
        return IntakeCommands.topCubeSpinUp().alongWith(homeSystems());
    }

    public static Command cubeChargeStation() {
        return IntakeCommands.fullSpinUp().alongWith(homeSystems());
    }

    public static Command homeAndSlowIntake() {
        return IntakeCommands.slowIntake().alongWith(homeSystems());
    }

    /** Goes to 0 */
    public static Command homeSystems() {
        return FourBarCommands.home().alongWith(ElevatorCommands.safeHome());
    }

    public static Command manualElevator() {
        return new RunCommand(
                () -> Robot.elevator.setManualOutput(Robot.operatorGamepad.elevatorManual()),
                Robot.elevator);
    }

    public static Command slowManualElevator() {
        return new RunCommand(
                () ->
                        Robot.elevator.setManualOutput(
                                Robot.operatorGamepad.elevatorManual()
                                        * OperatorConfig.slowModeScalar),
                Robot.elevator);
    }

    public static Command manualFourBar() {
        return new RunCommand(
                () -> Robot.fourBar.setManualOutput(Robot.operatorGamepad.fourBarManual()),
                Robot.fourBar);
    }

    public static Command slowManualFourBar() {
        return new RunCommand(
                () ->
                        Robot.fourBar.setManualOutput(
                                Robot.operatorGamepad.fourBarManual()
                                        * OperatorConfig.slowModeScalar),
                Robot.fourBar);
    }

    public static Command coneFloorLED() {
        return new OneColorLEDCommand(Color.kYellow, "Yellow Floor Cone", 99, 3);
    }

    public static Command coneShelfLED() {
        return new BlinkLEDCommand(Color.kYellow, "Yellow Shelf Cone", 99, 3);
    }

    public static Command cubeLED() {
        return new OneColorLEDCommand(Color.kPurple, "Purple Cube", 99, 3);
    }
}
