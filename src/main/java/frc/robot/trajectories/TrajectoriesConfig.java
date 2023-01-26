package frc.robot.trajectories;

import frc.robot.swerve.SwerveConfig;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kMaxAngularSpeedRadiansPerSecond = SwerveConfig.maxAngularVelocity;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared =
            SwerveConfig.maxAngularVelocity;

    public static final double kPXController = 0.6;
    public static final double kDXController = 0;
    public static final double kPYController = kPXController;
    public static final double kDYController = kDXController;
    public static final double kPThetaController = 4;
    public static final double kDThetaController = 0.01;
}
