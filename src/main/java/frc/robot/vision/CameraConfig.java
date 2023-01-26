package frc.robot.vision;

import org.photonvision.PhotonCamera;

// This class shouldn't be used directly, use the specific Camera static class instead
public class CameraConfig {
    public final String cameraName;
    public final double cameraToRobotX;
    public final double cameraToRobotY;
    public final double cameraToRobotZ;
    public final double cameraPitchRadians;
    public final double cameraYawRadians;
    public final double cameraRollRadians;
    public final PhotonCamera camera;

    /**
     * Vision Camera Config to be used when creating cameras.
     *
     * @param cameraName the name of the camera as it appears in the PhotonVision UI
     * @param cameraToRobotXMeters the x distance from the robot ceneter to the center of the camera
     *     in meters
     * @param cameraToRobotYMeters the y distance from the robot ceneter to the center of the camera
     *     in meters
     * @param cameraToRobotZMeters the z distance from the robot ceneter to the center of the camera
     *     in meters
     * @param cameraPitchRadians the pitch of the camera in radians. Be careful with direction of
     *     rotation
     */
    public CameraConfig(
            String cameraName,
            double cameraToRobotXMeters,
            double cameraToRobotYMeters,
            double cameraToRobotZMeters,
            double cameraPitchRadians,
            double cameraYawRadians,
            double cameraRollRadians) {
        this.cameraName = cameraName;
        this.cameraToRobotX = cameraToRobotXMeters;
        this.cameraToRobotY = cameraToRobotYMeters;
        this.cameraToRobotZ = cameraToRobotZMeters;
        this.cameraPitchRadians = cameraPitchRadians;
        this.cameraYawRadians = cameraYawRadians;
        this.cameraRollRadians = cameraRollRadians;
        this.camera = new PhotonCamera(cameraName);
    }
}
