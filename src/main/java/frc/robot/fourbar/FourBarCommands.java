package frc.robot.fourbar;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.operator.commands.OperatorCommands;
import java.util.function.DoubleSupplier;

public class FourBarCommands {
    public static void setupDefaultCommand() {
        Robot.fourBar.setDefaultCommand(OperatorCommands.manualFourBar());
    }

    public static Command setManualOutput(double speed) {
        return setManualOutput(speed);
    }

    public static Command setManualOutput(DoubleSupplier speed) {
        return new RunCommand(
                () -> Robot.fourBar.setManualOutput(speed.getAsDouble()), Robot.fourBar);
    }

    public static Command setMMPercent(double percent) {
        return new RunCommand(() -> Robot.fourBar.setMMPercent(percent), Robot.fourBar);
    }

    public static Command setConeIntake() {
        return setMMPercent(FourBar.config.coneIntake);
    }

    public static Command setConeMid() {
        return setMMPercent(FourBar.config.coneMid);
    }

    public static Command setConeTop() {
        return setMMPercent(FourBar.config.coneTop);
    }

    public static Command setCubeIntake() {
        return setMMPercent(FourBar.config.cubeIntake);
    }

    public static Command setCubeMid() {
        return setMMPercent(FourBar.config.cubeMid);
    }

    public static Command setCubeTop() {
        return setMMPercent(FourBar.config.cubeTop);
    }

    public static Command setMMPosition(double position) {
        return new RunCommand(() -> Robot.fourBar.setMMPosition(position), Robot.fourBar);
    }

    public static Command stop() {
        return new RunCommand(() -> Robot.fourBar.stop(), Robot.fourBar);
    }
}
