package frc.robot.fourbar;

import frc.SpectrumLib.subsystems.angleMech.AngleMechConfig;

public class FourBarConfig extends AngleMechConfig {

    public boolean kInverted = true;

    public final int fourbarMaxFalcon = 44000;

    // Positions set as percentage of fourbar
    public final int cubeIntake = 70;
    public final int cubeMid = 0;
    public final int cubeTop = 0;

    public final int coneIntake = 90;
    public final int coneStandingIntake = 70;
    public final int coneShelf = 0;

    public final int coneHybrid = 30;
    public final int coneMid = 48; // converted from 1800 angle //24700
    public final int coneTop = 100; // converted from 54900 angle

    public final int safePositionForElevator = 61;

    public final double zeroSpeed = -0.1;

    // Physical Constants
    public final double gearRatio = 1;

    public FourBarConfig() {
        super("FourBar");
        this.kP = 0.4; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.064;
        this.motionCruiseVelocity = 10500;
        this.motionAcceleration = 42000;

        this.currentLimit = 10;
        this.tirggerThresholdLimit = 10;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        updateTalonFXConfig();
    }
}
