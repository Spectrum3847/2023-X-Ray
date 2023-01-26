package frc.robot.auton;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import frc.robot.auton.commands.AutoBuilder;
import java.util.HashMap;

public class Auton {
    public static final SendableChooser<Command> autonChooser = new SendableChooser<>();
    private static boolean autoMessagePrinted = true;
    private static double autonStart = 0;
    public static HashMap<String, Command> eventMap = new HashMap<>();

    public Auton() {
        setupSelectors();
        setupEventMap();
    }

    // A chooser for autonomous commands
    public static void setupSelectors() {
        autonChooser.setDefaultOption(
                "Nothing", new PrintCommand("Doing Nothing in Auton").andThen(new WaitCommand(5)));
        // autonChooser.addOption("5 Ball w Balance", new FollowPath("5 Ball w Balance", true));
        // autonChooser.addOption("5 Ball", new FollowPath("5 Ball", false));
        autonChooser.addOption(
                "1 Meter",
                AutoBuilder.autoBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "1 Meter",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
        autonChooser.addOption(
                "5 Ball",
                AutoBuilder.autoBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "5 Ball",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
        autonChooser.addOption(
                "Test Path",
                AutoBuilder.autoBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "Test Path",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
    }

    // Adds event mapping to autonomous commands
    public static void setupEventMap() {
        eventMap.put("marker1", new PrintCommand("Passed marker 1"));
        eventMap.put("marker2", new PrintCommand("Passed marker 2"));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public static Command getAutonomousCommand() {
        // return new CharacterizeLauncher(Robot.launcher);
        Command auton = autonChooser.getSelected();
        if (auton != null) {
            return auton;
        } else {
            return new PrintCommand("*** AUTON COMMAND IS NULL ***");
        }
    }

    /** This method is called in AutonInit */
    public static void startAutonTimer() {
        autonStart = Timer.getFPGATimestamp();
        autoMessagePrinted = false;
    }

    /** Called in RobotPeriodic and displays the duration of the auton command Based on 6328 code */
    public static void printAutoDuration() {
        Command autoCommand = Auton.getAutonomousCommand();
        if (autoCommand != null) {
            if (!autoCommand.isScheduled() && !autoMessagePrinted) {
                if (DriverStation.isAutonomousEnabled()) {
                    RobotTelemetry.print(
                            String.format(
                                    "*** Auton finished in %.2f secs ***",
                                    Timer.getFPGATimestamp() - autonStart));
                } else {
                    RobotTelemetry.print(
                            String.format(
                                    "*** Auton CANCELLED in %.2f secs ***",
                                    Timer.getFPGATimestamp() - autonStart));
                }
                autoMessagePrinted = true;
            }
        }
    }
}
