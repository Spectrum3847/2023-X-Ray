package frc.robot.swerve.configs;

import edu.wpi.first.math.util.Units;
import frc.robot.RobotConfig.Motors;
import frc.robot.swerve.configTemplates.GyroConfig;
import frc.robot.swerve.configTemplates.GyroConfig.GyroType;
import frc.robot.swerve.configTemplates.ModuleConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig.AngleSensorType;
import frc.robot.swerve.configTemplates.SwerveConfig;
import frc.robot.swerve.configTemplates.TuningConfig;

public class XRAY2023 {
    /* Angle Offsets */
    public static final double mod0angleOffset = 194.76;
    public static final double mod1angleOffset = 169.18;
    public static final double mod2angleOffset = 255.05;
    public static final double mod3angleOffset = 309.19;

    /* CanBus Name */
    public static final String canBus = "3847";

    /* Physical Configs */
    static final double trackWidth = Units.inchesToMeters(18.5);
    static final double wheelBase = Units.inchesToMeters(21.5);
    static final double wheelDiameter = Units.inchesToMeters(4.092);
    static final double driveGearRatio = (6.75 / 1.0);
    static final double angleGearRatio = (50.0 / 14.0) * (60.0 / 10.0);
    static final boolean driveMotorInvert = true;
    static final boolean angleMotorInvert = true;
    static final boolean angleSensorInvert = false;

    // Tuning Config
    /* Angle Motor PID Values */
    static final double angleKP = 0.6;
    static final double angleKD = 12;

    /* Drive Motor PID Values */
    static final double driveKP = 0.1;
    static final double driveKD = 0.0;

    /* Drive Motor Characterization Values */
    static final double driveKS = (0.605 / 12); // /12 to convert from volts to %output
    static final double driveKV = (1.72 / 12);
    static final double driveKA = (0.193 / 12);

    /* Swerve Profiling Values */
    static final double maxVelocity =
            ((6380 / 60) / driveGearRatio) * wheelDiameter * Math.PI * 0.95; // meters per //
    // second
    static final double maxAccel = maxVelocity * 1.5; // take 1/2 sec to get to max speed.
    static final double maxAngularVelocity =
            maxVelocity / Math.hypot(trackWidth / 2.0, wheelBase / 2.0);
    static final double maxAngularAcceleration = Math.pow(maxAngularVelocity, 2);

    public static final GyroType type = GyroType.PIGEON2;
    public static final int port = 0;
    public static final GyroConfig gyro = new GyroConfig(type, port, canBus);

    public static final PhysicalConfig physical =
            new PhysicalConfig(
                    trackWidth,
                    wheelBase,
                    wheelDiameter,
                    driveGearRatio,
                    angleGearRatio,
                    driveMotorInvert,
                    angleMotorInvert,
                    angleSensorInvert,
                    AngleSensorType.ThriftyEncoder);

    public static final TuningConfig tuning =
            new TuningConfig(
                    angleKP,
                    angleKD,
                    driveKP,
                    driveKD,
                    driveKS,
                    driveKV,
                    driveKA,
                    maxVelocity,
                    maxAccel,
                    maxAngularVelocity,
                    maxAngularAcceleration);

    /* Module Configs */
    static final ModuleConfig Mod0 =
            new ModuleConfig(
                    canBus,
                    Motors.driveMotor0,
                    Motors.angleMotor0,
                    0,
                    mod0angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod1 =
            new ModuleConfig(
                    canBus,
                    Motors.driveMotor1,
                    Motors.angleMotor1,
                    1,
                    mod1angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod2 =
            new ModuleConfig(
                    canBus,
                    Motors.driveMotor2,
                    Motors.angleMotor2,
                    2,
                    mod2angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod3 =
            new ModuleConfig(
                    canBus,
                    Motors.driveMotor3,
                    Motors.angleMotor3,
                    3,
                    mod3angleOffset,
                    physical,
                    tuning);

    public static final ModuleConfig[] modules = new ModuleConfig[] {Mod0, Mod1, Mod2, Mod3};

    public static final SwerveConfig config = new SwerveConfig(physical, tuning, gyro, modules);
}