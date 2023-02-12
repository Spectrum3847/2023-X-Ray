package frc.robot.swerve;

import frc.SpectrumLib.swerve.SwerveModuleConfig;

public class ModuleConfig extends SwerveModuleConfig {
    public final int AbsAngleSensor;
    public final boolean isTTBsensor;

    public ModuleConfig(
            int angleMotorID,
            int driveMotorID,
            int AbsAngleSensor,
            double angleOffset,
            boolean isTTBsensor) {
        super(angleMotorID, driveMotorID, AbsAngleSensor, angleOffset, 0);
        this.AbsAngleSensor = AbsAngleSensor;
        this.isTTBsensor = isTTBsensor;
    }
}
