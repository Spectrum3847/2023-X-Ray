package frc.robot.trajectories;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Robot;
import frc.robot.trajectories.commands.PathBuilder;
import frc.robot.trajectories.commands.PathGeneration;
import java.util.function.BooleanSupplier;

public class TrajectoriesCommands {
    public static BooleanSupplier runPath;

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

    public static Command runGeneratedPathCommand() {
        Command generatePath = TrajectoriesCommands.getGeneratedPath();
        runPath = () -> false;
        if (generatePath != null) {
            generatePath.until(runPath);
        }
        return generatePath;
    }

    public static Command stopGeneratedPath() {
        runPath = () -> true;
        return null;
    }
}
