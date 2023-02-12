package frc.robot.swerve.gyros;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConfig;

public class Pigeon2 implements GyroIO {
    public WPI_Pigeon2 pigeon;

    /**
     * Creates a new Gyro, which is a wrapper for the Pigeon IMU and stores an offset so we don't
     * have to directly zero the gyro
     */
    public Pigeon2() {
        pigeon = new WPI_Pigeon2(RobotConfig.pigeonID);
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
