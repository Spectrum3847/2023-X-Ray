package frc.robot.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import java.util.function.DoubleSupplier;

// above all copied from PilotCommands.java

public class ElevatorCommands {
    public static void setupDefaultCommand() {
        Robot.elevator.setDefaultCommand(stop());
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

    // below doesn't work:((
    public static Command setMMPosition(double positionInches) {
        double posMeters = Elevator.inchesToMeters(positionInches);
        double posFalcon = Elevator.metersToFalcon(posMeters, 4.03391, 7.75);
        RobotTelemetry.print("running: " + posFalcon);
        return new RunCommand(() -> Robot.elevator.setMMPosition(posFalcon), Robot.elevator);
        // set diameter to 1.2815, gear ratio to 62/8
    }

    public static Command setEncoder(double position) {
        return new RunCommand(() -> Robot.elevator.setEncoder(position), Robot.elevator);
    }

    public static Command resetEncoder() {
        return setEncoder(0);
    }
}
