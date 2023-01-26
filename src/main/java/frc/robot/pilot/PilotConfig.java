package frc.robot.pilot;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.swerve.SwerveConfig;

/** Constants used by the Pilot Gamepad */
public class PilotConfig {
    public static final int port = 0;

    public static final double throttleDeadband = 0.15;
    public static final double throttleExp = 1.5;
    public static final double throttleScaler = SwerveConfig.maxVelocity * 0.5;
    public static final boolean xInvert = true;
    public static final boolean yInvert = true;

    public static final double steeringDeadband = 0.15;
    public static final double steeringExp = throttleExp;
    public static final double steeringScaler = SwerveConfig.maxAngularVelocity * 0.4;
    public static final boolean steeringInvert = true;

    public static final Translation2d intakeCoRmeters = new Translation2d(0, 0);

    public static final double launcherValue = 0;
}
