// Created by Spectrum3847
package frc.robot.swerve.configTemplates;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public class SwerveConfig {
    public final PhysicalConfig physical;
    public final TuningConfig tuning;
    public final GyroConfig gyro;
    public final ModuleConfig[] modules;
    public final SwerveDriveKinematics swerveKinematics;

    public SwerveConfig(
            PhysicalConfig physical, TuningConfig tuning, GyroConfig gyro, ModuleConfig[] modules) {
        this.physical = physical;
        this.tuning = tuning;
        this.gyro = gyro;
        this.modules = modules;

        swerveKinematics =
                new SwerveDriveKinematics(
                        physical.frontLeftLocation,
                        physical.frontRightLocation,
                        physical.backLeftLocation,
                        physical.backRightLocation);
    }
}
