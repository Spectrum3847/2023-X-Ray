package frc.robot.swerve.configs;

import frc.robot.RobotConfig.Motors;
import frc.robot.swerve.configTemplates.GyroConfig;
import frc.robot.swerve.configTemplates.GyroConfig.GyroType;
import frc.robot.swerve.configTemplates.ModuleConfig;
import frc.robot.swerve.configTemplates.PhysicalConfig;
import frc.robot.swerve.configTemplates.SwerveConfig;
import frc.robot.swerve.configTemplates.TuningConfig;

public class GAMMA2021 {
    /* Angle Offsets */
    static final double Mod0AngleOffset = 182.54;
    static final double Mod1AngleOffset = 88.69;
    static final double Mod2AngleOffset = 352.4;
    static final double Mod3AngleOffset = 350.59;

    /* Gyro Config */
    static final GyroType type = GyroType.PIGEON1;
    static final int port = 0;

    public static final GyroConfig gyro = new GyroConfig(type, port);
    public static final PhysicalConfig physical = FLASH2021.physical;
    public static final TuningConfig tuning = FLASH2021.tuning;

    /* Module Configs */
    static final ModuleConfig Mod0 =
            new ModuleConfig(
                    Motors.driveMotor0, Motors.angleMotor0, 3, Mod0AngleOffset, physical, tuning);

    static final ModuleConfig Mod1 =
            new ModuleConfig(
                    Motors.driveMotor1, Motors.angleMotor1, 13, Mod1AngleOffset, physical, tuning);

    static final ModuleConfig Mod2 =
            new ModuleConfig(
                    Motors.driveMotor2, Motors.angleMotor2, 23, Mod2AngleOffset, physical, tuning);

    static final ModuleConfig Mod3 =
            new ModuleConfig(
                    Motors.driveMotor3, Motors.angleMotor3, 33, Mod3AngleOffset, physical, tuning);

    public static final ModuleConfig[] modules = new ModuleConfig[] {Mod0, Mod1, Mod2, Mod3};

    public static final SwerveConfig config = new SwerveConfig(physical, tuning, gyro, modules);
}
