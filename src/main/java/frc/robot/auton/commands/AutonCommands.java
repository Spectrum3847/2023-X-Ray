package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.AutonConfig;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.fourbar.commands.FourBarDelay;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.intakeLauncher.commands.IntakeCommands;

public class AutonCommands {

    public static void setupDefaultCommand() {}

    public static Command rightStationTop() {
        return spinLauncher(IntakeCommands.bumpTopSpinUp()).andThen(launch(), stopMotors());
    }

    /* These 3 commands have not been mapped to the operator gamepad */
    public static Command firstShot() {
        return spinLauncher(IntakeCommands.firstShot()).andThen(launch(), stopMotors());
    }

    public static Command secondShot() {
        return spinLauncher(IntakeCommands.secondShot()).andThen(launch(), stopMotors());
    }

    public static Command angleThirdShot() {
        return spinLauncher(IntakeCommands.angleThirdShot()).andThen(launch(), stopMotors());
    }

    public static Command thirdShotBalance() {
        return spinLauncher(IntakeCommands.thirdShotBalance()).andThen(launch(), stopMotors());
    }

    /** Goes to 0 */
    private static Command homeSystems() {
        return FourBarCommands.home().alongWith(ElevatorCommands.safeHome());
    }

    private static Command spinLauncher(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime);
    }

    private static Command launch() {
        return IntakeCommands.launch().withTimeout(AutonConfig.launchTime);
    }

    private static Command stopMotors() {
        return IntakeCommands.stopAllMotors().withTimeout(AutonConfig.stopTime);
    }

    public static Command retractIntake() {
        return homeSystems().alongWith(IntakeCommands.stopAllMotors());
    }

    public static Command intakeCube() {
        return new CubeIntake()
                .alongWith(ElevatorCommands.cubeIntake(), FourBarCommands.cubeIntake());
    }

    public static Command intakeCone() {
        return new ConeIntake()
                .alongWith(
                        ElevatorCommands.coneStandingIntake(), FourBarCommands.coneStandingIntake())
                .withName("Auton Standing Cone")
                .finallyDo((b) -> homeSystems().withTimeout(1).schedule());
    }

    public static Command coneMid() {
        return IntakeCommands.slowIntake()
                .alongWith(ElevatorCommands.coneMid(), FourBarCommands.coneMid())
                .withName("Auton Cone Mid")
                .withTimeout(.8)
                .andThen(IntakeCommands.eject().withTimeout(.8))
                .andThen(retractIntake().withTimeout(.8));
    }

    public static Command coneTop() {
        return IntakeCommands.slowIntake()
                .alongWith(
                        ElevatorCommands.coneTop(),
                        new FourBarDelay(
                                FourBar.config.safePositionForElevator,
                                FourBar.config.coneTop,
                                Elevator.config.safePositionForFourBar))
                .withTimeout(.8)
                .andThen(IntakeCommands.eject().withTimeout(.8))
                .andThen(retractIntake().withTimeout(.8));
    }

    public static Command simpleLaunchCube() {
        return IntakeCommands.topCubeSpinUp()
                .alongWith(ElevatorCommands.cubeTop())
                .withTimeout(0.5)
                .andThen(IntakeCommands.launch())
                .withTimeout(2)
                .andThen(IntakeCommands.stopAllMotors().withTimeout(0.01));
    }
}
