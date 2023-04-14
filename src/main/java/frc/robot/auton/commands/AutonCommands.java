package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.auton.AutonConfig;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.swerve.commands.AlignToAprilTag;

public class AutonCommands {

    public static void setupDefaultCommand() {}

    public static Command rightStationTop() {
        return spinLauncher(IntakeCommands.bumpTopSpinUp()).andThen(launch(), stopMotors());
    }

    /* These 3 commands have not been mapped to the operator gamepad */
    public static Command firstShot() {
        return spinLauncher(IntakeCommands.firstShot()).andThen(launch(), stopMotors());
    }

    public static Command coolShot() {
        return spinLauncher(IntakeCommands.coolShot()).andThen(launch(), stopMotors());
    }

    public static Command hybridShot() {
        return spinLauncher(IntakeCommands.hybridShot()).andThen(launch(), stopMotors());
    }

    public static Command secondShot() {
        return spinLauncher(IntakeCommands.secondShot()).andThen(launch(), stopMotors());
    }

    public static Command cleanShot() {
        return spinLauncher(IntakeCommands.cleanShot()).andThen(launch(), stopMotors());
    }

    public static Command angleThirdShot() {
        return spinLauncher(IntakeCommands.angleThirdShot()).andThen(launch(), stopMotors());
    }

    public static Command thirdShotBalance() {
        return spinLauncher(IntakeCommands.thirdShotBalance()).andThen(launch(), stopMotors());
    }

    /** Goes to 0 */
    private static Command homeSystems() {
        return FourBarCommands.autonHome().alongWith(ElevatorCommands.autonSafeHome());
    }

    private static Command spinLauncher(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime);
    }

    public static Command launch() {
        return IntakeCommands.autoLaunch().withTimeout(AutonConfig.launchTime);
    }

    public static Command stopMotors() {
        return IntakeCommands.stopAllMotors().withTimeout(AutonConfig.stopTime);
    }

    public static Command retractIntake() {
        return homeSystems().alongWith(IntakeCommands.stopAllMotors());
    }

    public static Command intakeCube() {
        return new CubeIntake()
                .alongWith(ElevatorCommands.autonCubeIntake(), FourBarCommands.cubeIntake());
    }

    public static Command intakeCone() {
        return new ConeIntake()
                .alongWith(
                        ElevatorCommands.coneStandingIntake(), FourBarCommands.coneStandingIntake())
                .withName("AutonStandingCone");
    }

    public static Command stopElevator() {
        return new RunCommand(() -> Robot.elevator.stop(), Robot.elevator).withTimeout(0.1);
    }

    public static Command coneMid() {
        return OperatorCommands.coneMid()
                .withTimeout(1.1)
                .andThen(IntakeCommands.autoEject().withTimeout(.01))
                .andThen(retractIntake().withTimeout(1.1));
    }

    public static Command coneTop() {
        return OperatorCommands.coneTop()
                .withTimeout(1.7)
                .andThen(IntakeCommands.autoEject().withTimeout(.1))
                .andThen(retractIntake().withTimeout(1.7));
    }

    public static Command simpleLaunchCube() {
        return OperatorCommands.cubeTop()
                .withTimeout(0.5)
                .andThen(IntakeCommands.launch())
                .withTimeout(2)
                .andThen(IntakeCommands.stopAllMotors().withTimeout(0.01));
    }

    public static Command alignToGridHigh() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(IntakeCommands.topCubeSpinUp())
                .alongWith(ElevatorCommands.cubeTop())
                .withTimeout(1)
                .andThen(AutonCommands.launch(), AutonCommands.stopMotors())
                .andThen(AutonCommands.retractIntake());
    }

    public static Command alignToGridMid() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(IntakeCommands.midCubeSpinUp())
                .alongWith(ElevatorCommands.cubeMid())
                .withTimeout(1)
                .andThen(AutonCommands.launch(), AutonCommands.stopMotors())
                .andThen(AutonCommands.retractIntake());
    }

    public static Command alignToGridLowCube() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(IntakeCommands.hybridShot())
                .withTimeout(1)
                .andThen(AutonCommands.launch(), AutonCommands.stopMotors())
                .andThen(AutonCommands.retractIntake());
    }

    public static Command alignToGridLowCone() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(ElevatorCommands.coneFloorGoal())
                .alongWith(FourBarCommands.coneFloorGoal())
                .withTimeout(1)
                .andThen(IntakeCommands.floorEject().withTimeout(0.1))
                .andThen(AutonCommands.retractIntake());
    }
}
