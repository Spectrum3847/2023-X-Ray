package frc.robot.fourbar;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import java.util.function.DoubleSupplier;

public class FourBarCommands {
    public static void setupDefaultCommand() {
        Robot.fourBar.setDefaultCommand(fourBarManualControl());
    }

    public static Command setManualOutput(double speed) {
        return setManualOutput(speed);
    }

    public static Command setManualOutput(DoubleSupplier speed) {
        return new RunCommand(
                () -> Robot.fourBar.setManualOutput(speed.getAsDouble()), Robot.fourBar);
    }

    public static Command fourBarManualControl() {
        return setManualOutput(() -> Robot.operatorGamepad.getDriveFwdPositive() * 0.1);
    }

    public static Command stop() {
        return new RunCommand(() -> Robot.fourBar.stop(), Robot.fourBar);
    }
}
