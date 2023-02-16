package frc.robot.trajectories.commands;

import frc.robot.trajectories.TrajectoriesConfig;

public class PositionPaths {
    public static FollowOnTheFlyPath grid1Left() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneTTYPosition);
    }

    public static FollowOnTheFlyPath grid1Middle() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.cubeTYPosition);
    }

    public static FollowOnTheFlyPath grid1Right() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneTBYPosition);
    }

    public static FollowOnTheFlyPath grid2Left() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneMTYPosition);
    }

    public static FollowOnTheFlyPath grid2Middle() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.cubeMYPosition);
    }

    public static FollowOnTheFlyPath grid2Right() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneMBYPosition);
    }

    public static FollowOnTheFlyPath grid3Left() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneBTYPosition);
    }

    public static FollowOnTheFlyPath grid3Middle() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.cubeBYPosition);
    }

    public static FollowOnTheFlyPath grid3Right() {
        return new GeneratePathForScoring().GeneratePathScoring(TrajectoriesConfig.coneBBYPosition);
    }
}
