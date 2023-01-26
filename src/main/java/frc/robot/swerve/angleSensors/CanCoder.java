package frc.robot.swerve.angleSensors;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.math.geometry.Rotation2d;

public class CanCoder extends AngleSensorIO {

    private WPI_CANCoder encoder;
    private CANCoderConfiguration swerveCanCoderConfig;

    public CanCoder(int canID) {
        encoder = new WPI_CANCoder(canID);

        swerveCanCoderConfig = new CANCoderConfiguration();
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = false;
        swerveCanCoderConfig.initializationStrategy =
                SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;

        encoder.configFactoryDefault();
        encoder.configAllSettings(swerveCanCoderConfig);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 249);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
    }

    @Override
    public Rotation2d get() {
        return this.getPosition();
    }

    @Override
    public Rotation2d getPosition() {
        return Rotation2d.fromDegrees(encoder.getAbsolutePosition());
    }
}
