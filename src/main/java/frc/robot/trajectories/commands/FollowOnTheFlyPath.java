// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.trajectories.commands;

// import com.pathplanner.lib.PathConstraints;
// import com.pathplanner.lib.PathPlanner;
// import com.pathplanner.lib.PathPlannerTrajectory;
// import com.pathplanner.lib.PathPoint;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Robot;
// import frc.robot.trajectories.TrajectoriesConfig;
// import java.util.LinkedList;

// public class FollowOnTheFlyPath extends CommandBase {
//     public static boolean OTF;
//     private LinkedList<PathPoint> endPoints = new LinkedList<>();
//     private PathPlannerTrajectory path;
//     private PathPlannerTrajectory pathToGetSecondPoints;

//     private double maxVelocity;
//     private double maxAcceleration;

//     private double startXPos;
//     private double startYPos;
//     private double startHeading;
//     private double startRotation;
//     private double startVelocity;
//     private double secondXPos;
//     private double secondYPos;

//     private Command pathFollowingCommmand = null;

//     /*Creates a new GeneratePath.
//      * * @param firstPoints*/
//     public FollowOnTheFlyPath(LinkedList<PathPoint> fullPath) {
//         endPoints = new LinkedList<PathPoint>();
//         endPoints = fullPath;
//         OTF = false;
//         // Use addRequirements() here to declare subsystem dependencies.
//         addRequirements(Robot.swerve);
//     }

//     // Called when the command is initially scheduled.
//     @Override
//     @SuppressWarnings("unchecked")
//     public void initialize() {
//         Robot.vision.resetEstimatedPose();
//         OTF = true;
//         LinkedList<PathPoint> fullPath = new LinkedList<>();
//         fullPath = (LinkedList<PathPoint>) endPoints.clone();

//         double xPositions[] = new double[] {};

//         maxVelocity = TrajectoriesConfig.kGenPathMaxSpeed;
//         maxAcceleration = TrajectoriesConfig.kGenPathMaxAccel;

//         startXPos = Robot.pose.getPosition().getX();
//         startYPos = Robot.pose.getPosition().getY();
//         startHeading = Robot.swerve.getFieldRelativeHeading().getDegrees();
//         startRotation = Robot.swerve.getRotation().getDegrees();
//         startVelocity = Robot.swerve.getFieldRelativeMagnitude();

//         double constantRotation;
//         double constantHeading;
//         double constantYPos;

//         constantRotation = TrajectoriesConfig.constantBlueRotation;
//         constantHeading = TrajectoriesConfig.constantBlueHeading;
//         constantYPos = TrajectoriesConfig.bumpYPosition;
//         xPositions = TrajectoriesConfig.blueXPositions;

//         /*if (DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
//             constantRotation = TrajectoriesConfig.constantRedRotation;
//             constantHeading = TrajectoriesConfig.constantRedHeading;
//             xPositions = TrajectoriesConfig.redXPositions;
//         }*/

//         if (startYPos < TrajectoriesConfig.changeYPositionLine
//                 && DriverStation.getAlliance().equals(DriverStation.Alliance.Blue)) {
//             for (int i = 0; i < xPositions.length; i++) {
//                 fullPath.set(
//                         i,
//                         new PathPoint(
//                                 new Translation2d(xPositions[i], constantYPos),
//                                 Rotation2d.fromDegrees(constantHeading),
//                                 Rotation2d.fromDegrees(constantRotation)));
//                 if (i == 2) {
//                     fullPath.set(
//                             i,
//                             new PathPoint(
//                                     new Translation2d(xPositions[i], constantYPos),
//                                     Rotation2d.fromDegrees(constantHeading),
//                                     Rotation2d.fromDegrees(constantRotation),
//                                     TrajectoriesConfig.kGenPathBumpSpeed));
//                 }
//             }
//         } else if (startYPos
//                         > TrajectoriesConfig.fieldWidth - TrajectoriesConfig.changeYPositionLine
//                 && DriverStation.getAlliance().equals(DriverStation.Alliance.Red)) {
//             for (int i = 0; i < xPositions.length; i++) {
//                 fullPath.set(
//                         i,
//                         new PathPoint(
//                                 new Translation2d(
//                                         xPositions[i],
//                                         TrajectoriesConfig.fieldWidth - constantYPos),
//                                 Rotation2d.fromDegrees(constantHeading),
//                                 Rotation2d.fromDegrees(constantRotation)));
//                 if (i == 2) {
//                     fullPath.set(
//                             i,
//                             new PathPoint(
//                                     new Translation2d(
//                                             xPositions[i],
//                                             TrajectoriesConfig.fieldWidth - constantYPos),
//                                     Rotation2d.fromDegrees(constantHeading),
//                                     Rotation2d.fromDegrees(constantRotation),
//                                     TrajectoriesConfig.kGenPathBumpSpeed));
//                 }
//             }
//         }

//         for (int i = fullPath.size() - 3; i >= 0; i--) {
//             if (xPositions[i] > startXPos) {
//                 fullPath.remove(i);
//             }
//         }

//         pathToGetSecondPoints =
//                 PathPlanner.generatePath(
//                         new PathConstraints(maxVelocity, maxAcceleration),
//                         fullPath // position, heading(direction of travel),
//                         // holonomic
//                         // rotation
//                         ); // This is only used to get the second points of the OTF path

//         secondXPos = pathToGetSecondPoints.getInitialPose().getX();
//         secondYPos = pathToGetSecondPoints.getInitialPose().getY();

//         if (startVelocity == 0) {
//             startHeading = getStoppedHeading(secondXPos, secondYPos);
//         }

//         fullPath.add(
//                 0,
//                 new PathPoint(
//                         new Translation2d(startXPos, startYPos),
//                         Rotation2d.fromDegrees(startHeading),
//                         Rotation2d.fromDegrees(startRotation),
//                         startVelocity) // position, heading(direction of
//                 // travel), holonomic rotation,
//                 // velocity override
//                 );

//         path =
//                 PathPlanner.generatePath(
//                         new PathConstraints(maxVelocity, maxAcceleration),
//                         fullPath // position, heading(direction of travel),
//                         // holonomic
//                         // rotation
//                         );

//         pathFollowingCommmand = PathBuilderEstimatedPose.pathBuilder.followPath(path);
//         pathFollowingCommmand.initialize();
//     }

//     // Called every time the scheduler runs while the command is scheduled.
//     @Override
//     public void execute() {
//         pathFollowingCommmand.execute();
//     }

//     // Called once the command ends or is interrupted.
//     @Override
//     public void end(boolean interrupted) {
//         OTF = false;
//         pathFollowingCommmand.end(interrupted);
//         Robot.swerve.odometry.resetOdometry(
//                 Robot.pose.getPosition()); // Resets odometry position to estimated position
//     }

//     // Returns true when the command should end.
//     @Override
//     public boolean isFinished() {
//         return pathFollowingCommmand.isFinished();
//     }

//     private double getStoppedHeading(double secondXPos, double secondYPos) {
//         Translation2d heading =
//                 new Translation2d(secondXPos, secondYPos)
//                         .minus(new Translation2d(startXPos, startYPos));
//         return heading.getAngle().getDegrees();
//     }
// }
