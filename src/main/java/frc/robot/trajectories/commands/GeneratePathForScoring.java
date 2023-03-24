// package frc.robot.trajectories.commands;

// import com.pathplanner.lib.PathPoint;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj.DriverStation;
// import frc.robot.trajectories.TrajectoriesConfig;
// import java.util.LinkedList;

// public class GeneratePathForScoring {
//     // public FollowOnTheFlyPath GeneratePathScoring(double finalYPos) {
//         LinkedList<PathPoint> finalPoints = new LinkedList<>();

//         double constantRotation;
//         double constantHeading;
//         double constantYPos;
//         double lineupXPositionModifier;
//         double xPositions[] = new double[] {};
//         double finalXPos;

//         constantRotation = TrajectoriesConfig.constantBlueRotation;
//         constantHeading = TrajectoriesConfig.constantBlueHeading;
//         constantYPos = TrajectoriesConfig.clearYPosition;
//         lineupXPositionModifier = TrajectoriesConfig.lineupXPositionModifier;
//         xPositions = TrajectoriesConfig.blueXPositions;
//         finalXPos = TrajectoriesConfig.finalBlueXPosition;

//         /*if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
//             finalXPos = TrajectoriesConfig.finalRedXPosition;
//             constantRotation = TrajectoriesConfig.constantRedRotation;
//             constantHeading = TrajectoriesConfig.constantRedHeading;
//             lineupXPositionModifier = -TrajectoriesConfig.lineupXPositionModifier;
//             xPositions = TrajectoriesConfig.redXPositions;
//         }*/

//         if (DriverStation.getAlliance().equals(DriverStation.Alliance.Blue)) {
//             for (int i = 0; i < xPositions.length; i++) {
//                 finalPoints.add(
//                         new PathPoint(
//                                 new Translation2d(xPositions[i], constantYPos),
//                                 Rotation2d.fromDegrees(constantHeading),
//                                 Rotation2d.fromDegrees(constantRotation)));
//             }
//         } else if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
//             for (int i = 0; i < xPositions.length; i++) {
//                 finalPoints.add(
//                         new PathPoint(
//                                 new Translation2d(
//                                         xPositions[i],
//                                         TrajectoriesConfig.fieldWidth - constantYPos),
//                                 Rotation2d.fromDegrees(constantHeading),
//                                 Rotation2d.fromDegrees(constantRotation)));
//             }
//         }
//         finalPoints.add(
//                 new PathPoint(
//                         new Translation2d(finalXPos + lineupXPositionModifier, finalYPos),
//                         Rotation2d.fromDegrees(constantHeading),
//                         Rotation2d.fromDegrees(constantRotation)));
//         finalPoints.add(
//                 new PathPoint(
//                         new Translation2d(finalXPos, finalYPos),
//                         Rotation2d.fromDegrees(constantHeading),
//                         Rotation2d.fromDegrees(constantRotation)));
//         return new FollowOnTheFlyPath(finalPoints);
//     }
// }
