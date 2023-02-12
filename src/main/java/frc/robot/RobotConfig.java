package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public final class RobotConfig {

    public static final String mainTabName = "Main";
    public static final Double robotInitDelay = 2.0; // Seconds to wait before starting robot code
    public static final boolean enableLogging = true; // Enable logging to file

    public final String Canivore = "3847";
    public final Motors motors = new Motors();
    public final Pneumatic pneumatic = new Pneumatic();
    public final String praticeBotMAC = "00:80:2F:23:E9:33";
    public final ModuleType PowerDistributionType = ModuleType.kCTRE;

    public static final int pigeonID = 0;
    public static final int ledPWMport = 0;

    // Timeout constants
    public static final int kLongCANTimeoutMs = 100;
    public static final int kCANTimeoutMs = 10;

    public final class Motors {
        public static final int driveMotor0 = 1;
        public static final int angleMotor0 = 2;
        public static final int driveMotor1 = 11;
        public static final int angleMotor1 = 12;
        public static final int driveMotor2 = 21;
        public static final int angleMotor2 = 22;
        public static final int driveMotor3 = 31;
        public static final int angleMotor3 = 32;

        public static final int launcherMotor = 50; // unused
        public static final int intakeMotor = 52; // unused
        public static final int elevatorMotor = 40;
        public static final int fourBarMotor = 45;
    }

    public final class Pneumatic {
        public final int ExamplePneumatic = 0;
    }

    private RobotType robotType;

    public RobotConfig() {
        RobotTelemetry.createTab(mainTabName);
        checkRobotType();
        switch (getRobotType()) {
            case COMP:
                break;
            case PRACTICE:
                break;
            case SIM:
            case REPLAY:
                // Set all the constants specifically for the simulation
                break;
        }
    }

    /** Set the RobotType based on if simulation or the MAC address of the RIO */
    public RobotType checkRobotType() {
        if (Robot.isSimulation()) {
            robotType = RobotType.SIM;
            RobotTelemetry.print("Robot Type: Simulation");
        } else if (Robot.MAC.equals(praticeBotMAC)) {
            robotType = RobotType.PRACTICE;
            RobotTelemetry.print("Robot Type: Practice");
        } else {
            robotType = RobotType.COMP;
            RobotTelemetry.print("Robot Type: Competition");
        }
        return robotType;
    }

    public RobotType getRobotType() {
        return robotType;
    }

    public enum RobotType {
        COMP,
        PRACTICE,
        SIM,
        REPLAY
    }
}
