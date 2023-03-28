package frc.robot.auton;

public class AutonConfig {

    /* Time in seconds*/
    public static final double intakeTime = 0.75;
    public static final double retractTime = 1.5;
    public static final double spinUpTime = 0.5;
    public static final double launchTime = 0.3;
    public static final double stopTime = 0.001;

    // speeds
    public static final double kMaxSpeed = 3; // 2 = too slow
    public static final double kMaxAccel = 3; // 2 worked but took too long //2.5 = too slow
    public static final double kMaxMobilitySpeed = 1.5;
    public static final double kMaxMobilityAccel = 1.5;

    // Balance values
    public static final double stopDrivingAngle = 4;
    public static final double balancedAngle = 4;
    public static final double balanceDriveSpeed = 0.3;
    public static final double balanceSlowDriveSpeed = 0.25;
}
