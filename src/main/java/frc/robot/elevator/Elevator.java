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

    /**
     * Converts meters to falcon units.
     *
     * @param meters
     * @param circumference
     * @param gearRatio
     * @return falcon units
     */
    public static double metersToFalcon(double meters, double circumference, double gearRatio) {
        meters = meters / circumference;
        meters = meters * 2048 * gearRatio;
        return meters;
    }

    /**
     * Converts inches to meters.
     *
     * @param inches
     * @return meters
     */
    public static double inchesToMeters(double inches) {
        double meters = inches * 0.0254;
        return meters;
    }

    /**
     * Converts real height to extension for the elevator. Subtracts the height of the elevator at
     * the bottom. So that the height of the elevator at the bottom is 0. Does trigonomic
     * calculations to find the relative height.
     *
     * @param meters height in meters
     * @return relative height in meters
     * @throws IllegalArgumentException if the height is below the starting height or if the
     *     Extension is above the max Extension
     * @see #heightToHorizontalExtension(double)
     * @see #extensionToHeight(double)
     */
    public static double heightToExtension(double meters) {
        meters = meters - config.startingHeight;
        if (meters < 0) {
            throw new IllegalArgumentException("Height is below the starting height.");
        }
        meters = 2 * (meters/Math.sqrt(3));
        if (meters > config.maxExtension) {
            throw new IllegalArgumentException("Height is above the max extension.");
        }
        return meters;
    }

    /**
     * converts real height to horizontal extension for the elevator.
     *
     * @param meters height in meters
     * @return horizontal extension in meters relative to the robot's frame perimeter.
     * @see #heightToExtension(double)
     * @see #extensionToHeight(double)
     */
    public static double heightToHorizontalExtension(double meters) {
        meters = meters/Math.sqrt(3);
        meters = meters + config.startingHorizontalExtension;
        return meters;
    }

    /**
     * converts elevator extension to real height.
     *
     * @param meters extension in meters
     * @return height in meters
     * @see #heightToExtension(double)
     * @see #heightToHorizontalExtension(double)
     */
    public static double extensionToHeight(double meters) {
        meters = Math.sqrt(3)*(meters/2);
        meters = meters + config.startingHeight;
        return meters;
    }
}
