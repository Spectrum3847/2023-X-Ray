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
                                        + Robot.vision.photonVision.getYaw()
                                        + "|| gyro (D): "
                                        + Robot.swerve.getHeading().getDegrees()
                                        + " || Aiming at: "
                                        + (Robot.vision.photonVision.getYaw()
                                                + Robot.swerve.getHeading().getDegrees())));
    }

    public static Command printEstimatedPhotonPoseInfo() {
        if (Robot.vision.photonVision.currentPose != null) {
            Pair<Pose3d, Double> pose = Robot.vision.photonVision.currentPose;
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
        } else {
            return new InstantCommand(
                    () -> RobotTelemetry.print("PhotonVision doesn't have a pose!"));
        }
    }

    public static Command printEstimatedLimelightPoseInfo() {
        return new InstantCommand(() -> Robot.vision.printDebug());
    }
}
