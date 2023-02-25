package frc.robot.trajectories;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class TrajectoriesCommands {
    public static Command resetThetaController() {
        return new InstantCommand(() -> Robot.trajectories.resetTheta(), Robot.trajectories);
    }
}
