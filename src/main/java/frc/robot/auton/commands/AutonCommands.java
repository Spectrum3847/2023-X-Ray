package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.AutonConfig;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.IntakeCommands;

public class AutonCommands {

    public static void setupDefaultCommand() {}

    public static Command rightStationTop() {
        return rightStationTopSpinUp().andThen(launch(), stopMotors());
    }

    /** Goes to 0 */
    private static Command homeSystems() {
        return FourBarCommands.home().alongWith(ElevatorCommands.safeHome());
    }

    private static Command rightStationTopSpinUp() {
        return IntakeCommands.bumpTopSpinUp().withTimeout(AutonConfig.spinUpTime);
    }

    private static Command launch() {
        return IntakeCommands.launch().withTimeout(AutonConfig.launchTime);
    }

    private static Command stopMotors() {
        return IntakeCommands.stopAllMotors().withTimeout(AutonConfig.stopTime);
    }
}
