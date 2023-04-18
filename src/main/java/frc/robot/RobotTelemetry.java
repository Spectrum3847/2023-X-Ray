package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.SpectrumLib.telemetry.Alert;
import frc.SpectrumLib.telemetry.Alert.AlertType;
import frc.SpectrumLib.telemetry.TelemetrySubsystem;
import frc.SpectrumLib.util.Network;
import frc.SpectrumLib.util.Util;
import frc.robot.auton.Auton;
import frc.robot.elevator.Elevator;
import java.util.Map;

public class RobotTelemetry extends TelemetrySubsystem {

    private static boolean disablePrints = false;

    // Alerts
    private static Alert PracticeRobotAlert = new Alert("PRACTICE ROBOT", AlertType.INFO);
    private static Alert CompetitionRobotAlert = new Alert("COMPETITION ROBOT", AlertType.INFO);
    private static Alert SimRobotAlert = new Alert("SIM ROBOT", AlertType.INFO);
    private static Alert batteryAlert = new Alert("Low Battery < 12v", AlertType.WARNING);
    private static Alert FMSConnectedAlert = new Alert("FMS Connected", AlertType.INFO);
    private static Alert logReceiverQueueAlert =
            new Alert("NOT LOGGING: Queue Exceede", AlertType.ERROR);

    private String IPaddress = "UNKOWN";

    public RobotTelemetry(String name) {
        super(name);
        logCommands();

        // Column 0
        // Setup the auton selector to display on shuffleboard
        Auton.setupSelectors();
        tab.add("Auton Selection", Auton.autonChooser).withPosition(0, 0).withSize(2, 1);
        tab.add("Score 3rd GP", Auton.score3rd).withPosition(0, 1).withSize(2, 1);
        // Column 2
        tab.addBoolean("Connected?", () -> flash())
                .withPosition(2, 0)
                .withSize(1, 1)
                .withProperties(
                        Map.of("Color when true", "#300068", "Color when false", "#FFFFFF"));

        // tab.addBoolean("Pose Overriden", () -> Robot.vision.poseOverriden)
        //         .withPosition(3, 2)
        //         .withSize(1, 1)
        //         .withProperties(
        //                 Map.of("Color when true", "#50C878", "Color when false", "#FFFFFF"));
        // tab.addBoolean("Vision Integrated", () -> Robot.vision.visionIntegrated)
        //         .withPosition(2, 3)
        //         .withSize(1, 1)
        //         .withProperties(
        //                 Map.of("Color when true", "#50C878", "Color when false", "#FFFFFF"));
        // tab.addBoolean("Multiple Targets Seen", () -> Robot.vision.multipleTargetsInView())
        //         .withPosition(3, 4)
        //         .withSize(1, 1)
        //         .withProperties(
        //                 Map.of("Color when true", "#50C878", "Color when false", "#FFFFFF"));
        // tab.addBoolean("Limelight Status", () -> Robot.vision.visionConnected)
        //         .withPosition(2, 4)
        //         .withSize(1, 1)
        //         .withProperties(
        //                 Map.of("Color when true", "#50C878", "Color when false", "#FF0000"));
        tab.addNumber("LLVerticalOffset", () -> Robot.vision.verticalOffset).withPosition(3, 4);
        tab.addNumber("LLHorizontalOffset", () -> Robot.vision.horizontalOffset).withPosition(3, 5);
        tab.addNumber("LLXDistance", () -> Robot.vision.getDistanceToTarget()).withPosition(3, 5);

        tab.addNumber("Match Time", () -> Timer.getMatchTime())
                .withPosition(2, 1)
                .withSize(2, 2)
                .withWidget("Simple Dial")
                .withProperties(Map.of("Min", 0, "Max", 135));

        // Column 4
        tab.add("Alerts", SmartDashboard.getData("Alerts")).withPosition(4, 0).withSize(2, 2);
        tab.add("MAC Address", Robot.MAC).withPosition(4, 2).withSize(2, 1);
        tab.addString("IP Address", () -> getIP()).withPosition(4, 3).withSize(2, 1);
        tab.addNumber("ElevatorPos", () -> Elevator.falconToInches(Robot.elevator.getPosition()))
                .withPosition(6, 0);
        tab.addNumber("FourBarPercentAngle", () -> Robot.fourBar.getPercentAngle())
                .withPosition(6, 1); // 72000 is max
        // tab.addNumber("Elevator kF", () -> Robot.elevator.getKf()).withPosition(6, 1);
    }

    public static void createTab(String name) {
        Shuffleboard.getTab(name);
    }

    @Override
    public void periodic() {
        checkRobotType();
        checkFMSalert();
        checkBatteryWhenDisabledalert();
        checkLogQueueAlert();
        logCommands();
    }

    public String getIP() {
        if (IPaddress == "UNKOWN") {
            IPaddress = Network.getIPaddress();
        }
        return IPaddress;
    }

    private void checkRobotType() {
        switch (Robot.config.getRobotType()) {
            case XRAY2023:
                CompetitionRobotAlert.set(true);
                PracticeRobotAlert.set(false);
                SimRobotAlert.set(false);
                break;
            case PRACTICE2023:
            case ALPHA2023:
            case FLASH2021:
            case INFRARED3847:
            case GAMMA2021:
                CompetitionRobotAlert.set(false);
                PracticeRobotAlert.set(true);
                SimRobotAlert.set(false);
                break;
            case SIM:
            case REPLAY:
                CompetitionRobotAlert.set(false);
                PracticeRobotAlert.set(false);
                SimRobotAlert.set(true);
                break;
        }
    }

    private static void checkFMSalert() {
        FMSConnectedAlert.set(DriverStation.isFMSAttached());
    }

    private static void checkBatteryWhenDisabledalert() {
        batteryAlert.set(DriverStation.isDisabled() && Util.checkBattery(12.0));
    }

    private static boolean flash() {
        return (int) Timer.getFPGATimestamp() % 2 == 0;
    }

    private static void checkLogQueueAlert() {
        logReceiverQueueAlert.set(Robot.log.logger.getReceiverQueueFault());
    }

    private static void logCommands() {
        // Allows us to see all running commands on the robot, needed to log commands
        SmartDashboard.putData(CommandScheduler.getInstance());

        // Log scheduled commands
        Robot.log.logger.recordOutput(
                "ActiveCommands/Scheduler",
                NetworkTableInstance.getDefault()
                        .getEntry("/SmartDashboard/Scheduler/Names")
                        .getStringArray(new String[] {}));
    }

    public static void print(String output) {
        if (!disablePrints) {
            System.out.println(output);
        }
    }
}
