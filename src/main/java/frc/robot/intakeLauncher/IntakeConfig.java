package frc.robot.intakeLauncher;

import frc.SpectrumLib.subsystems.rollerMech.RollerMechConfig;

public class IntakeConfig extends RollerMechConfig {
    public IntakeConfig() {
        super("Intake");
        this.kP = 0.5; // not accurate value, just testing
        this.kI = 0; // could be 0
        this.kD = 0; // could be 0
        this.kF = 0.3;
        this.motionCruiseVelocity = 50663;
        this.motionAcceleration = 60663;

        this.currentLimit = 25;
        this.tirggerThresholdLimit = 25;
        this.PeakCurrentDuration = 0.0;
        this.EnableCurrentLimit = true;
        updateTalonFXConfig();
    }
}
