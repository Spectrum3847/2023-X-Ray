package frc.robot.elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.SpectrumLib.subsystems.linearMech.LinearMechConfig;
import frc.robot.RobotConfig.Motors;

public class ElevatorConfig extends LinearMechConfig {
    public static final String name = "Elevator";

    // All these are in inches
    public final double hopHeight = 1;
    public final double hopTime = 0.5;

    public final double coneIntake = 0;
    public final double coneStandingIntake = 0;
    public final double coneShelf = 43.13;

    public final double coneHybrid = 2;
    public final double coneMid = 34.5;
    public final double coneTop = 57.4;

    public final double cubeIntake = 0;
    public final double autonCubeIntake = 0;
    public final double cubeMid = 12.5;
    public final double cubeTop = 14.5; // 24.5;
    public final double cubeHybrid = 0;

    public final double diameterInches = 2.0051;
    public final double gearRatio = 9 / 1;
    public final double maxUpPos = 59.3;
    public final double maxCarriageHeight = 29.732;

    public final double safePositionForFourBar = 42;
    public final double startingHeight = 0;
    public final double startingHorizontalExtension = 0; // TODO: find starting horizontal extension
    public final double safeIntakeHeight = 3.42;
    public final double maxExtension = 0; // TODO: find max relative extension
    public final double angle = 60;
    public final double homeThreshold = 8000; // falcon units
    public final double homeTimeout = 0.5; // seconds
    public final double maxHomeTimeout = 4; // seconds, no home command can take longer than this
    public final double holdConeHeight =
            2; // inches, hold cone will not run if the elevator is above this position

    public final double zeroSpeed = -0.2;

    public final double LEDheight = 24;

    public static final int elevatorMotorID = Motors.elevatorMotor;

    public ElevatorConfig() {
        super(name);
        this.kP = 0.43;
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.064;
        this.motionCruiseVelocity = 24500;
        this.motionAcceleration = 40000;

        this.currentLimit = 30;
        this.tirggerThresholdLimit = 30;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        this.kNeutralMode = NeutralMode.Brake;
        updateTalonFXConfig();
    }
}
