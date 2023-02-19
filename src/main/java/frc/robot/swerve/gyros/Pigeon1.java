package frc.robot.swerve.gyros;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConfig;

public class Pigeon1 implements GyroIO {
    public WPI_PigeonIMU pigeon;

    /**
     * Creates a new Gyro, which is a wrapper for the Pigeon IMU and stores an offset so we don't
     * have to directly zero the gyro
     */
    public Pigeon1() {
        pigeon = new WPI_PigeonIMU(RobotConfig.pigeonID);
        pigeon.configFactoryDefault();
    }

    /**
     * Get the raw yaw of the robot in Rotation2d without using the yawOffset
     *
     * @return the raw yaw of the robot in Rotation2d
     */
    public Rotation2d getRawYaw() {
        return Rotation2d.fromDegrees(pigeon.getYaw());
    }
}
