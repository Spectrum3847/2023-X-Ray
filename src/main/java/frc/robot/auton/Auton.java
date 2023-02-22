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
import frc.robot.auton.commands.LeftCubeTaxiCommand;
import frc.robot.auton.commands.MiddleCubeTaxiCommand;
import frc.robot.auton.commands.RightCubeTaxiCommand;
import frc.robot.auton.commands.TaxiCommand;
import frc.robot.trajectories.TrajectoriesConfig;
import frc.robot.trajectories.commands.PathBuilder;
import java.util.HashMap;

public class Auton {
    public static final SendableChooser<Command> autonChooser = new SendableChooser<>();
    private static boolean autoMessagePrinted = true;
    private static double autonStart = 0;
    public static HashMap<String, Command> eventMap =
            new HashMap<>(); // Stores all the values of the event map

    public Auton() {
        setupSelectors(); // runs the command to start the chooser for auto on shuffleboard
        setupEventMap(); // sets the eventmap to run during auto
    }

    // A chooser for autonomous commands
    public static void setupSelectors() {
        autonChooser.setDefaultOption(
                "Nothing",
                new PrintCommand("Doing Nothing in Auton")
                        .andThen(new WaitCommand(5))); // setups an auto that does nothing
        autonChooser.addOption("Taxi Simple", new TaxiCommand());
        autonChooser.addOption("Left Cube Taxi", new LeftCubeTaxiCommand());
        autonChooser.addOption("Right Cube Taxi", new RightCubeTaxiCommand());
        autonChooser.addOption("Middle Cube Taxi", new MiddleCubeTaxiCommand());
        autonChooser.addOption(
                "1 Meter",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "1 Meter",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig
                                                .kMaxAccel)))); // sets an auto to drive one meter
        // forward
        autonChooser.addOption(
                "3 Meters",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "3 Meters",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig
                                                .kMaxAccel)))); // sets an auto to drive one meter
        // forward
        autonChooser.addOption(
                "5 Meters",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "5 Meters",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig
                                                .kMaxAccel)))); // sets an auto to drive one meter
        // forward

        autonChooser.addOption(
                "5 Ball",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "5 Ball",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig
                                                .kMaxAccel)))); // runs the 5 ball auto that is set
        // in pathplanner
        autonChooser.addOption(
                "Test Path",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "Test Path",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig
                                                .kMaxAccel)))); // run a test path to see how things
        // are supposed to be on the field
        autonChooser.addOption(
                "2 Ball Bottom",
                PathBuilder.pathBuilder.fullAuto(
                        PathPlanner.loadPathGroup(
                                "2 Ball Bottom",
                                new PathConstraints(
                                        TrajectoriesConfig.kMaxSpeed,
                                        TrajectoriesConfig.kMaxAccel))));
    }

    // Adds event mapping to autonomous commands
    public static void setupEventMap() {
        eventMap.put(
                "marker1",
                new PrintCommand(
                        "Passed marker 1")); // sample of what the eventmap can do doesn't run
        // unless there is an event maker with the name marker1
        eventMap.put(
                "marker2",
                new PrintCommand(
                        "Passed marker 2")); // sample of what the eventmap can do doesn't run
        // unless there is an event maker with the name marker2
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public static Command getAutonomousCommand() {
        // return new CharacterizeLauncher(Robot.launcher);
        Command auton = autonChooser.getSelected(); // sees what auto is chosen on shuffleboard
        if (auton != null) {
            return auton; // checks to make sure there is an auto and if there is it runs an auto
        } else {
            return new PrintCommand(
                    "*** AUTON COMMAND IS NULL ***"); // runs if there is no auto chosen, which
            // shouldn't happen because of the default
            // auto set to nothing which still runs
            // something
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
