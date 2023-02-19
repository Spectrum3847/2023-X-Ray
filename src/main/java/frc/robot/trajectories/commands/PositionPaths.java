package frc.robot.trajectories.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.trajectories.TrajectoriesConfig;

public class PositionPaths {
    static double yPos;

    public static FollowOnTheFlyPath grid1Left() {
        yPos = TrajectoriesConfig.coneTTYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneBBYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid1Middle() {
        yPos = TrajectoriesConfig.cubeTYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.cubeBYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid1Right() {
        yPos = TrajectoriesConfig.coneTBYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneBTYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Left() {
        yPos = TrajectoriesConfig.coneMTYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneMBYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Middle() {
        yPos = TrajectoriesConfig.cubeMYPosition;
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Right() {
        yPos = TrajectoriesConfig.coneMBYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneMTYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Left() {
        yPos = TrajectoriesConfig.coneBTYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneTBYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Middle() {
        yPos = TrajectoriesConfig.cubeBYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.cubeTYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Right() {
        yPos = TrajectoriesConfig.coneBBYPosition;
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.coneTTYPosition;
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }
}
