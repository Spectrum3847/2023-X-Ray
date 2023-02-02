package frc.robot.trajectories;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;
import frc.robot.trajectories.commands.PathGeneration;

public class TrajectoriesCommands {

    public static Command resetThetaController() {
        return new InstantCommand(() -> Robot.trajectories.resetTheta(), Robot.trajectories);
    }

    public static Command getGeneratedPath() {
        Command path = (Command) PathGeneration.generatedPath;
        if (path != null) {
            return path;
        } else {
            return new PrintCommand("*** GENERATED PATH COMMAND IS NULL ***");
        }
    }

    public static Command runGeneratedPathCommand() {
        Command generatePath = TrajectoriesCommands.getGeneratedPath();
        if (generatePath != null) {
            generatePath.schedule();
        }
        return generatePath;
    }
}
