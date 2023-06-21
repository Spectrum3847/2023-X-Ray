package frc.robot.auton;

import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import frc.robot.auton.commands.AutoPaths;
import frc.robot.auton.commands.AutonCommands;
import frc.robot.auton.commands.CubeHybridSpinUp;
import frc.robot.auton.commands.EjectCone;
import frc.robot.auton.commands.LeftCubeTaxiCommand;
import frc.robot.auton.commands.MiddleCubeTaxiCommand;
import frc.robot.auton.commands.RightCubeTaxiCommand;
import frc.robot.auton.commands.SetConeSpitPosition;
import frc.robot.auton.commands.TaxiCommand;
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.trajectories.TrajectoriesConfig;
import java.util.HashMap;

public class Auton {
    public static final SendableChooser<Command> autonChooser = new SendableChooser<>();
    public static final SendableChooser<Boolean> score3rd = new SendableChooser<>();
    private static boolean autoMessagePrinted = true;
    private static double autonStart = 0;
    public static HashMap<String, Command> eventMap =
            new HashMap<>(); // Stores all the values of the event map

    public Auton() {
        setupEventMap(); // sets the eventmap to run during auto
        setupSelectors(); // runs the command to start the chooser for auto on shuffleboard
    }

    // Autobuilder only using odometry (running this at Waco)
    public static SwerveAutoBuilder getAutoBuilder() {
        return new SwerveAutoBuilder(
                Robot.swerve.odometry::getPoseMeters, // Pose2d supplier
                Robot.swerve.odometry
                        ::resetOdometry, // Pose2d consumer, used to reset odometry at the
                // beginning of auto
                Robot.swerve.config.swerveKinematics, // SwerveDriveKinematics
                new PIDConstants(
                        TrajectoriesConfig.kPTranslationController,
                        TrajectoriesConfig.kITranslationController,
                        TrajectoriesConfig.kDTranslationController), // PID constants to correct for
                // translation error (used to create
                // the X and Y PID controllers)
                new PIDConstants(
                        TrajectoriesConfig.kPRotationController,
                        TrajectoriesConfig.kIRotationController,
                        TrajectoriesConfig
                                .kDRotationController), // PID constants to correct for rotation
                // error (used to create the
                // rotation controller)
                Robot.swerve
                        ::setModuleStatesAuto, // Module states consumer used to output to the drive
                // subsystem
                eventMap, // Gets the event map values to use for running addional
                // commands during auto
                true, // Should the path be automatically mirrored depending on
                // alliance color
                // Alliance.
                Robot.swerve // The drive subsystem. Used to properly set the requirements of
                // path following commands
                );
    }

    // Autobuilder w/Vision (not run at Waco)
    static SwerveAutoBuilder getVisionAutoBuilder() {
        return new SwerveAutoBuilder(
                Robot.pose::getEstimatedPose, // Pose2d supplier
                Robot.swerve.odometry
                        ::resetOdometry, // Pose2d consumer, used to reset odometry at the
                // beginning of auto
                Robot.swerve.config.swerveKinematics, // SwerveDriveKinematics
                new PIDConstants(
                        TrajectoriesConfig.kPTranslationController,
                        TrajectoriesConfig.kITranslationController,
                        TrajectoriesConfig.kDTranslationController), // PID constants to correct for
                // translation error (used to create
                // the X and Y PID controllers)
                new PIDConstants(
                        TrajectoriesConfig.kPRotationController,
                        TrajectoriesConfig.kIRotationController,
                        TrajectoriesConfig
                                .kDRotationController), // PID constants to correct for rotation
                // error (used to create the
                // rotation controller)
                Robot.swerve
                        ::setModuleStatesAuto, // Module states consumer used to output to the drive
                // subsystem
                Auton.eventMap, // Gets the event map values to use for running addional
                // commands during auto
                true, // Should the path be automatically mirrored depending on
                // alliance color
                // Alliance.
                Robot.swerve // The drive subsystem. Used to properly set the requirements of
                // path following commands
                );
    }

