package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public final class RobotConfig {

    public static final String mainTabName = "Main";
    public static final Double robotInitDelay = 2.0; // Seconds to wait before starting robot code
    public static final boolean enableLogging = true; // Enable logging to file

    public final String Canivore = "3847";
    public final Motors motors = new Motors();
    public final Pneumatic pneumatic = new Pneumatic();
    public final String pratice2023BotMAC = "00:80:2F:19:0D:CE";
    public final String alpha2023BotMAC = "00:80:2F:23:E9:33";
    public final String flash2021BotMAC = "00:80:2F:32:FC:79";
    public final String xray2023BotMAC = "00:80:2F:22:50:6D";
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

        public static final int elevatorMotor = 41;
        public static final int fourBarMotor = 42;

        public static final int lowerRoller = 51;
        public static final int frontRoller = 52;
        public static final int launcher = 53;
    }

    public final class Pneumatic {
        public final int ExamplePneumatic = 0;
    }

    private RobotType robotType = null;

    public RobotConfig() {
        RobotTelemetry.createTab(mainTabName);
        checkRobotType();
        switch (getRobotType()) {
            case XRAY2023:
                break;
            case PRACTICE2023:
                break;
            case ALPHA2023:
            case GAMMA2021:
            case INFRARED3847:
            case FLASH2021:
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
        } else if (Robot.MAC.equals(pratice2023BotMAC)) {
            robotType = RobotType.PRACTICE2023;
            RobotTelemetry.print("Robot Type: Practice-2023");
        } else if (Robot.MAC.equals(alpha2023BotMAC)) {
            robotType = RobotType.ALPHA2023;
            RobotTelemetry.print("Robot Type: Alpha-2023");
        } else if (Robot.MAC.equals(flash2021BotMAC)) {
            robotType = RobotType.FLASH2021;
            RobotTelemetry.print("Robot Type: FLASH-2021");
        } else {
            robotType = RobotType.XRAY2023;
            RobotTelemetry.print("Robot Type: X-RAY-2023");
        }
        return robotType;
    }

    public RobotType getRobotType() {
        return robotType;
    }

    public enum RobotType {
        XRAY2023,
        PRACTICE2023,
        ALPHA2023,
        FLASH2021,
        GAMMA2021,
        INFRARED3847,
        SIM,
        REPLAY
    }
}
