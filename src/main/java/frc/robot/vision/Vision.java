package frc.robot.vision;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class Vision extends SubsystemBase {
    public final VisionConfig config;
    public final RobotPoseEstimator poseEstimator;
    public final PhotonCamera[] cameras;
    public Pair<Pose3d, Double> currentPose;

    private ArrayList<Pair<PhotonCamera, Transform3d>> cameraPairs;
    private double yaw, pitch, area, poseAmbiguity, captureTime;
    private int targetId;
    private NetworkTable table;
    private NetworkTableEntry tx, ty, ta;

    // testing
    private final DecimalFormat df = new DecimalFormat();
    private double lastYaw = 0;
    private boolean targetFound = true;

    public Vision() {
        setName("Vision");
        config = new VisionConfig();
        table = NetworkTableInstance.getDefault().getTable("limelight");
        cameraPairs = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        currentPose = new Pair<Pose3d, Double>(new Pose3d(0, 0, 0, new Rotation3d(0, 0, 0)), 0.0);

        /* Limelight NetworkTable Retrieval */
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");

        cameras =
                new PhotonCamera[] {VisionConfig.LL.config.camera
                    /* Add cameras here */
                };
        /* setting up the pair(s) of cameras and their 3d transformation from the center of the robot
        to give to the pose estimator */
        for (int i = 0; i < cameras.length; i++) {
            cameraPairs.add(getCameraPair(getCameraConfig(i)));
        }

        poseEstimator =
                new RobotPoseEstimator(VisionConfig.tagMap, VisionConfig.strategy, cameraPairs);

        // printing purposes
        df.setMaximumFractionDigits(2);
    }

    @Override
    public void periodic() {
        currentPose = getEstimatedPose();
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);

        double[] dv = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] robotPose = table.getEntry("botpose").getDoubleArray(dv);

        if (robotPose.length > 0) {
            SmartDashboard.putString("BotX", df.format(robotPose[0]));
            SmartDashboard.putString("BotY", df.format(robotPose[1]));
            SmartDashboard.putString("BotZ", df.format(robotPose[2]));
            SmartDashboard.putString("Bot1", df.format(robotPose[3]));
            SmartDashboard.putString("Bot2", df.format(robotPose[4]));
            SmartDashboard.putString("Bot3", df.format(robotPose[5]));
        }

        SmartDashboard.putString("tagX", df.format(x));
        SmartDashboard.putString("tagY", df.format(y));
        SmartDashboard.putString("tagArea", df.format(area));
        /* Adding vision estimate to pose */
        // if(isValidPose()) {
        //     Robot.pose.addVisionMeasurement(currentPose.getFirst().toPose2d(),
        // getTimestampSeconds());
        // }

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
        return Units.degreesToRadians(yaw) + Robot.swerve.getHeading().getRadians();
    }

    public double getYaw() {
        return yaw;
    }

    /** Gets the camera capture time in seconds. */
    public double getTimestampSeconds() {
        return Timer.getFPGATimestamp() - (currentPose.getSecond().doubleValue() / 1000d);
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
     * Projects 3d pose to 2d to compare against odometry estimate. Does not account for difference
     * in rotation.
     *
     * @return whether or not the vision estimated pose is within 1 meter of the odometry estimated
     *     pose
     */
    private boolean isValidPose() {
        Pose2d pose = currentPose.getFirst().toPose2d();
        Pose2d odometryPose = Robot.pose.getPosition();
        return (Math.abs(pose.getX() - odometryPose.getX()) <= 1)
                && (Math.abs(pose.getY() - odometryPose.getY()) <= 1);
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

    private void printDebug() {
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
