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
	 * Converts real height to relative height for the elevator.
     *  Subtracts the height of the elevator at the bottom.
     *  So that the height of the elevator at the bottom is 0.
     *  Does trigonomic calculations to find the relative height.
	 * @param meters height in meters
     * @return relative height in meters
     * @throws IllegalArgumentException if the height is below the starting height or if the height is above the max height
     * @see #heightToRelativeHeight(double)
	 */
    public static double heightToRelativeHeight(double meters) {
        meters = meters - config.startingHeight;
        meters = meters / Math.cos(Math.toRadians(config.angle));
        return meters;
    }
}
