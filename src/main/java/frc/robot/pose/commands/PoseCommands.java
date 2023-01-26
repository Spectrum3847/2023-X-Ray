package frc.robot.pose.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

public class PoseCommands {
    public static Command resetHeading(Rotation2d heading) {
        return new InstantCommand(() -> Robot.pose.resetHeading(heading));
    }

    public static Command resetHeading(double headingDeg) {
        return new InstantCommand(
                () -> Robot.pose.resetHeading(Rotation2d.fromDegrees(headingDeg)));
    }
}
