package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.Intake;

public class IntakeCommands {

    public static void setupDefaultCommand() {
        Robot.intake.setDefaultCommand(
                stopAllMotors()
                        .withTimeout(1)
                        .andThen(new HoldCone())
                        .withName("IntakeDefaultCommand"));
    }

    public static Command slowIntake() {
        return setVelocities(
                        Intake.config.lowerSlowSpeed,
                        Intake.config.frontSlowSpeed,
                        Intake.config.launcherSlowSpeed)
                .withName("SlowIntake");
    }

    public static Command intake() {
        return setVelocities(
                        Intake.config.lowerIntakeSpeed,
                        Intake.config.frontIntakeSpeed,
                        Intake.config.launcherIntakeSpeed)
                .withName("Intake");
    }

    public static Command fullIntake() {
        return setIntakeRollers(1.0, 1.0, 0).withName("FullIntake");
    }

    public static Command airIntake() {
        return setVelocities(
                        Intake.config.lowerAirIntakeSpeed,
                        Intake.config.frontAirIntakeSpeed,
                        Intake.config.launcherAirIntakeSpeed)
                .withName("AirIntake");
    }

    public static Command eject() {
        return setVelocities(
                        Intake.config.lowerEjectSpeed,
                        Intake.config.frontEjectSpeed,
                        Intake.config.launcherEjectSpeed)
                .alongWith(FourBarCommands.home().alongWith(ElevatorCommands.simpleSafeHome()))
                .withName("Eject")
                .finallyDo(
                        (b) ->
                                ElevatorCommands.safeHome()
                                        .alongWith(FourBarCommands.home())
                                        .withTimeout(1.5)
                                        .schedule());
    }

    public static Command autoEject() {
        return setVelocities(
                        Intake.config.lowerEjectSpeed,
                        Intake.config.frontEjectSpeed,
                        Intake.config.launcherEjectSpeed)
                .alongWith(FourBarCommands.home().alongWith(ElevatorCommands.simpleSafeHome()))
                .withName("Eject");
    }

    public static Command floorEject() {
        return setVelocities(
                        Intake.config.lowerFloorSpeed,
                        Intake.config.frontFloorSpeed,
                        Intake.config.launcherFloorSpeed)
                .withName("FloorEject");
    }

    public static Command cubeFloorLaunch() {
        return setVelocities(
                        Intake.config.lowerFeedSpeed,
                        Intake.config.frontHybridSpeed,
                        Intake.config.launcherHybridSpeed)
                .withName("Cube Floor");
    }

    public static Command midCubeSpinUp() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontMidCubeSpeed,
                        Intake.config.launcherMidCubeSpeed)
                .withName("MidCubeSpin");
    }

    public static Command topCubeSpinUp() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontTopCubeSpeed,
                        Intake.config.launcherTopCubeSpeed)
                .withName("TopCubeSpin");
    }

    public static Command behindChargeStationSpinUp() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontChargeStationLaunchSpeed,
                        Intake.config.launcherChargeStationLaunchSpeed)
                .withName("BehindCSSpin");
    }

    public static Command autoMidSpinUp() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontAutoMidSpeed,
                        Intake.config.launcherAutoMidSpeed)
                .withName("AutoMidSpin");
    }

    public static Command bumpTopSpinUp() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontBumpTopSpeed,
                        Intake.config.launcherBumpTopSpeed)
                .withName("BumpTopSpin");
    }

    public static Command firstShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontFirstShotSpeed,
                        Intake.config.launcherFirstShotSpeed)
                .withName("FirstShot");
    }

    public static Command coolShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontCoolShotSpeed,
                        Intake.config.launcherCoolShotSpeed)
                .withName("CoolShot");
    }

    public static Command hybridShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontHybridSpeed,
                        Intake.config.launcherHybridSpeed)
                .withName("HybridShot");
    }

    public static Command shot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontCoolShotSpeed,
                        Intake.config.launcherCoolShotSpeed)
                .withName("Shot");
    }

    public static Command secondShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontSecondShotSpeed,
                        Intake.config.launcherSecondShotSpeed)
                .withName("SecondShot");
    }

    public static Command cleanShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontSecondShotSpeed,
                        Intake.config.launcherSecondShotSpeed)
                .withName("SecondShot");
    }

    public static Command angleThirdShot() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontAngleThirdShotSpeed,
                        Intake.config.launcherAngleThirdShotSpeed)
                .withName("AngleThirdShot");
    }

    public static Command thirdShotBalance() {
        return setVelocities(
                        Intake.config.lowerSpinUpSpeed,
                        Intake.config.frontThirdShotBalanceSpeed,
                        Intake.config.launcherThirdShotBalanceSpeed)
                .withName("ThirdShotBalance");
    }

    public static Command launch() {
        return new RunCommand(() -> Robot.intake.launch(), Robot.intake)
                .withName("Launch")
                .finallyDo(
                        (b) ->
                                ElevatorCommands.safeHome()
                                        .alongWith(FourBarCommands.home())
                                        .withTimeout(1.0)
                                        .schedule());
    }

    public static Command autoLaunch() {
        return new RunCommand(() -> Robot.intake.launch(), Robot.intake);
    }

    public static Command manualSpinUpLauncher() {
        return setIntakeRollers(0.1, 1.0, 1.0).withName("ManualSpinUp");
    }

    public static Command manualLaunch() {
        return setIntakeRollers(-1.0, 1.0, 1.0).withName("ManualLaunch");
    }

    public static Command setVelocities(double lower, double front, double launcher) {
        return new RunCommand(
                        () -> Robot.intake.setVelocities(lower, front, launcher), Robot.intake)
                .withName("IntakeSetVelocity");
    }

    public static Command setIntakeRollers(double lower, double front, double launcher) {
        return new RunCommand(
                        () -> Robot.intake.setPercentOutputs(lower, front, launcher), Robot.intake)
                .withName("IntakeSetPercentage");
    }

    public static Command stopAllMotors() {
        return new RunCommand(() -> Robot.intake.stopAll(), Robot.intake).withName("IntakeStop");
    }
}
