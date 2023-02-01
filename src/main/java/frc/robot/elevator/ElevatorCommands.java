package frc.robot.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Robot;
import java.util.function.DoubleSupplier;

// above all copied from PilotCommands.java

public class ElevatorCommands {
    public static void setupDefaultCommand() {
        Robot.elevator.setDefaultCommand(elevatorManualControl());
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
        return new RunCommand(() -> Robot.elevator.setMMPosition(position), Robot.elevator);
    }

    public static Command zeroElevator() {
        return new RunCommand(() -> Robot.elevator.zeroElevator(), Robot.elevator);
    }

    public static Command elevatorManualControl() {
        return setOutput(() -> Robot.operatorGamepad.getRightFwdPositive() * 0.5);
    }

    public static Command runDownAndZero() {
        return new StartEndCommand(
                () -> Robot.elevator.setManualOutput(-0.1),
                () -> Robot.elevator.zeroElevator(),
                Robot.elevator);
    }

    // // below doesn't work:((
    // public static Command setMMPosition(double positionInches) {
    //     double posMeters = Elevator.inchesToMeters(positionInches);
    //     double posFalcon = Elevator.metersToFalcon(posMeters, 4.03391, 7.75);
    //     RobotTelemetry.print("running: " + posFalcon);
    //     return new RunCommand(() -> Robot.elevator.setMMPosition(posFalcon), Robot.elevator);
    //     // set diameter to 1.2815, gear ratio to 62/8
    // }

    public static Command setEncoder(double position) {
        return new RunCommand(() -> Robot.elevator.setEncoder(position), Robot.elevator);
    }

    public static Command resetEncoder() {
        return setEncoder(0);
    }
}
