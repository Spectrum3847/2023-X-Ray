package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.fourbar.FourBar;
import java.util.function.DoubleSupplier;

// above all copied from PilotCommands.java

public class ElevatorCommands {
    public static Trigger elevatorUp =
            new Trigger(() -> Elevator.falconToInches(Robot.elevator.getPosition()) > 12);

    public static void setupDefaultCommand() {
        Robot.elevator.setDefaultCommand(
                stop().withTimeout(0.25)
                        .andThen(new ElevatorHoldPosition())
                        .withName("ElevatorDefaultCommand"));
    }

    public static void setupElevatorTriggers() {
        elevatorUp = new Trigger(() -> Elevator.falconToInches(Robot.elevator.getPosition()) > 12);
    }

    public static Command stop() {
        return new RunCommand(() -> Robot.elevator.stop(), Robot.elevator).withName("ElevatorStop");
    }

    public static Command coastMode() {
        return new StartEndCommand(
                        () -> Robot.elevator.setBrakeMode(false),
                        () -> Robot.elevator.setBrakeMode(true),
                        Robot.elevator)
                .ignoringDisable(true)
                .withName("ElevatorCoast");
    }

    public static Command setOutput(double value) {
        return new RunCommand(() -> Robot.elevator.setManualOutput(value), Robot.elevator)
                .withName("ElevatorOutput");
    }

    public static Command setOutput(DoubleSupplier value) {
        return new RunCommand(
                        () -> Robot.elevator.setManualOutput(value.getAsDouble()), Robot.elevator)
                .withName("ElevatorOutput");
    }

    private static Command setMMPositionWithDelay(double positionFalconUnits) {
        return new ElevatorDelay(
                Elevator.inchesToFalcon(Elevator.config.safePositionForFourBar),
                positionFalconUnits,
                FourBar.config.safePositionForElevator);
    }

    public static Command setMMPositionFromInches(double inches) {
        return setMMPositionWithDelay(Elevator.inchesToFalcon(inches));
    }

    public static Command coneIntake() {
        return hopElevator(Elevator.config.coneIntake).withName("ElevatorConeIntake");
    }

    public static Command coneStandingIntake() {
        return setMMPositionFromInches(Elevator.config.coneStandingIntake)
                .withName("ElevatorConeStandInt");
    }

    public static Command autonConeStandingIntake() {
        return setMMPositionFromInches(Elevator.config.coneStandingIntake)
                .withTimeout(0.5)
                .withName("AutonElevatorConeStandInt");
    }

    public static Command hopElevator(double inches) {
        return ElevatorCommands.setMMPositionFromInches(Elevator.config.hopHeight)
                .withTimeout(Elevator.config.hopTime)
                .andThen(setMMPositionFromInches(inches))
                .withName("ElevatorHop");
    }

    public static Command hopElevator() {
        return hopElevator(0);
    }

    public static Command coneFloorGoal() {
        return setMMPositionFromInches(Elevator.config.coneHybrid).withName("ElevatorConeFloor");
    }

    public static Command coneMid() {
        return setMMPositionFromInches(Elevator.config.coneMid).withName("ElevatorConeMid");
    }

    public static Command coneTop() {
        return setMMPositionFromInches(Elevator.config.coneTop).withName("ElevatorConeTop");
    }

    public static Command coneShelf() {
        return setMMPositionFromInches(Elevator.config.coneShelf).withName("ElevatorConeShelf");
    }

    public static Command cubeIntake() {
        return hopElevator(Elevator.config.cubeIntake).withName("ElevatorCubeIntake");
    }

    public static Command autonCubeIntake() {
        return hopElevator(Elevator.config.autonCubeIntake)
                .withTimeout(0.5)
                .withName("ElevatorAutonCubeIntake");
    }

    public static Command cubeFloorGoal() {
        return setMMPositionFromInches(Elevator.config.cubeHybrid).withName("ElevatorCubeFloor");
    }

    public static Command cubeMid() {
        return setMMPositionFromInches(Elevator.config.cubeMid).withName("ElevatorCubeMid");
    }

    public static Command cubeTop() {
        return setMMPositionFromInches(Elevator.config.cubeTop).withName("ElevatorCubeTop");
    }

    public static Command safeHome() {
        return new ElevatorDelay(
                        Elevator.config.safePositionForFourBar,
                        0,
                        FourBar.config.safePositionForElevator)
                .withName("ElevatorSafeHome");
    }

    public static Command simpleSafeHome() {
        return new ElevatorDelay(0, FourBar.config.safePositionForElevator);
    }

    public static Command autonSafeHome() {
        return new ElevatorDelay(
                        Elevator.config.safePositionForFourBar,
                        3000,
                        FourBar.config.safePositionForElevator)
                .withName("ElevatorSafeHome");
    }

    /**
     * Be careful using this command. Will not wait for fourbar to be at a safe position before
     * going home
     */
    public static Command home() {
        return setMMPositionWithDelay(0.34).withName("ElevatorHome");
    }

    public static Command zeroElevatorRoutine() {
        return new ZeroElevatorRoutine().withName("Zero Elevator");
    }
}
