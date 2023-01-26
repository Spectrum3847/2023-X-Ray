package frc.robot.swerve.angleSensors;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogInput;

public class ThriftyEncoder extends AngleSensorIO {
    public AnalogInput input;
    public double readVoltageMax;

    private static double standardReadVoltageMax = 4.8;

    /**
     * Create a new ThriftyEncoder at this analog input channel
     *
     * @param channel
     */
    public ThriftyEncoder(int channel) {
        this(new AnalogInput(channel));
    }

    public ThriftyEncoder(AnalogInput input) {
        this.input = input;
        this.readVoltageMax = ThriftyEncoder.standardReadVoltageMax;
    }

    // get position
    public Rotation2d getPosition() {
        return new Rotation2d(this.getPositionRadians());
    }

    private double getPositionRadians() {
        return (input.getVoltage() * 2 * Math.PI) / this.readVoltageMax;
    }

    public Rotation2d get() {
        return this.getPosition();
    }
}
