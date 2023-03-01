package frc.robot.trajectories.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.trajectories.TrajectoriesConfig;

public class PositionPaths {
    static double yPos;

    public static FollowOnTheFlyPath grid1Left() {
        yPos = TrajectoriesConfig.blueYPositions[0];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[8];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid1Middle() {
        yPos = TrajectoriesConfig.blueYPositions[1];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[7];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid1Right() {
        yPos = TrajectoriesConfig.blueYPositions[2];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[6];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Left() {
        yPos = TrajectoriesConfig.blueYPositions[3];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[5];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Middle() {
        yPos = TrajectoriesConfig.blueYPositions[4];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[4];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid2Right() {
        yPos = TrajectoriesConfig.blueYPositions[5];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[3];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Left() {
        yPos = TrajectoriesConfig.blueYPositions[6];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[2];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Middle() {
        yPos = TrajectoriesConfig.blueYPositions[7];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[1];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }

    public static FollowOnTheFlyPath grid3Right() {
        yPos = TrajectoriesConfig.blueYPositions[8];
        if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
            yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[0];
        }
        return new GeneratePathForScoring().GeneratePathScoring(yPos);
    }
}
