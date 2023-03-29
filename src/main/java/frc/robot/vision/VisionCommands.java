package frc.robot.vision;

public class VisionCommands {

    /*public static Command aimToHybridSpot(int spot) {
        return PilotCommands.aimPilotDrive(
                        () ->
                                Robot.pose.getHeading().getRadians()
                                        + Units.degreesToRadians(
                                                Robot.vision.getThetaToHybrid(
                                                        spot))) // or Robot.swerve.getRotation()?
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
    */
}
