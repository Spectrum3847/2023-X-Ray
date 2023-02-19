package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;

public class VisionCommands {

    public static Command printYawInfo() {
        return new InstantCommand(
                () ->
                        RobotTelemetry.print(
                                "Yaw (D): "
                                        + Robot.vision.getYaw()
                                        + "|| gyro (D): "
                                        + Robot.swerve.getHeading().getDegrees()
                                        + " || Aiming at: "
                                        + (Robot.vision.getYaw()
                                                + Robot.swerve.getHeading().getDegrees())));
    }

    public static Command printEstimatedPoseInfo() {
        Pair<Pose3d, Double> pose = Robot.vision.currentPose;
        return new InstantCommand(
                () ->
                        RobotTelemetry.print(
                                "Estimated Pose: | X: "
                                        + pose.getFirst().getTranslation().getX()
                                        + " | Y: "
                                        + pose.getFirst().getTranslation().getY()
                                        + " | Z: "
                                        + pose.getFirst().getTranslation().getZ()
                                        + " | Rotation (D): "
                                        + Units.radiansToDegrees(
                                                pose.getFirst().getRotation().getZ())
                                        + " | Latency: "
                                        + pose.getSecond().doubleValue()));
    }
}
