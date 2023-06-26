package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.auton.AutonConfig;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.swerve.commands.AlignToAprilTag;
import frc.robot.swerve.commands.DriveToCubeNode;
import frc.robot.swerve.commands.SwerveCommands;
import frc.robot.swerve.commands.SwerveDrive;
import java.util.function.DoubleSupplier;

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

    public static Command hybridShotFast() {
        return spinLauncherMidSpeed(IntakeCommands.hybridShot()).andThen(launch(), stopMotors());
    }

    public static Command coolShotFast() {
        return spinLauncherMidSpeed(IntakeCommands.coolShot()).andThen(launch(), stopMotors());
    }

    public static Command secondShotLaunch() {
        return launch().andThen(stopMotors());
    }

    public static Command secondShot() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(spinLauncher(IntakeCommands.secondShot()).andThen(launch(), stopMotors()));
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

    public static Command cubeMidEject() {
        return spinLauncher(IntakeCommands.midCubeSpinUp()).andThen(launch(), stopMotors());
    }

    /** Goes to 0 */
    private static Command homeSystems() {
        return FourBarCommands.autonHome()
                .withTimeout(1.5)
                .alongWith(ElevatorCommands.autonSafeHome());
    }

    private static Command spinLauncher(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime);
    }

    private static Command spinLauncherFast(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime - 0.4);
    }

    private static Command spinLauncherMidSpeed(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime - 0.25);
    }

    private static Command spinLauncherForHigh(Command spinCommand) {
        return spinCommand.withTimeout(AutonConfig.spinUpTime - 0.3);
    }

    public static Command launch() {
        return IntakeCommands.autoLaunch().withTimeout(AutonConfig.launchTime);
    }

    public static Command launchFast() {
        return IntakeCommands.autoLaunch().withTimeout(AutonConfig.launchTime - .15);
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
                        ElevatorCommands.autonConeStandingIntake(),
                        FourBarCommands.coneStandingIntake())
                .withName("AutonStandingCone");
    }

    public static Command stopElevator() {
        return new RunCommand(() -> Robot.elevator.stop(), Robot.elevator).withTimeout(0.1);
    }

    public static Command coneMid() {
        return OperatorCommands.coneMid()
                .withTimeout(1.1)
                .andThen(IntakeCommands.autoEject().withTimeout(.1));
    }

    public static Command coneMidFull() {
        return OperatorCommands.coneMid()
                .withTimeout(1.1)
                .andThen(IntakeCommands.autoEject().withTimeout(.1))
                .andThen(retractIntake().withTimeout(1.1));
    }

    public static Command coneTop() {
        return OperatorCommands.coneTop()
                .withTimeout(1.7)
                .andThen(IntakeCommands.autoEject().withTimeout(.1));
    }

    public static Command cubeMidSpinUp() {
        return ElevatorCommands.cubeMid();
    }

    public static Command cubeHybridSpinUp() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(spinLauncher(IntakeCommands.coolShot()));
    }

    public static Command simpleLaunchCube() {
        return OperatorCommands.cubeTop()
                .withTimeout(0.5)
                .andThen(IntakeCommands.launch())
                .withTimeout(2)
                .andThen(IntakeCommands.stopAllMotors().withTimeout(0.01));
    }

    public static Command alignToGridMid() {
        return new DriveToCubeNode(0)
                .alongWith(ElevatorCommands.cubeMid())
                .withTimeout(0.75)
                .andThen(spinLauncherFast(IntakeCommands.midCubeSpinUp()))
                .andThen(launch(), stopMotors());
    }

    public static Command alignToGridHigh() {
        return new DriveToCubeNode(0)
                .alongWith(ElevatorCommands.cubeTop())
                .withTimeout(0.75)
                .andThen(spinLauncherFast(IntakeCommands.topCubeSpinUp()))
                .andThen(launch(), stopMotors());
    }

    public static Command alignToGridMidFast() {
        return new DriveToCubeNode(0)
                .alongWith(ElevatorCommands.cubeMid())
                .withTimeout(0.45)
                .andThen(spinLauncherFast(IntakeCommands.midCubeSpinUp()))
                .andThen(launch(), stopMotors());
    }

    public static Command alignToGridHighFast() {
        return new DriveToCubeNode(0)
                .alongWith(ElevatorCommands.cubeTop())
                .withTimeout(0.45)
                .andThen(spinLauncherFast(IntakeCommands.topCubeSpinUp()))
                .andThen(launch(), stopMotors());
    }

    public static Command alignToGridLowCube() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(IntakeCommands.hybridShot())
                .withTimeout(1)
                .andThen(AutonCommands.launch(), AutonCommands.stopMotors());
    }

    public static Command alignToGridLowCone() {
        return new AlignToAprilTag(() -> -0.75, 0)
                .withTimeout(1)
                .alongWith(ElevatorCommands.coneFloorGoal())
                .alongWith(FourBarCommands.coneFloorGoal())
                .withTimeout(1)
                .andThen(IntakeCommands.floorEject().withTimeout(0.2));
    }

    public static Command autonConeFloorGoalPostion() {
        return ElevatorCommands.coneFloorGoal().alongWith(FourBarCommands.coneFloorGoal());
    }

    public static Command ejectCone() {
        return IntakeCommands.floorEject();
    }

    public static Command autonConeFloorGoal() {
        return ElevatorCommands.coneFloorGoal()
                .alongWith(
                        FourBarCommands.coneFloorGoal(),
                        new WaitCommand(0.20).andThen(IntakeCommands.floorEject()))
                .withName("AutonConeFloorGoal");
    }

    public static Command aimPilotDrive(double goalAngleRadians) {
        return aimPilotDrive(() -> goalAngleRadians);
    }

    /** Reset the Theata Controller and then run the SwerveDrive command and pass a goal Supplier */
    public static Command aimPilotDrive(DoubleSupplier goalAngleSupplierRadians) {
        return SwerveCommands.resetTurnController()
                .andThen(
                        new SwerveDrive(
                                () -> 0,
                                () -> 0,
                                () ->
                                        Robot.swerve.calculateRotationController(
                                                goalAngleSupplierRadians)))
                .withName("AutoAimPilotDrive");
    }

    public static Command faceForward() {
        return aimPilotDrive(Math.PI);
    }

    public static Command faceBackward() {
        return aimPilotDrive(0);
    }
}
