package frc.robot.elevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.SpectrumLib.subsystems.linearMech.LinearMechSubsystem;
import frc.robot.RobotConfig;

public class Elevator extends LinearMechSubsystem {
    public static ElevatorConfig config = new ElevatorConfig();

    public Elevator() {
        super(config);
        motorLeader = new WPI_TalonFX(RobotConfig.Motors.elevatorMotor);
        // motorLeader.setNeutralMode(true);
        setupFalconLeader();
    }

    public static double metersToFalcon(double meters, double circumference, double gearRatio) {
        meters = meters / circumference;
        meters = meters * 2048 * gearRatio;
        return meters;
    }

    public static double inchesToMeters(double inches) {
        double meters = inches * 0.0254;
        return meters;
    }

    /**
	 * Converts real height to extension for the elevator.
     *  Subtracts the height of the elevator at the bottom.
     *  So that the height of the elevator at the bottom is 0.
     *  Does trigonomic calculations to find the relative height.
	 * @param meters height in meters
     * @return relative height in meters
     * @throws IllegalArgumentException if the height is below the starting height or if the Extension is above the max Extension
	 */
    public static double heightToExtension(double meters) {
        meters = meters - config.startingHeight;
        if (meters < 0) {
            throw new IllegalArgumentException("Height is below the starting height.");
        }
        meters = meters / Math.sin(Math.toRadians(config.angle));
        if (meters > config.maxExtension) {
            throw new IllegalArgumentException("Height is above the max extension.");
        }
        return meters;
    }
}
