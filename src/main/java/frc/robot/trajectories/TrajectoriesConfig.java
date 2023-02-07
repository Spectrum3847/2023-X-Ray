package frc.robot.trajectories;

import frc.robot.swerve.SwerveConfig;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kMaxAngularSpeedRadiansPerSecond = SwerveConfig.maxAngularVelocity;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared =
            SwerveConfig.maxAngularVelocity;

    // PID Values for 2023
    public static final double kPTranslationController = 0.6;
    public static final double kITranslationController = 0;
    public static final double kDTranslationController = 0;
    public static final double kPRotationController = 5;
    public static final double kIRotationController = 0;
    public static final double kDRotationController = 0.01;

    // Alliance Positions
    public static final double XPosition = 1.8; // This stays constant for all On-The-Fly Paths
    public static final double ConeRRYPosition = 0.5;
    public static final double CubeRYPosition = 1.05;
    public static final double ConeLRYPosition = 1.6;
    public static final double ConeRMYPosition = 2.2;
    public static final double CubeMYPosition = 2.75;
    public static final double ConeLMYPosition = 3.3;
    public static final double ConeRLYPosition = 3.85;
    public static final double CubeLYPosition = 4.42;
    public static final double ConeLLYPosition = 4.95;
}
