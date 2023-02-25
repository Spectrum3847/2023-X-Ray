package frc.robot.elevator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.fourbar.FourBar;
import java.util.function.DoubleSupplier;

// above all copied from PilotCommands.java

public class ElevatorCommands {
    public static void setupDefaultCommand() {
        Robot.elevator.setDefaultCommand(
                stop().withTimeout(0.25).andThen(new ElevatorHoldPosition()));
    }

    public static Command stop() {
        return new RunCommand(() -> Robot.elevator.stop(), Robot.elevator);
    }

    public static Command setOutput(double value) {
        return new RunCommand(() -> Robot.elevator.setManualOutput(value), Robot.elevator);
    }

    public static Command setOutput(DoubleSupplier value) {
        return new RunCommand(
                () -> Robot.elevator.setManualOutput(value.getAsDouble()), Robot.elevator);
    }

    public static Command setMMPosition(double position) {
        return new ElevatorDelay(
                Elevator.config.safePositionForFourBar,
                position,
                FourBar.config.safePositionForElevator);
    }

    public static Command setMMPositionFromInches(double inches) {
        return setMMPosition(Elevator.inchesToFalcon(inches));
    }

    public static Command coneIntake() {
        return setMMPositionFromInches(Elevator.config.coneIntake);
    }

    public static Command coneStandingIntake() {
        return setMMPositionFromInches(Elevator.config.coneStandingIntake);
    }

    public static Command coneHybrid() {
        return setMMPosition(Elevator.config.coneHybrid);
    }

    public static Command coneMid() {
        return setMMPositionFromInches(Elevator.config.coneMid);
    }

    public static Command coneTop() {
        return setMMPositionFromInches(Elevator.config.coneTop);
    }

    public static Command coneShelf() {
        return setMMPositionFromInches(Elevator.config.coneShelf);
    }

    public static Command cubeIntake() {
        return setMMPositionFromInches(Elevator.config.cubeIntake);
    }

    public static Command cubeMid() {
        return setMMPositionFromInches(Elevator.config.cubeMid);
    }

    public static Command cubeTop() {
        return setMMPositionFromInches(Elevator.config.cubeTop);
    }

    public static Command safeHome() {
        return new ElevatorDelay(
                Elevator.config.safePositionForFourBar, 0, FourBar.config.safePositionForElevator);
    }

    public static Command home() {
        return setMMPosition(0);
    }

    public static Command zeroElevatorRoutine() {
        return new ZeroElevatorRoutine();
    }
}
