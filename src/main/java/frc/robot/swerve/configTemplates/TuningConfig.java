package frc.robot.swerve.configTemplates;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class TuningConfig {
    public final double angleKP;
    public final double angleKD;
    public final double driveKP;
    public final double driveKD;
    public final double driveKS;
    public final double driveKV;
    public final double driveKA;
    public final double maxVelocity;
    public final double maxAutoVelocity;
    public final double maxAcceleration;
    public final double maxAngularVelocity;
    public final double MaxAngularAcceleration;

    public double kPRotationController = 0.0;
    public double kIRotationController = 0.0;
    public double kDRotationController = 0.0;

    /* Swerve Current Limiting */
    // Need a way to set these values
    public int angleContinuousCurrentLimit = 20;
    public int anglePeakCurrentLimit = 30;
    public double anglePeakCurrentDuration = 0.1;
    public boolean angleEnableCurrentLimit = true;

    public int driveContinuousCurrentLimit = 40;
    public int drivePeakCurrentLimit = 40;
    public double drivePeakCurrentDuration = 0.0;
    public boolean driveEnableCurrentLimit = true;

    /* Neutral Modes */
    public NeutralMode angleNeutralMode = NeutralMode.Brake;
    public NeutralMode driveNeutralMode = NeutralMode.Brake;

    public TuningConfig(
            double angleKP,
            double angleKD,
            double driveKP,
            double driveKD,
            double driveKS,
            double driveKV,
            double driveKA,
            double maxVelocity,
            double maxAutoVelocity,
            double maxAcceleration,
            double maxAngularVelocity,
            double maxAngularAcceleration) {
        this.angleKP = angleKP;
        this.angleKD = angleKD;
        this.driveKP = driveKP;
        this.driveKD = driveKD;
        this.driveKS = driveKS;
        this.driveKV = driveKV;
        this.driveKA = driveKA;
        this.maxVelocity = maxVelocity;
        this.maxAutoVelocity = maxAutoVelocity;
        this.maxAcceleration = maxAcceleration;
        this.maxAngularVelocity = maxAngularVelocity;
        this.MaxAngularAcceleration = maxAngularAcceleration;
    }

    public TuningConfig configRotationController(
            double kPRotationController, double kIRotationController, double kDRotationController) {
        this.kPRotationController = kPRotationController;
        this.kIRotationController = kIRotationController;
        this.kDRotationController = kDRotationController;
        return this;
    }

    // Must be called in the config file
    public TuningConfig configAngleCurrentLimits(
            int continuousCurrentLimit, int peakCurrentLimit, double peakCurrentDuration) {
        angleContinuousCurrentLimit = continuousCurrentLimit;
        anglePeakCurrentLimit = peakCurrentLimit;
        anglePeakCurrentDuration = peakCurrentDuration;
        return this;
    }

    public TuningConfig configDriveCurrentLimits(
            int continuousCurrentLimit, int peakCurrentLimit, double peakCurrentDuration) {
        driveContinuousCurrentLimit = continuousCurrentLimit;
        drivePeakCurrentLimit = peakCurrentLimit;
        drivePeakCurrentDuration = peakCurrentDuration;
        return this;
    }

    public TuningConfig configNeutralModes(
            NeutralMode angleNeutralMode, NeutralMode driveNeutralMode) {
        this.angleNeutralMode = angleNeutralMode;
        this.driveNeutralMode = driveNeutralMode;
        return this;
    }
}
