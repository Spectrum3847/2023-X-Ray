package frc.robot.elevator;

import frc.SpectrumLib.subsystems.linearMech.LinearMechConfig;
import frc.robot.RobotConfig.Motors;

public class ElevatorConfig extends LinearMechConfig {
    public static final String name = "Elevator";
    public final double diameterInches = 1.2815; // changed from int, 4
    public final double gearRatio = 62 / 8;

    public static final int elevatorMotorID = Motors.elevatorMotor;

    public double kP = 0;
    public double kI = 0; // could be 0
    public double kD = 0; // could be 0
    public double kF = 0.1079;
    public double kIz = 0; // could be 0
    public double motionCruiseVelocity = 4663;
    public double motionAcceleration = 4663;

    public ElevatorConfig() {
        super(name);
    }
}
