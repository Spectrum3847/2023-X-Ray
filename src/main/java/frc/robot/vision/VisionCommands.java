package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import frc.robot.pilot.commands.PilotCommands;

public class VisionCommands {

    public static Command aimToHybridSpot(int spot) {
        return PilotCommands.aimPilotDrive(() -> Units.degreesToRadians(Robot.vision.getThetaToHybrid(spot)))
                .withName("Aim to Hybrid Spot");
    }

    public static Command printYawInfo() {
        return new InstantCommand(
                () ->
                        RobotTelemetry.print(
                                "Yaw (D): "
                                        + Robot.vision.photonVision.getYaw()
                                        + "|| gyro (D): "
                                        + Robot.swerve.getRotation().getDegrees()
                                        + " || Aiming at: "
                                        + (Robot.vision.photonVision.getYaw()
                                                + Robot.swerve.getRotation().getDegrees())));
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
}
