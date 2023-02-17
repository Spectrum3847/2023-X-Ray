package frc.robot.elevator;

import frc.SpectrumLib.subsystems.linearMech.LinearMechConfig;
import frc.robot.RobotConfig.Motors;

public class ElevatorConfig extends LinearMechConfig {
    public static final String name = "Elevator";

    // All these are made up and need to be changed
    public final double cubeIntake = 0;
    public final double cubeMid = 0;
    public final double cubeTop = 0;

    public final double coneIntake = 0;
    public final double coneStandingIntake = 0;
    public final double coneShelf = 140000;

    public final double coneMid = 130000;
    public final double coneTop = 150000;

    public final double diameterInches = 2.0; // changed from int, 4
    public final double gearRatio = 9 / 1;
    public final double maxUpFalconPos = 162116;

    public final double safePositionForFourBar = 0; // TODO: find safe position for four bar
    public final double startingHeight = 0; // TODO: find starting height
    public final double startingHorizontalExtension = 0; // TODO: find starting horizontal extension
    public final double maxExtension = 160000; // TODO: find max relative extension
    public final double angle = 60;

    public final double zeroSpeed = -0.2;

    public static final int elevatorMotorID = Motors.elevatorMotor;

    public ElevatorConfig() {
        super(name);
        this.kP = 0.5; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.4;
        this.motionCruiseVelocity = 50663;
        this.motionAcceleration = 60663;

        this.currentLimit = 25;
        this.tirggerThresholdLimit = 25;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        updateTalonFXConfig();
    }
}
