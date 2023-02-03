package frc.robot.fourbar;

import frc.SpectrumLib.subsystems.angleMech.AngleMechConfig;

public class FourBarConfig extends AngleMechConfig {

    public boolean kInverted = true;

    public final int minAngle = 0;
    public final int maxAngle = 75000;
    public final int midAngle = 37000;

    public final int fourbarMaxFalcon = 60000;

    // Positions set as percentage of fourbar
    public final double cubeIntake = 95;
    public final double cubeMid = 60;
    public final double cubeTop = 100;

    public final double coneIntake = 100;
    public final double coneStandingIntake = 90;
    public final double coneShelf = 0;

    public final double coneMid = 50;
    public final double coneTop = 100;

    // Physical Constants
    public final double gearRatio = 1;

    public FourBarConfig() {
        super("FourBar");
        this.kP = 0.5; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.3;
        this.motionCruiseVelocity = 4000;
        this.motionAcceleration = 4000;

        this.currentLimit = 10;
        this.tirggerThresholdLimit = 10;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        updateTalonFXConfig();
    }
}