    // A chooser for autonomous commands
    public static void setupSelectors() {
        // Advanced comp autos with odometry (Ordered by likelyhood of running)
        autonChooser.setDefaultOption("Clean Side", AutoPaths.CleanSide());
        autonChooser.addOption("Test Cone Mid", AutonCommands.coneMid());
        autonChooser.addOption("Test Clean Side", AutoPaths.TestCleanSide1());
        autonChooser.addOption("New Bump Side", AutoPaths.NewBumpSide());
        autonChooser.addOption("Over Charge", AutoPaths.OverCharge());
        autonChooser.addOption("Bump Side 3", AutoPaths.BumpSide3());
        autonChooser.addOption("Clean Side 3 Red", AutoPaths.CleanSide3Good());
        autonChooser.addOption("Clean Side 2 Good", AutoPaths.CleanSide2Good());
        autonChooser.addOption("Test Cone Placement", new SetConeSpitPosition());
        autonChooser.addOption("Test Cone Spit", new EjectCone());
        autonChooser.addOption("Test Cone Place", AutonCommands.autonConeFloorGoalPostion());

        score3rd.setDefaultOption("True", true);
        score3rd.addOption("False", false);

        autonChooser.addOption("Clean Side 2", AutoPaths.CleanSide2());
        autonChooser.addOption("Bump Side 2", AutoPaths.BumpSide2());
        autonChooser.addOption("3 Ball Bottom Balance", AutoPaths.BallBottomBalance());
        autonChooser.addOption("Special", AutoPaths.Special());
        autonChooser.addOption("3 Ball Bottom Angle", AutoPaths.BallBottomAngle());
        // Simple comp autos
        autonChooser.addOption("Taxi Simple", new TaxiCommand());
        autonChooser.addOption("Left Cube Taxi", new LeftCubeTaxiCommand());
        autonChooser.addOption("Right Cube Taxi", new RightCubeTaxiCommand());
        autonChooser.addOption("Middle Cube Taxi", new MiddleCubeTaxiCommand());
        autonChooser.addOption(
                "Nothing",
                new PrintCommand("Doing Nothing in Auton")
                        .andThen(new WaitCommand(5))); // setups an auto that does nothing
        // // Autos for tuning/testing (not used at comp)
        // autonChooser.addOption(
        // "1 Meter",
        // getAutoBuilder()
        // .fullAuto(
        // PathPlanner.loadPathGroup(
        // "1 Meter",
        // new PathConstraints(
        // AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
        // autonChooser.addOption(
        // "3 Meters",
        // getAutoBuilder()
        // .fullAuto(
        // PathPlanner.loadPathGroup(
        // "3 Meters",
        // new PathConstraints(
        // AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
        // autonChooser.addOption(
        // "5 Meters",
        // getAutoBuilder()
        // .fullAuto(
        // PathPlanner.loadPathGroup(
        // "5 Meters",
        // new PathConstraints(
        // AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel))));
    }

    // Adds event mapping to autonomous commands
    public static void setupEventMap() {
        // Cube Shooting Commmands
        eventMap.put("FirstShot", AutonCommands.firstShot()); // Tuned correctly
        eventMap.put("CoolShot", AutonCommands.coolShot()); // Tuned correctly
        eventMap.put("SecondShot", AutonCommands.secondShot()); // Tuned Correctly
        eventMap.put("AngleThirdShot", AutonCommands.angleThirdShot()); // Need to be tuned to run
        eventMap.put("SimpleLaunchCube", AutonCommands.simpleLaunchCube());
        eventMap.put("CleanShot", AutonCommands.cleanShot());
        eventMap.put("CubeMidSpinUp", AutonCommands.cubeMidSpinUp());
        eventMap.put("CubeHybridSpinUp", new CubeHybridSpinUp());
        eventMap.put("CubeMidEject", AutonCommands.cubeMidEject());
        eventMap.put("AlignToGridMid", AutonCommands.alignToGridMid());
        // Cone placing Commands
        eventMap.put("ConeMid", AutonCommands.coneMid());
        eventMap.put("ConeMidFull", AutonCommands.coneMidFull());
        eventMap.put("ConeTop", AutonCommands.coneTop());
        eventMap.put("ConeHybridPlacement", new SetConeSpitPosition());
        eventMap.put("ConeHybrid", new EjectCone());
        // Intake Commands
        eventMap.put("IntakeCube", AutonCommands.intakeCube());
        eventMap.put("IntakeCone", AutonCommands.intakeCone());
        eventMap.put("RetractIntake", AutonCommands.retractIntake());
        // Drivetrain Commands
        eventMap.put("LockSwerve", new LockSwerve());
        eventMap.put("FaceForward", AutonCommands.faceForward());
        eventMap.put("FaceBackward", AutonCommands.faceBackward());
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
