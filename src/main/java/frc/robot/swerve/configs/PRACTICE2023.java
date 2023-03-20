package frc.robot.swerve.configs;

import frc.robot.RobotConfig.Motors;
import frc.robot.swerve.configTemplates.GyroConfig;
import frc.robot.swerve.configTemplates.ModuleConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig.AngleSensorType;
import frc.robot.swerve.configTemplates.SwerveConfig;
import frc.robot.swerve.configTemplates.TuningConfig;

public class PRACTICE2023 {
    /* Angle Offsets */
    public static final double mod0angleOffset = 324.58;
    public static final double mod1angleOffset = 68.2910156;
    public static final double mod2angleOffset = 136.8457;
    public static final double mod3angleOffset = 9.9316;

    public static final GyroConfig gyro = XRAY2023.gyro;

    public static final PhysicalConfig physical =
            new PhysicalConfig(
                    XRAY2023.trackWidth,
                    XRAY2023.wheelBase,
                    XRAY2023.wheelDiameter,
                    XRAY2023.driveGearRatio,
                    XRAY2023.angleGearRatio,
                    XRAY2023.driveMotorInvert,
                    XRAY2023.angleMotorInvert,
                    XRAY2023.angleSensorInvert,
                    AngleSensorType.CANCoder);

    public static final TuningConfig tuning = XRAY2023.tuning;

    /* Module Configs */
    static final ModuleConfig Mod0 =
            new ModuleConfig(
                    XRAY2023.canBus,
                    Motors.driveMotor0,
                    Motors.angleMotor0,
                    3,
                    mod0angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod1 =
            new ModuleConfig(
                    XRAY2023.canBus,
                    Motors.driveMotor1,
                    Motors.angleMotor1,
                    13,
                    mod1angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod2 =
            new ModuleConfig(
                    XRAY2023.canBus,
                    Motors.driveMotor2,
                    Motors.angleMotor2,
                    23,
                    mod2angleOffset,
                    physical,
                    tuning);

    static final ModuleConfig Mod3 =
            new ModuleConfig(
                    XRAY2023.canBus,
                    Motors.driveMotor3,
                    Motors.angleMotor3,
                    33,
                    mod3angleOffset,
                    physical,
                    tuning);

    public static final ModuleConfig[] modules = new ModuleConfig[] {Mod0, Mod1, Mod2, Mod3};

    public static final SwerveConfig config = new SwerveConfig(physical, tuning, gyro, modules);
}
