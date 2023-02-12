package frc.robot.trajectories;

public class TrajectoriesConfig {
    /* Swerve Conroller Constants */
    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kGenPathMaxSpeed = 0.5;
    public static final double kGenPathMaxAccel = 0.5;

    // PID Values for 2023
    public static final double kPTranslationController = 0.6;
    public static final double kITranslationController = 0;
    public static final double kDTranslationController = 0;
    public static final double kPRotationController = 5;
    public static final double kIRotationController = 0;
    public static final double kDRotationController = 0.01;

    // Alliance Positions

    // Constant Values
    public static final double constantRotation = 180; // This is constant for all On-the-Fly Paths
    public static final double finalXPosition =
            1.81; // This stays constant for all On-The-Fly Paths
    // Scoring Positions (these stay constant throughout all On-The-Fly Paths)
    public static final double coneTTYPosition = 4.97;
    public static final double cubeTYPosition = 4.42;
    public static final double coneTBYPosition = 3.85;
    public static final double coneMTYPosition = 3.29;
    public static final double cubeMYPosition = 2.75;
    public static final double coneMBYPosition = 2.17;
    public static final double coneBTPosition = 1.62;
    public static final double cubeBYPosition = 1.05;
    public static final double coneBBYPosition = 0.51;

    // Top X Positions
    public static final double topFirstXPosition =
            5.30; // This stays constant for all Top On-The-Fly Paths
    public static final double topSecondXPosition =
            2.50; // This stays constant for all Top On-The-Fly Paths

    // Top Y Positions
    public static final double topFirstYPosition =
            4.50; // This stays constant for all Top On-The-Fly Paths
    public static final double topSecondYPosition =
            4.40; // This stays constant for all Top On-The-Fly Paths

    // Top constant heading values
    public static final double topFirstHeading =
            180; // This stays constant for all Top On-The-Fly Paths
    public static final double topSecondHeading =
            180; // This stays constant for all Top On-The-Fly Paths

    // Heading values for Top On-the-Fly Generations
    public static final double topConeTTHeading = 140.00;
    public static final double topCubeTHeading = 178.25;
    public static final double topConeTBHeading = -140.50;
    public static final double topConeMTHeading = -120.50;
    public static final double topCubeMHeading = -112.00;
    public static final double topConeMBHeading = -106.50;
    public static final double topConeBTHeading = -103.50;
    public static final double topCubeBHeading = -101.15;
    public static final double topConeBBHeading = -99.75;

    // Bottom X Positions
    public static final double bottomFirstXPosition =
            5.30; // This stays constant for all Bottom On-The-Fly Paths
    public static final double bottomSecondXPosition =
            2.50; // This stays constant for all Bottom On-The-Fly Paths

    // Bottom Y Positions
    public static final double bottomFirstYPosition =
            1; // This stays constant for all Bottom On-The-Fly Paths
    public static final double bottomSecondYPosition =
            1; // This stays constant for all Bottom On-The-Fly Paths

    // Bottom constant heading values
    public static final double bottomFirstHeading =
            180; // This stays constant for all Bottom On-The-Fly Paths
    public static final double bottomSecondHeading =
            180; // This stays constant for all Bottom On-The-Fly Paths

    // Heading values for Bottom On-the-Fly Generations
    public static final double bottomConeTTHeading = 99.5;
    public static final double bottomCubeTHeading = 100.90;
    public static final double bottomConeTBHeading = 103.00;
    public static final double bottomConeMTHeading = 106.00;
    public static final double bottomCubeMHeading = 110.50;
    public static final double bottomConeMBHeading = 119.25;
    public static final double bottomConeBTHeading = 136.70;
    public static final double bottomCubeBHeading = 176.00;
    public static final double bottomConeBBHeading = -143.00;
}
