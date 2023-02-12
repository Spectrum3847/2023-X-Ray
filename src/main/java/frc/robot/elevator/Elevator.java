package frc.robot.elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import frc.SpectrumLib.subsystems.linearMech.LinearMechSubsystem;
import frc.robot.RobotConfig;

public class Elevator extends LinearMechSubsystem {
    public static ElevatorConfig config = new ElevatorConfig();

    public Elevator() {
        super(config);
        motorLeader = new WPI_TalonFX(RobotConfig.Motors.elevatorMotor);
        motorLeader.setNeutralMode(NeutralMode.Brake);
        config.updateTalonFXConfig();
        setupFalconLeader();
        motorLeader.configReverseSoftLimitThreshold(0);
        motorLeader.configReverseSoftLimitEnable(true);
    }

    public void zeroElevator() {
        motorLeader.setSelectedSensorPosition(0);
    }

    public void resetSensorPosition(double pos) {
        motorLeader.setSelectedSensorPosition(pos); // 10 for now, will change later
    }

    public void softLimitsTrue() {
        motorLeader.configReverseSoftLimitEnable(true);
    }

    public void softLimitsFalse() {
        motorLeader.configReverseSoftLimitEnable(false);
    }

    public double getKf() {
        TalonFXConfiguration FXconfig = new TalonFXConfiguration();
        motorLeader.getAllConfigs(FXconfig);
        return FXconfig.slot0.kF;
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

    public static double metersToFalcon(double meters) {
        return metersToFalcon(meters, config.diameterInches * Math.PI, config.gearRatio);
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

    public static double inchesToFalcon(double inches) {
        double meters = inchesToMeters(inches);
        double falcon = metersToFalcon(meters);
        return falcon;
    }
    /**
     * Converts real height to extension for the elevator. Subtracts the height of the elevator at
     * the bottom. So that the height of the elevator at the bottom is 0. Does trigonomic
     * calculations to find the relative height.
     *
     * @param meters height in meters
     * @return relative height in meters
     * @see #heightToHorizontalExtension(double)
     * @see #extensionToHeight(double)
     */
    public static double heightToExtension(double meters) {
        meters = meters - config.startingHeight;
        if (meters < 0) {
            meters = config.startingHeight;
            DriverStation.reportWarning("Height is below the starting height. See Elevator#heightToExtension", false);
        }
        meters = meters / Math.sin(Math.toRadians(config.angle));
        if (meters > config.maxExtension) {
            meters = config.maxExtension;
            DriverStation.reportWarning("Height is above the max extension. See Elevator#heightToExtension", false);
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
        meters = meters - config.startingHeight;
        meters = meters / Math.cos(Math.toRadians(config.angle));
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
        meters = meters * Math.sin(Math.toRadians(config.angle));
        meters = meters + config.startingHeight;
        return meters;
    }
}
