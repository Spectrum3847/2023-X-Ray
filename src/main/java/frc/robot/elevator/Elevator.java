package frc.robot.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.util.Units;
import frc.SpectrumLib.subsystems.linearMech.LinearMechSubsystem;
import frc.SpectrumLib.util.Conversions;
import frc.robot.RobotConfig;

public class Elevator extends LinearMechSubsystem {
    public static ElevatorConfig config = new ElevatorConfig();

    public Elevator() {
        super(config);
        motorLeader = new WPI_TalonFX(RobotConfig.Motors.elevatorMotor, "3847");
        config.updateTalonFXConfig();
        setupFalconLeader();
        motorLeader.configForwardSoftLimitThreshold(inchesToFalcon(config.maxUpPos));
        motorLeader.configForwardSoftLimitEnable(true);
        motorLeader.configReverseSoftLimitThreshold(600);
        motorLeader.configReverseSoftLimitEnable(true);
        motorLeader.setSelectedSensorPosition(-300);
        motorLeader.configNominalOutputForward(0.06);
    }

    @Override
    public void setMMPosition(double position) {
        motorLeader.set(ControlMode.MotionMagic, position);
    }

    public void setBrakeMode(Boolean brake) {
        if (brake) {
            motorLeader.setNeutralMode(NeutralMode.Brake);
        } else {
            motorLeader.setNeutralMode(NeutralMode.Coast);
        }
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

    /**
     * Converts meters to falcon units.
     *
     * @param meters
     * @param circumference in meters
     * @param gearRatio
     * @return falcon units
     */
    public static double metersToFalcon(double meters, double circumference, double gearRatio) {
        meters = meters / circumference;
        meters = meters * 2048 * gearRatio;
        return meters;
    }

    public static double metersToFalcon(double meters) {
        return metersToFalcon(
                meters, Units.inchesToMeters(config.diameterInches) * Math.PI, config.gearRatio);
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
     * Converts falcon units to inches.
     *
     * @param falcon
     * @return inches
     */
    public static double falconToInches(double falcon) {
        return Units.metersToInches(
                Conversions.FalconToMeters(
                        falcon,
                        Units.inchesToMeters(Elevator.config.diameterInches) * Math.PI,
                        Elevator.config.gearRatio));
    }
}
