package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class PhotonVision {
    public final VisionConfig config;
    public final RobotPoseEstimator poseEstimator;
    public final PhotonCamera[] cameras;
    public Pair<Pose3d, Double> currentPose;

    private ArrayList<Pair<PhotonCamera, Transform3d>> cameraPairs;
    private double yaw, pitch, area, poseAmbiguity, captureTime;
    private int targetId;

    // testing
    private final DecimalFormat df = new DecimalFormat();
    private double lastYaw = 0;
    private boolean targetFound = true;

    public PhotonVision() {
        config = new VisionConfig();
        cameraPairs = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        currentPose = new Pair<Pose3d, Double>(new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0)), 0.0);

        cameras =
                new PhotonCamera[] {VisionConfig.LL.config.camera
                    /* Add cameras here */
                };
        /* setting up the pair(s) of cameras and their 3d transformation from the center of the robot
        to give to the pose estimator */
        for (int i = 0; i < cameras.length; i++) {
            cameraPairs.add(getCameraPair(getCameraConfig(i)));
        }

        /* Outdated use of PhotonPoseEstimator */
        poseEstimator =
                new RobotPoseEstimator(VisionConfig.tagMap, VisionConfig.strategy, cameraPairs);

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    public void update() {
        currentPose = getEstimatedPose();

        /* get targets & basic data from a single camera */
        PhotonPipelineResult results = cameras[0].getLatestResult();
        if (results.hasTargets()) {
            PhotonTrackedTarget target = results.getBestTarget();
            // negate it because the target.getYaw is the yaw of the robot from the target which is
            // the opposite direction. Or photonvision yaw is CW+ CCW-
            yaw = -target.getYaw();
            pitch = target.getPitch();
            area = target.getArea();
            targetId = target.getFiducialId();
            poseAmbiguity = target.getPoseAmbiguity();
            captureTime = Timer.getFPGATimestamp() - (results.getLatencyMillis() / 1000d);

            // printing
            if (lastYaw == 0) {
                lastYaw = yaw;
            }

            /* If robot has moved, print new target data */
            if (Math.round(lastYaw) != Math.round(yaw)) {
                // printDebug(targetId, yaw, pitch, area, poseAmbiguity, captureTime);
            }

            lastYaw = yaw;
            targetFound = true;
        } else {
            // no target found
            yaw = 0.0;
            if (targetFound) {
                RobotTelemetry.print("Lost target");
                targetFound = false;
            }
        }
    }

    /**
     * Gets the yaw of the target relative to the field for aiming.
     *
     * @return Yaw in radians
     */
    public double getRadiansToTarget() {
        return Units.degreesToRadians(yaw) + Robot.swerve.getRotation().getRadians();
    }

    public double getYaw() {
        return yaw;
    }

    /**
     * Gets the estimated pose of the robot.
     *
     * @return the estimated pose of the robot
     */
    private Pair<Pose3d, Double> getEstimatedPose() {
        return poseEstimator.update();
    }

    /**
     * Gets the camera pair for the pose estimator.
     *
     * @param cameraConfig the camera config
     * @return the camera pair
     */
    private Pair<PhotonCamera, Transform3d> getCameraPair(CameraConfig config) {
        return new Pair<PhotonCamera, Transform3d>(
                config.camera,
                new Transform3d(
                        new Translation3d(
                                config.cameraToRobotX,
                                config.cameraToRobotY,
                                config.cameraToRobotZ),
                        new Rotation3d(
                                config.cameraRollRadians,
                                config.cameraPitchRadians,
                                config.cameraYawRadians)));
    }

    /**
     * Gets the camera config for the camera.
     *
     * @param cameraIndex the camera index
     * @return the camera config
     */
    private CameraConfig getCameraConfig(int iteration) {
        switch (iteration) {
            case 0:
                return VisionConfig.LL.config;
                /* add camera configs here */
            default:
                RobotTelemetry.print("Something went wrong trying to get camera config");
                return VisionConfig.LL.config;
        }
    }

    void printDebug() {
        RobotTelemetry.print(
                "Target ID: "
                        + targetId
                        + " | Target Yaw: "
                        + df.format(yaw)
                        + " | Pitch: "
                        + df.format(pitch)
                        + " | Area: "
                        + df.format(area)
                        + " | Pose Ambiguity: "
                        + poseAmbiguity
                        + " | Capture Time: "
                        + df.format(captureTime));
    }
}
