package frc.robot.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;

public class IntakeCommands {

    public static void setupDefaultCommand() {
        Robot.intake.setDefaultCommand(stopAllMotors());
    }

    public static Command intake() {
        return setIntakeRollers(1.0, 0.75, -1.0);
    }

    public static Command eject() {
        return setIntakeRollers(-1.0, -1.0, -0.4);
    }

    public static Command launch() {
        return setIntakeRollers(1.0, 1.0, 1.0);
    }

    public static Command setIntakeRollers(double lower, double upper, double launcher) {
        return new RunCommand(() -> Robot.intake.setRollers(lower, upper, launcher), Robot.intake);
    }

    public static Command stopAllMotors() {
        return new RunCommand(() -> Robot.intake.stopAll(), Robot.intake);
    }
}
