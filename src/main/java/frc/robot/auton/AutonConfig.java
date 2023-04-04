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
    public static final double kMaxAngleAccel = 3.25; // 3 is too slow for 3 ball bottom w angle
    public static final double kMaxMobilitySpeed = 1.5;
    public static final double kMaxMobilityAccel = 1.5;

    // Balance values
    public static final double stopDrivingRate = 7.1; // 6 too low 8 works
    public static final double stopDrivingAngle = 2.5;
    public static final double balanceDriveSpeed = 0.6; // .3 works on 8
    public static final double gryoOffset = 1.0107421875;
}
