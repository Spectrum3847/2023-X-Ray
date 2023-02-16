package frc.robot.trajectories;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kGenPathMaxSpeed = 3;
    public static final double kGenPathMaxAccel = 3;

    // PID Values for 2023
    public static final double kPTranslationController = 0.6;
    public static final double kITranslationController = 0;
    public static final double kDTranslationController = 0;
    public static final double kPRotationController = 5;
    public static final double kIRotationController = 0;
    public static final double kDRotationController = 0.01;

    // Constant Values
    public static final double constantRotation = 180; // This is constant for all On-the-Fly Paths
    // Heading values for On-the-Fly Generations
    public static final double constantHeading = 180;
    public static final double finalXPosition =
            1.80; // This stays constant for all On-The-Fly Paths
    // Scoring Positions (these stay constant throughout all On-The-Fly Paths)
    public static final double lineupXPositionModifier = 0.2;
    public static final double coneTTYPosition = 4.97;
    public static final double cubeTYPosition = 4.42;
    public static final double coneTBYPosition = 3.85;
    public static final double coneMTYPosition = 3.29;
    public static final double cubeMYPosition = 2.75;
    public static final double coneMBYPosition = 2.17;
    public static final double coneBTYPosition = 1.62;
    public static final double cubeBYPosition = 1.05;
    public static final double coneBBYPosition = 0.51;
    public static final double xPositions[] = new double[] {5.20, 5.00, 3.75, 2.45};

    // On-the-Fly Y Positions
    public static final double clearYPosition =
            4.60; // This stays constant for all clear On-The-Fly Paths
    public static final double bumpYPosition =
            0.85; // This stays constant for all bump On-The-Fly Paths
    public static final double changeYPositionLine = 2.65;
}
