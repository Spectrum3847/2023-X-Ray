package frc.robot.elevator;

import frc.SpectrumLib.subsystems.linearMech.LinearMechConfig;
import frc.robot.RobotConfig.Motors;

public class ElevatorConfig extends LinearMechConfig {
    public static final String name = "Elevator";

    // All these are in inches
    public final double cubeIntake = 0;
    public final double cubeMid = 0;
    public final double cubeTop = 0;

    public final double coneIntake = 0;
    public final double coneStandingIntake = 0;
    public final double coneShelf = 43.13; // 126200 falcon

    public final double coneHybrid = 0;
    public final double coneMid = 33.774; // 95900  falcon
    public final double coneTop = 57.415; // 168000 falcon

    public final double diameterInches = 2.0051; // changed from int, 4
    public final double gearRatio = 9 / 1;
    public final double maxUpFalconPos = 58.1; // 170000 falcon
    public final double maxCarriageHeight = 29.732; // 87000 falcon

    public final double safePositionForFourBar = 42; // 121000 falcon
    public final double startingHeight = 0;
    public final double startingHorizontalExtension = 0; // TODO: find starting horizontal extension
    public final double safeIntakeHeight = 3.42; // 10000 falcon
    public final double maxExtension = 0; // TODO: find max relative extension
    public final double angle = 60;

    public final double zeroSpeed = -0.2;

    public static final int elevatorMotorID = Motors.elevatorMotor;

    public ElevatorConfig() {
        super(name);
        this.kP = 0.4; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.064;
        this.motionCruiseVelocity = 10500;
        this.motionAcceleration = 42000;

        this.currentLimit = 30;
        this.tirggerThresholdLimit = 30;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        updateTalonFXConfig();
    }
}
