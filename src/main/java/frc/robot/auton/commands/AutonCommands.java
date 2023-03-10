package frc.robot.auton.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.AutonConfig;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.commands.OperatorCommands;

public class AutonCommands {

    public static void setupDefaultCommand() {}

    public static Command rightStationTop() {
        return spinLauncher(IntakeCommands.bumpTopSpinUp()).andThen(launch(), stopMotors());
    }

    /* These 3 commands have not been mapped to the operator gamepad */
    public static Command communityTop() {
        return spinLauncher(IntakeCommands.communityTopSpinUp()).andThen(launch(), stopMotors());
    }

    public static Command behindStationMid() {
        return spinLauncher(IntakeCommands.behindStationMidSpinUp())
                .andThen(launch(), stopMotors());
    }

    public static Command onStationTop() {
        return spinLauncher(IntakeCommands.onStationTopSpinUp()).andThen(launch(), stopMotors());
    }

    /** Goes to 0 */
    private static Command homeSystems() {
        return FourBarCommands.home().alongWith(ElevatorCommands.safeHome());
    }

    private static Command spinLauncher(Command spinCommand) {
        return spinCommand.withTimeout(
                SmartDashboard.getNumber("spinTime", AutonConfig.spinUpTime));
    }

    private static Command launch() {
        return IntakeCommands.launch().withTimeout(AutonConfig.launchTime);
    }

    private static Command stopMotors() {
        return IntakeCommands.stopAllMotors().withTimeout(AutonConfig.stopTime);
    }

    public static Command retractIntake() {
        return homeSystems();
    }

    public static Command intakeCube() {
        return OperatorCommands.cubeIntake();
    }

    public static Command simpleLaunchCube() {
        return OperatorCommands.cubeTop()
                .withTimeout(0.5)
                .andThen(IntakeCommands.launch())
                .withTimeout(2)
                .andThen(IntakeCommands.stopAllMotors().withTimeout(0.01));
    }
}
