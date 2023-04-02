package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.elevator.commands.ElevatorDelay;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.Intake;

public class IntakeCommands {

    public static void setupDefaultCommand() {
        Robot.intake.setDefaultCommand(stopAllMotors().withTimeout(1).andThen(new HoldCone()));
    }

    public static Command slowIntake() {
        return setVelocities(
                Intake.config.lowerSlowSpeed,
                Intake.config.frontSlowSpeed,
                Intake.config.launcherSlowSpeed);
    }

    public static Command intake() {
        return setVelocities(
                Intake.config.lowerIntakeSpeed,
                Intake.config.frontIntakeSpeed,
                Intake.config.launcherIntakeSpeed);
    }

    public static Command fullIntake() {
        return setIntakeRollers(1.0, 1.0, 0);
    }

    public static Command eject() {
        return setVelocities(
                        Intake.config.lowerEjectSpeed,
                        Intake.config.frontEjectSpeed,
                        Intake.config.launcherEjectSpeed)
                .alongWith(FourBarCommands.home().alongWith(new ElevatorDelay(0, 30)))
                .finallyDo(
                        (b) ->
                                ElevatorCommands.home()
                                        .alongWith(FourBarCommands.home())
                                        .schedule());
    }

    public static Command floorEject() {
        return setVelocities(
                Intake.config.lowerFloorSpeed,
                Intake.config.frontFloorSpeed,
                Intake.config.launcherFloorSpeed);
    }

    public static Command midCubeSpinUp() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontMidCubeSpeed,
                Intake.config.launcherMidCubeSpeed);
    }

    public static Command topCubeSpinUp() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontTopCubeSpeed,
                Intake.config.launcherTopCubeSpeed);
    }

    public static Command behindChargeStationSpinUp() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontChargeStationLaunchSpeed,
                Intake.config.launcherChargeStationLaunchSpeed);
    }

    public static Command autoMidSpinUp() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontAutoMidSpeed,
                Intake.config.launcherAutoMidSpeed);
    }

    public static Command bumpTopSpinUp() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontBumpTopSpeed,
                Intake.config.launcherBumpTopSpeed);
    }

    public static Command firstShot() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontFirstShotSpeed,
                Intake.config.launcherFirstShotSpeed);
    }

    public static Command secondShot() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontSecondShotSpeed,
                Intake.config.launcherSecondShotSpeed);
    }

    public static Command angleThirdShot() {
        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontAngleThirdShotSpeed,
                Intake.config.launcherAngleThirdShotSpeed);
    }

    public static Command thirdShotBalance() {

        return setVelocities(
                Intake.config.lowerSpinUpSpeed,
                Intake.config.frontThirdShotBalanceSpeed,
                Intake.config.launcherThirdShotBalanceSpeed);
    }

    public static Command launch() {
        return new RunCommand(() -> Robot.intake.launch(), Robot.intake);
    }

    public static Command manualSpinUpLauncher() {
        return setIntakeRollers(0.1, 1.0, 1.0);
    }

    public static Command manualLaunch() {
        return setIntakeRollers(-1.0, 1.0, 1.0);
    }

    public static Command setVelocities(double lower, double front, double launcher) {
        return new RunCommand(
                () -> Robot.intake.setVelocities(lower, front, launcher), Robot.intake);
    }

    public static Command setIntakeRollers(double lower, double front, double launcher) {
        return new RunCommand(
                () -> Robot.intake.setPercentOutputs(lower, front, launcher), Robot.intake);
    }

    public static Command stopAllMotors() {
        return new RunCommand(() -> Robot.intake.stopAll(), Robot.intake);
    }
}
