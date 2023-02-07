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
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class GeneratePath extends CommandBase {
    private double startXPos;
    private double startYPos;
    private double startHeading;
    private double startRotation;
    private double startVelocity;

    private PathPlannerTrajectory path;
    private double maxVelocity;
    private double maxAcceleration;

    private double finalXPos;
    private double finalYPos;
    private double finalHeading;
    private double finalRotation;

    /** Creates a new GeneratePath. */
    public GeneratePath() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startXPos = Robot.swerve.getPoseMeters().getX();
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

        path =
                PathPlanner.generatePath(
                        new PathConstraints(maxVelocity, maxAcceleration),
                        new PathPoint(
                                new Translation2d(startXPos, startYPos),
                                Rotation2d.fromDegrees(startHeading),
                                Rotation2d.fromDegrees(startRotation),
                                startVelocity), // position, heading(direction of
                        // travel), holonomic rotation,
                        // velocity override
                        new PathPoint(
                                new Translation2d(finalXPos, finalYPos),
                                Rotation2d.fromDegrees(finalHeading),
                                Rotation2d.fromDegrees(
                                        finalRotation)) // position, heading(direction of travel),
                        // holonomic
                        // rotation
                        );
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        PathBuilder.pathBuilder.fullAuto(path);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
