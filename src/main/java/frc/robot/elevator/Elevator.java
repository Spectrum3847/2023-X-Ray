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
}
