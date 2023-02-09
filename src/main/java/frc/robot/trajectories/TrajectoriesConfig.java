package frc.robot.trajectories;

import frc.robot.swerve.SwerveConfig;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kGenPathMaxSpeed = 2;
    public static final double kGenPathMaxAccel = 2;
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
    public static final double finalRotation = 180;

    public static final double firstXPosition =
            5.30; // This stays constant for all On-The-Fly Paths
    public static final double secondXPosition =
            2.50; // This stays constant for all On-The-Fly Paths

    public static final double firstYPosition =
            4.50; // This stays constant for all On-The-Fly Paths
    public static final double secondYPosition =
            4.40; // This stays constant for all On-The-Fly Paths

    public static final double firstHeading =
            180; // This stays constant for all On-The-Fly Paths
    public static final double secondHeading =
            180; // This stays constant for all On-The-Fly Paths

    public static final double finalXPosition =
            1.81; // This stays constant for all On-The-Fly Paths

        // Scoring Positions (these stay constant throughout all On-The-Fly Paths)
    public static final double coneTTYPosition = 4.97;
    public static final double cubeTPosition = 4.42;
    public static final double coneTBYPosition = 3.85;
    public static final double coneMTYPosition = 3.29;
    public static final double cubeMYPosition = 2.75;
    public static final double coneMBYPosition = 2.17;
    public static final double coneBTPosition = 1.62;
    public static final double cubeBYPosition = 1.05;
    public static final double coneBBYPosition = 0.51;

    public static final double coneTTHeading = 140.00;
    public static final double cubeTHeading = 178.25;
    public static final double coneTBHeading = -140.50;
    public static final double coneMTHeading = -120.50;
    public static final double cubeMHeading = -112.00;
    public static final double coneMBHeading = -106.50;
    public static final double coneBTHeading = -103.50;
    public static final double cubeBHeading = -101.15;
    public static final double coneBBHeading = -99.75;
}
