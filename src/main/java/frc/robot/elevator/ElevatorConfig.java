package frc.robot.elevator;

import frc.SpectrumLib.subsystems.linearMech.LinearMechConfig;
import frc.robot.RobotConfig.Motors;

public class ElevatorConfig extends LinearMechConfig {
    public static final String name = "Elevator";
    public final double diameterInches = 1.2815; // changed from int, 4
    public final double gearRatio = 62 / 8;
    public final double maxUpFalconPos = 162116;

    public static final int elevatorMotorID = Motors.elevatorMotor;

    public ElevatorConfig() {
        super(name);
        this.kP = 0.1; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.1079;
        this.motionCruiseVelocity = 4663;
        this.motionAcceleration = 4663;

        this.currentLimit = 20;
        this.tirggerThresholdLimit = currentLimit;

        updateTalonFXConfig();
    }
}
