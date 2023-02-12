// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trajectories.commands;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import frc.robot.Robot;
import frc.robot.swerve.SwerveConfig;

public class FollowTrajectory extends PPSwerveControllerCommand {

    /** Creates a new FollowTrajectory. */
    public FollowTrajectory(PathPlannerTrajectory trajectory) {
        super(
                trajectory,
                Robot.pose::getPosition,
                SwerveConfig.swerveKinematics,
                Robot.trajectories.xController,
                Robot.trajectories.yController,
                Robot.trajectories.thetaController,
                Robot.swerve::setModuleStates,
                Robot.swerve);

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        super.initialize();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        super.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
