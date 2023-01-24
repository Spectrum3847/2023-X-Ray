package frc.robot.swerve;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConfig;

public class Gyro {
    public WPI_Pigeon2 pigeon;
    public Rotation2d yawOffset = new Rotation2d(0);

    /**
     * Creates a new Gyro, which is a wrapper for the Pigeon IMU and stores an offset so we don't
     * have to directly zero the gyro
     */
    public Gyro() {
        pigeon = new WPI_Pigeon2(RobotConfig.pigeonID);
        pigeon.configFactoryDefault();
        zeroGyro();
    }

    /** Zero the gyro */
    public void zeroGyro() {
        setGyroDegrees(0);
    }

    /**
     * Set the gyro yawOffset to a specific angle
     *
     * @param degrees The angle to set the gyro to
     */
    public void setGyroDegrees(double value) {
        yawOffset = getRawYaw().minus(new Rotation2d(value));
    }

    /**
     * Get the yaw of the robot in Rotation2d
     *
     * @return the yaw of the robot in Rotation2d
     */
    public Rotation2d getYaw() {
        return getRawYaw().minus(yawOffset);
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
