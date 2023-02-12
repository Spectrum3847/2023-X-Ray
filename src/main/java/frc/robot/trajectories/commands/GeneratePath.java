// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trajectories.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.trajectories.TrajectoriesConfig;
import java.util.LinkedList;
import java.util.List;

public class GeneratePath extends CommandBase {
    private List<PathPoint> points = new LinkedList<>();

    private double startXPos;
    private double startYPos;
    private double startHeading;
    private double startRotation;
    private double startVelocity = 0;

    private PathPlannerTrajectory path;
    private double maxVelocity = TrajectoriesConfig.kGenPathMaxSpeed;
    private double maxAcceleration = TrajectoriesConfig.kGenPathMaxAccel;

    private double finalXPos = 0;
    private double finalYPos = 0;
    private double finalHeading;
    private double finalRotation;

    private Command pathFollowingCommmand = null;

    /** Creates a new GeneratePath. */
    public GeneratePath() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        points = new LinkedList<>();

        startXPos = Robot.swerve.getPoseMeters().getX();
        // robot.pose.getmeters()
        startYPos = Robot.swerve.getPoseMeters().getY();
        startHeading = Robot.swerve.getHeading().getDegrees();
        startRotation = Robot.pose.getOdometryPose().getRotation().getDegrees();
        startVelocity = Robot.swerve.mSwerveMods[0].getState().speedMetersPerSecond;

        maxVelocity = 2;
        maxAcceleration = 2;

        finalXPos = 0;
        finalYPos = 0;
        finalHeading = 0;
        finalRotation = 0;

        points.add(
                new PathPoint(
                        new Translation2d(startXPos, startYPos),
                        Rotation2d.fromDegrees(startHeading),
                        Rotation2d.fromDegrees(startRotation),
                        startVelocity) // position, heading(direction of
                // travel), holonomic rotation,
                // velocity override
                );
        points.add(
                new PathPoint(
                        new Translation2d(finalXPos, finalYPos),
                        Rotation2d.fromDegrees(finalHeading),
                        Rotation2d.fromDegrees(
                                finalRotation)) // position, heading(direction of travel),
                // holonomic
                // rotation
                );
        /*points.add(
                new PathPoint(
                        new Translation2d(
                                ,
                                TrajectoriesConfig.topFirstYPosition),
                        Rotation2d.fromDegrees(TrajectoriesConfig.topFirstHeading),
                        Rotation2d.fromDegrees(TrajectoriesConfig.constantRotation),
                        0));

        points.add(
                new PathPoint(
                        new Translation2d(
                                TrajectoriesConfig.topSecondXPosition,
                                TrajectoriesConfig.topSecondYPosition),
                        Rotation2d.fromDegrees(TrajectoriesConfig.topSecondHeading),
                        Rotation2d.fromDegrees(TrajectoriesConfig.constantRotation)));*/

        path =
                PathPlanner.generatePath(
                        new PathConstraints(maxVelocity, maxAcceleration),
                        points // position, heading(direction of travel),
                        // holonomic
                        // rotation
                        );

        pathFollowingCommmand = PathBuilder.pathBuilder.followPath(path);
        pathFollowingCommmand.initialize();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        pathFollowingCommmand.execute();
        // pathFollowingCommmand.end(isFinished());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        pathFollowingCommmand.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pathFollowingCommmand.isFinished();
    }
}
