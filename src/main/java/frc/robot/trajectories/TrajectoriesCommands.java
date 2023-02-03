package frc.robot.trajectories;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;
import frc.robot.trajectories.commands.PathBuilder;
import frc.robot.trajectories.commands.PathGeneration;

public class TrajectoriesCommands {
    public static Command resetThetaController() {
        return new InstantCommand(() -> Robot.trajectories.resetTheta(), Robot.trajectories);
    }

    public static Command getGeneratedPath() {
        PathPlannerTrajectory path = PathGeneration.generatedPath;
        if (path != null) {
            return PathBuilder.pathBuilder.fullAuto(path);
        } else {
            return new PrintCommand("*** GENERATED PATH COMMAND IS NULL ***");
        }
    }

    public static Command runGeneratedPath() {
        Command generatePath = TrajectoriesCommands.getGeneratedPath();
        return generatePath;
    }
}
