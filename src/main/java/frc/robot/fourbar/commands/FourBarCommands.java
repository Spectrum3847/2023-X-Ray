package frc.robot.fourbar.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Robot;
import frc.robot.fourbar.FourBar;
import java.util.function.DoubleSupplier;

public class FourBarCommands {
    public static void setupDefaultCommand() {
        Robot.fourBar.setDefaultCommand(
                new FourBarHoldPosition().withName("FourBarDefaultCommand"));
    }

    public static Command coastMode() {
        return new StartEndCommand(
                        () -> Robot.fourBar.setBrakeMode(false),
                        () -> Robot.fourBar.setBrakeMode(true),
                        Robot.fourBar)
                .ignoringDisable(true);
    }

    public static Command zeroFourBarRoutine() {
        return new ZeroFourBarRoutine().withName("ZeroFourBar");
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

    public static Command coneIntake() {
        return setMMPercent(FourBar.config.coneIntake);
    }

    public static Command coneStandingIntake() {
        return setMMPercent(FourBar.config.coneStandingIntake);
    }

    public static Command coneFloorGoal() {
        return setMMPercent(FourBar.config.coneHybrid);
    }

    public static Command coneMid() {
        return setMMPercent(FourBar.config.coneMid);
    }

    public static Command coneTop() {
        return setMMPercent(FourBar.config.coneTop);
    }

    public static Command coneShelf() {
        return setMMPercent(FourBar.config.coneShelf);
    }

    public static Command cubeIntake() {
        return setMMPercent(FourBar.config.cubeIntake);
    }

    public static Command cubeFloorGoal() {
        return setMMPercent(FourBar.config.cubeHybrid);
    }

    public static Command cubeMid() {
        return setMMPercent(FourBar.config.cubeMid);
    }

    public static Command cubeTop() {
        return setMMPercent(FourBar.config.cubeTop);
    }

    public static Command home() {
        return setMMPercent(0);
    }

    public static Command autonHome() {
        return setMMPercent(1);
    }

    public static Command setMMPosition(double position) {
        return new RunCommand(() -> Robot.fourBar.setMMPosition(position), Robot.fourBar);
    }

    public static Command stop() {
        return new RunCommand(() -> Robot.fourBar.stop(), Robot.fourBar);
    }
}
