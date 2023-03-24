package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;

public class IntakeCommands {

    public static void setupDefaultCommand() {
        Robot.intake.setDefaultCommand(stopAllMotors().withTimeout(1).andThen(new holdCone()));
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

    public static Command eject() {
        return setVelocities(
                Intake.config.lowerEjectSpeed,
                Intake.config.frontEjectSpeed,
                Intake.config.launcherEjectSpeed);
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
        // testing using shuffleboard
        return new CubeLaunchTest(Intake.config.frontBumpTopSpeed);
        /* regular method */

        // return setVelocities(
        //         Intake.config.lowerSpinUpSpeed,
        //         Intake.config.frontBumpTopSpeed,
        //         Intake.config.launcherBumpTopSpeed);
    }

    public static Command communityTopSpinUp() {

        return new CubeLaunchTest(Intake.config.frontCommunityTopSpeed);
        /*return setVelocities(
        Intake.config.lowerSpinUpSpeed,
        Intake.config.frontCommunityMidSpeed,
        Intake.config.launcherCommunityMidSpeed);*/
    }

    public static Command behindStationMidSpinUp() {

        return new CubeLaunchTest(Intake.config.frontBehindRStationMidSpeed);
        /*return setVelocities(
        Intake.config.lowerSpinUpSpeed,
        Intake.config.frontBehindRStationMidSpeed,
        Intake.config.launcherBehindRStationMidSpeed);*/
    }

    public static Command behindStationMidSpinUpBalance() {

        return new CubeLaunchTest(Intake.config.frontBehindRStationMidSpeedBalance);
        /*return setVelocities(
        Intake.config.lowerSpinUpSpeed,
        Intake.config.frontBehindRStationMidSpeed,
        Intake.config.launcherBehindRStationMidSpeed);*/
    }

    public static Command onStationTopSpinUp() {

        return new CubeLaunchTest(Intake.config.frontBehindMStationTopSpeed);
        // return setVelocities(
        //         Intake.config.lowerSpinUpSpeed,
        //         Intake.config.frontOnStationTopSpeed,
        //         Intake.config.launcherOnStationTopSpeed);
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

    public static Command setVelocities(double lower, double upper, double launcher) {
        return new RunCommand(
                () -> Robot.intake.setVelocities(lower, upper, launcher), Robot.intake);
    }

    public static Command setIntakeRollers(double lower, double upper, double launcher) {
        return new RunCommand(
                () -> Robot.intake.setPercentOutputs(lower, upper, launcher), Robot.intake);
    }

    public static Command stopAllMotors() {
        return new RunCommand(() -> Robot.intake.stopAll(), Robot.intake);
    }
}
