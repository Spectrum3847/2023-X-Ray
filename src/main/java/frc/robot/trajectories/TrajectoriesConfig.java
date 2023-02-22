package frc.robot.trajectories;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kGenPathMaxSpeed = 3;
    public static final double kGenPathMaxAccel = 3;
    public static final double kGenPathBumpSpeed = 2;

    // PID Values for 2023
    public static final double kPTranslationController = 4.5; // try ~5
    public static final double kITranslationController = 0;
    public static final double kDTranslationController = 0;
    public static final double kPRotationController = 5; // try ~0.5
    public static final double kIRotationController = 0;
    public static final double kDRotationController = 0.01; // try to remove

    public static final double fieldWidth = 8.02; // Shouldn't need anymore

    // Constant Values
    public static final double constantBlueRotation = 180;
    public static final double constantRedRotation = 0;
    // Heading values for On-the-Fly Generations
    public static final double constantBlueHeading = 180;
    public static final double constantRedHeading = 0;
    public static final double finalBlueXPosition = 1.80;
    public static final double finalRedXPosition = 14.75;
    // Scoring Positions (these stay constant throughout all On-The-Fly Paths)
    public static final double lineupXPositionModifier = 0.2;
    // These are tuned for the Blue Alliance (everything is opposite in Red Alliance Scoring)
    public static final double coneTTYPosition = 4.97;
    public static final double cubeTYPosition = 4.42;
    public static final double coneTBYPosition = 3.85;
    public static final double coneMTYPosition = 3.29;
    public static final double cubeMYPosition = 2.75;
    public static final double coneMBYPosition = 2.17;
    public static final double coneBTYPosition = 1.62;
    public static final double cubeBYPosition = 1.05;
    public static final double coneBBYPosition = 0.51;
    public static final double blueXPositions[] = new double[] {5.20, 5.00, 3.75, 2.45};
    public static final double redXPositions[] = new double[] {11.35, 11.55, 12.80, 14.10};

    // On-the-Fly Y Positions
    public static final double clearYPosition =
            4.80; // This stays constant for all clear On-The-Fly Paths
    public static final double bumpYPosition =
            0.7; // This stays constant for all bump On-The-Fly Paths
    public static final double changeYPositionLine = 2.65;
}
