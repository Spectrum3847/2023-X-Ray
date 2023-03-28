package frc.robot.trajectories.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.trajectories.TrajectoriesConfig;

public class PositionPaths {
    static double yPos;

    public static FollowOnTheFlyPath grid1Left() {
        return toGrid(0);
    }

    public static FollowOnTheFlyPath grid1Middle() {
        return toGrid(1);
    }

    public static FollowOnTheFlyPath grid1Right() {
        return toGrid(2);
    }

    public static FollowOnTheFlyPath grid2Left() {
        return toGrid(3);
    }

    public static FollowOnTheFlyPath grid2Middle() {
        return toGrid(4);
    }

    public static FollowOnTheFlyPath grid2Right() {
        return toGrid(5);
    }

    public static FollowOnTheFlyPath grid3Left() {
        return toGrid(6);
    }

    public static FollowOnTheFlyPath grid3Middle() {
        return toGrid(7);
    }

    public static FollowOnTheFlyPath grid3Right() {
        return toGrid(8);
    }

    /**
     * @param index 0-8 representing the 9 different scoring locations
     * @return path to scoring location
     */
    private static FollowOnTheFlyPath toGrid(int index) {
        yPos = TrajectoriesConfig.blueYPositions[index];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[8 - index];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }
}
