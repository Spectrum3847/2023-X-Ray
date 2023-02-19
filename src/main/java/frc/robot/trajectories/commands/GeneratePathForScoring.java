package frc.robot.trajectories.commands;

import com.pathplanner.lib.PathPoint;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.trajectories.TrajectoriesConfig;
import java.util.LinkedList;

public class GeneratePathForScoring {
    public FollowOnTheFlyPath GeneratePathScoring(double finalYPos) {
        LinkedList<PathPoint> finalPoints = new LinkedList<>();

        double constantRotation;
        double constantHeading;
        double constantYPos;
        double lineupXPositionModifier;

        double finalXPos;

        constantRotation = TrajectoriesConfig.constantRotation;
        constantHeading = TrajectoriesConfig.constantHeading;
        lineupXPositionModifier = TrajectoriesConfig.lineupXPositionModifier;

        constantYPos = TrajectoriesConfig.clearYPosition;

        finalXPos = TrajectoriesConfig.finalXPosition;

        for (int i = 0; i < TrajectoriesConfig.xPositions.length; i++) {
            finalPoints.add(
                    new PathPoint(
                            new Translation2d(TrajectoriesConfig.xPositions[i], constantYPos),
                            Rotation2d.fromDegrees(constantHeading),
                            Rotation2d.fromDegrees(constantRotation)));
        }
        finalPoints.add(
                new PathPoint(
                        new Translation2d(finalXPos + lineupXPositionModifier, finalYPos),
                        Rotation2d.fromDegrees(constantHeading),
                        Rotation2d.fromDegrees(constantRotation)));
        finalPoints.add(
                new PathPoint(
                        new Translation2d(finalXPos, finalYPos),
                        Rotation2d.fromDegrees(constantHeading),
                        Rotation2d.fromDegrees(constantRotation)));
        return new FollowOnTheFlyPath(finalPoints);
    }
}
