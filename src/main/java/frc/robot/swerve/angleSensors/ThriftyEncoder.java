package frc.robot.swerve.angleSensors;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogInput;

public class ThriftyEncoder extends AngleSensorIO {
    public AnalogInput input;
    public double readVoltageMax;

    private static double standardReadVoltageMax = 4.8107905230000005;

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
        this.readVoltageMax = standardReadVoltageMax;
    }

    // get positiond
    public Rotation2d getPosition() {
        return new Rotation2d(this.getPositionRadians());
    }

    private double getPositionRadians() {
        double currentVoltage = input.getVoltage();
        if (this.readVoltageMax < currentVoltage) {
            this.readVoltageMax = currentVoltage;
            System.out.println("####!!!!!!New analog max voltage: " + currentVoltage);
        }
        return (currentVoltage * 2 * Math.PI) / this.readVoltageMax;
    }

    public Rotation2d get() {
        return this.getPosition();
    }
}
