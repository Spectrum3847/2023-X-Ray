// package frc.robot.trajectories.commands;

// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.trajectories.TrajectoriesConfig;

// public class PositionPaths {
//     static double yPos;

//     public static Command grid1Left() {
//         return toGrid(0).andThen(toGrid(0));
//     }

//     public static Command grid1Middle() {
//         return toGrid(1).andThen(toGrid(1));
//     }

//     public static Command grid1Right() {
//         return toGrid(2).andThen(toGrid(2));
//     }

//     public static Command grid2Left() {
//         return toGrid(3).andThen(toGrid(3));
//     }

//     public static Command grid2Middle() {
//         return toGrid(4).andThen(toGrid(4));
//     }

//     public static Command grid2Right() {
//         return toGrid(5).andThen(toGrid(5));
//     }

//     public static Command grid3Left() {
//         return toGrid(6).andThen(toGrid(6));
//     }

//     public static Command grid3Middle() {
//         return toGrid(7).andThen(toGrid(7));
//     }

//     public static Command grid3Right() {
//         return toGrid(8).andThen(toGrid(8));
//     }

//     /**
//      * @param index 0-8 representing the 9 different scoring locations
//      * @return path to scoring location
//      */
//     private static FollowOnTheFlyPath toGrid(int index) {
//         yPos = TrajectoriesConfig.blueYPositions[index];
//         if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
//             yPos = TrajectoriesConfig.fieldWidth - TrajectoriesConfig.blueYPositions[8 - index];
//         }
//         return new GeneratePathForScoring().GeneratePathScoring(yPos);
//     }
// }
