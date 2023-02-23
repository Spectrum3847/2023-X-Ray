package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.commands.OperatorCommands;

public class AutonCommands {
    public static Command intakeCube() {
        return OperatorCommands.cubeIntake();
    }

    public static Command retractIntake() {
        return IntakeCommands.stopAllMotors().andThen(OperatorCommands.homeSystems());
    }

    public static Command closeLaunchCube() {
        return IntakeCommands.fullSpinUp()
                .withTimeout(0.2)
                .andThen(IntakeCommands.launch())
                .withTimeout(0.3)
                .andThen(IntakeCommands.stopAllMotors());
    }

    public static Command farLaunchCube() {
        return closeLaunchCube();
    }

    public static Command simpleLaunchCube() {
        return OperatorCommands.cubeTop()
                .withTimeout(0.5)
                .andThen(IntakeCommands.launch())
                .withTimeout(2)
                .andThen(IntakeCommands.stopAllMotors().withTimeout(0.01));
    }

    public static Command autoBalance() {
        return null;
    }
}
