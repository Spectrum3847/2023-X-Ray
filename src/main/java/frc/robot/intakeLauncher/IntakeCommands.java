package frc.robot.intakeLauncher;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class IntakeCommands {

    public static void setupDefaultCommand() {
        Robot.intake.setDefaultCommand(stopAllMotors());
    }

    public static Command intake() {
        return setSpeeds(
                Intake.config.lowerIntakeSpeed,
                Intake.config.frontIntakeSpeed,
                Intake.config.launcherIntakeSpeed);
    }

    public static Command eject() {
        return setIntakeRollers(-1.0, -1.0, -0.2);
    }

    public static Command spinUpLauncher() {
        return setIntakeRollers(0.1, 1.0, 1.0);
    }

    public static Command launch() {
        return setIntakeRollers(-1.0, 1.0, 1.0);
    }

    public static Command setSpeeds(double lower, double upper, double launcher) {
        return new RunCommand(
                () -> Robot.intake.setRollerSpeeds(lower, upper, launcher), Robot.intake);
    }

    public static Command holdPosition() {
        return new RunCommand(() -> Robot.intake.holdRollerPositions(), Robot.intake);
    }

    public static Command setIntakeRollers(double lower, double upper, double launcher) {
        return new RunCommand(() -> Robot.intake.setRollers(lower, upper, launcher), Robot.intake);
    }

    public static Command stopAllMotors() {
        return new RunCommand(() -> Robot.intake.stopAll(), Robot.intake);
    }
}
