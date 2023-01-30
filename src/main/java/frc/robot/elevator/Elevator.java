package frc.robot.elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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

    public double getKf() {
        TalonFXConfiguration FXconfig = new TalonFXConfiguration();
        motorLeader.getAllConfigs(FXconfig);
        return FXconfig.slot0.kF;
    }

    public static double metersToFalcon(double meters, double circumference, double gearRatio) {
        meters = meters / circumference;
        meters = meters * 2048 * gearRatio;
        return meters;
    }

    public static double metersToFalcon(double meters) {
        return metersToFalcon(meters, config.diameterInches * Math.PI, config.gearRatio);
    }

    public static double inchesToMeters(double inches) {
        double meters = inches * 0.0254;
        return meters;
    }

    public static double inchesToFalcon(double inches) {
        double meters = inchesToMeters(inches);
        double falcon = metersToFalcon(meters);
        return falcon;
    }
}
