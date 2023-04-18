package frc.robot.auton;

public class AutonConfig {

    /* Time in seconds*/
    public static final double intakeTime = 0.75;
    public static final double retractTime = 1.5;
    public static final double spinUpTime = 0.5;
    public static final double launchTime = 0.3;
    public static final double stopTime = 0.001;

    // speeds
    public static final double kMaxSpeed = 3;
    public static final double kMaxAccel = 3.2;
    public static final double kMaxFastSpeed = 3.4;
    public static final double kMaxFastAccel = 4;
    public static final double kMaxBalanceSpeed = 2.0;
    public static final double kMaxBalanceAccel = 1.5; // helps not jump off charge station
    public static final double kMaxCleanSpeed = 4;
    public static final double kMaxCleanAccel = 4;
    public static final double kMaxBumpSpeed = 3;
    public static final double kMaxBumpAccel = 3;
    public static final double kMaxSpecialAccel = 4.2;
    public static final double kMaxAngleAccel = 3.3; // 3 is too slow for 3 ball bottom w angle
    public static final double kMaxMobilitySpeed = 1.5;
    public static final double kMaxMobilityAccel = 1.5;

    // Balance values
    public static final double stopDrivingRate = 7.1; // 6 too low 8 works
    public static final double stopDrivingAngle = 3.5;
    public static final double balanceDriveSpeed = 0.6; // .3 works on 8
    public static final double gryoOffset = 1.0107421875;

    // Node Alignment Time
    public static final double midCubeAlignTime = 1.3;
    public static final double midCubeAlignTime2 = 2.5;
}
