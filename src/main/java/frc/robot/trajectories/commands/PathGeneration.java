package frc.robot.trajectories.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class PathGeneration {
    // More complex path with holonomic rotation. Non-zero starting velocity of 2 m/s. Max velocity
    // of 4 m/s and max accel of 3 m/s^2
    public static PathPlannerTrajectory generatedPath =
            PathPlanner.generatePath(
                    new PathConstraints(4, 3),
                    new PathPoint(
                            new Translation2d(1.0, 1.0),
                            Rotation2d.fromDegrees(0),
                            Rotation2d.fromDegrees(0),
                            0), // position, heading(direction of travel), holonomic rotation,
                    // velocity override
                    new PathPoint(
                            new Translation2d(2.0, 1.0),
                            Rotation2d.fromDegrees(0),
                            Rotation2d.fromDegrees(
                                    0)) // position, heading(direction of travel), holonomic
                    // rotation
                    );
}
