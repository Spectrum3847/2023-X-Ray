package frc.robot.swerve.configs;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.util.Units;
import frc.robot.RobotConfig.Motors;
import frc.robot.swerve.configTemplates.GyroConfig;
import frc.robot.swerve.configTemplates.ModuleConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig.AngleSensorType;
import frc.robot.swerve.configTemplates.SwerveConfig;
import frc.robot.swerve.configTemplates.TuningConfig;

public class ALPHA2023 {
    /* Angle Offsets */
    public static final double mod0angleOffset = 194.238281;
    public static final double mod1angleOffset = 170.068359;
    public static final double mod2angleOffset = 255.585938;
    public static final double mod3angleOffset = 308.408203;

    /* Physical Configs */
    static final double trackWidth = Units.inchesToMeters(18.5);
    static final double wheelBase = Units.inchesToMeters(21.5);
    static final double wheelDiameter = Units.inchesToMeters(4);
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
            ((6380 / 60) / angleGearRatio) * wheelDiameter * Math.PI * 0.95; // meters per //
    // second
    public static final double maxAutoVelocity =
            ((6380 / 60) / driveGearRatio) * wheelDiameter * Math.PI * 0.95; // meters per //
    // second
    static final double maxAccel = maxVelocity * 1.5; // take 1/2 sec to get to max speed.
    static final double maxAngularVelocity =
            maxVelocity / Math.hypot(trackWidth / 2.0, wheelBase / 2.0);
    static final double maxAngularAcceleration = Math.pow(maxAngularVelocity, 2);

    /*Rotation Controller*/
    public static final double kPRotationController = 4.0;
    public static final double kIRotationController = 0.0;
    public static final double kDRotationController = 0.1;

    public static final GyroConfig gyro = INFRARED2022.gyro;

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
                    AngleSensorType.CANCoder);

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
                            maxAutoVelocity,
                            maxAccel,
                            maxAngularVelocity,
                            maxAngularAcceleration)
                    .configNeutralModes(NeutralMode.Coast, NeutralMode.Coast)
                    .configRotationController(
                            kPRotationController, kIRotationController, kDRotationController);;

    /* Module Configs */
    static final ModuleConfig Mod0 =
            new ModuleConfig(
                    Motors.driveMotor0, Motors.angleMotor0, 3, mod0angleOffset, physical, tuning);

    static final ModuleConfig Mod1 =
            new ModuleConfig(
                    Motors.driveMotor1, Motors.angleMotor1, 13, mod1angleOffset, physical, tuning);

    static final ModuleConfig Mod2 =
            new ModuleConfig(
                    Motors.driveMotor2, Motors.angleMotor2, 23, mod2angleOffset, physical, tuning);

    static final ModuleConfig Mod3 =
            new ModuleConfig(
                    Motors.driveMotor3, Motors.angleMotor3, 33, mod3angleOffset, physical, tuning);

    public static final ModuleConfig[] modules = new ModuleConfig[] {Mod0, Mod1, Mod2, Mod3};

    public static final SwerveConfig config = new SwerveConfig(physical, tuning, gyro, modules);
}
