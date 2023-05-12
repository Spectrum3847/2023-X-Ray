package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Threads;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.SpectrumLib.sim.PhysicsSim;
import frc.SpectrumLib.util.Network;
import frc.robot.auton.Auton;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.Intake;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.LEDs;
import frc.robot.leds.commands.CountdownLEDCommand;
import frc.robot.leds.commands.LEDCommands;
import frc.robot.operator.OperatorGamepad;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.PilotGamepad;
import frc.robot.pilot.commands.PilotCommands;
import frc.robot.pose.Pose;
import frc.robot.swerve.Swerve;
import frc.robot.swerve.commands.SwerveCommands;
import frc.robot.trajectories.Trajectories;
import frc.robot.vision.Vision;
import org.littletonrobotics.junction.LoggedRobot;

public class Robot extends LoggedRobot {
    public static RobotConfig config;
    public static RobotLog log;
    public static Swerve swerve;
    public static Pose pose;
    public static Trajectories trajectories;
    public static Elevator elevator;
    public static Intake intake;
    public static FourBar fourBar;
    public static Vision vision;
    public static LEDs leds;
    public static PilotGamepad pilotGamepad;
    public static OperatorGamepad operatorGamepad;
    public static RobotTelemetry telemetry;
    public static Auton auton;

    public static String MAC = "";

    public Robot(double period) {
        super(period);
    }

    // Intialize subsystems and run their setupDefaultCommand methods here
    private void intializeSystems() {
        System.out.println("Started InitSubsystems");
        vision = new Vision();
        System.out.println("Started Vision");
        swerve = new Swerve();
        System.out.println("Started Swerve");
        pose = new Pose();
        System.out.println("Started Pose");
        trajectories = new Trajectories();
        System.out.println("Started Trajectories");

        elevator = new Elevator();
        System.out.println("Started Elevator");
        intake = new Intake();
        System.out.println("Started Intake");
        fourBar = new FourBar();
        System.out.println("Started FourBar");

        leds = new LEDs();
        System.out.println("Started Led");
        pilotGamepad = new PilotGamepad();
        operatorGamepad = new OperatorGamepad();
        System.out.println("Started Gamepads");
        telemetry = new RobotTelemetry(RobotConfig.mainTabName);
        System.out.println("Started RobotTelemetry");
        auton = new Auton();
        System.out.println("Started Auton");

        // Set Default Commands, this method should exist for each subsystem that has
        // commands
        SwerveCommands.setupDefaultCommand();
        IntakeCommands.setupDefaultCommand();
        ElevatorCommands.setupDefaultCommand();
        FourBarCommands.setupDefaultCommand();
        PilotCommands.setupDefaultCommand();
        OperatorCommands.setupDefaultCommand();
        LEDCommands.setupDefaultCommand();

        System.out.println("Finished Setting Up Default Commands");
    }

    /**
     * Used in all robot mode intialize methods to cancel previous commands and reset button
     * bindings
     */
    public static void resetCommandsAndButtons() {
        CommandScheduler.getInstance().cancelAll(); // Disable any currently running commands
        CommandScheduler.getInstance().getActiveButtonLoop().clear();

        // Reset Config for all gamepads and other button bindings
        pilotGamepad.resetConfig();
        operatorGamepad.resetConfig();
        LEDCommands.setupLEDTriggers();
        ElevatorCommands.setupElevatorTriggers();
    }

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        RobotTelemetry.print("--- Robot Init Starting ---");
        Timer.delay(RobotConfig.robotInitDelay); // Wait for the robot to fully boot up
        // Set the MAC Address for this robot, useful for adjusting comp/practice bot
        // settings
        MAC = Network.getMACaddress();
        RobotTelemetry.print("Robot MAC: " + MAC);

        // Set up the config
        config = new RobotConfig();

        // Setup the logger
        log = new RobotLog(this);

        // Initialize all systems, do this after getting the MAC address
        intializeSystems();
        SmartDashboard.putData(CommandScheduler.getInstance());
        SmartDashboard.putBoolean("RecordMatch", false);
        SmartDashboard.putBoolean("StopRecording", false);
        RobotTelemetry.print("--- Robot Init Complete ---");
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Ensures that the main thread is the highest priority thread
        Threads.setCurrentThreadPriority(true, 99);
        /**
         * Runs the Scheduler. This is responsible for polling buttons, adding newly-scheduled
         * commands, running already-scheduled commands, removing finished or interrupted commands,
         * and running subsystem periodic() methods. This must be called from the robot's periodic
         * block in order for anything in the Command-based framework to work.
         */
        CommandScheduler.getInstance().run();

        Auton.printAutoDuration(); // Prints auton command duration if it finishes or cancelled

        Threads.setCurrentThreadPriority(true, 10); // Set the main thread back to normal priority
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {
        RobotTelemetry.print("## Disabled Init Starting");
        resetCommandsAndButtons();
        swerve.resetSteeringToAbsolute(); // reset the steering encoders to absolute value

        RobotTelemetry.print("## Disabled Init Complete");
    }

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {
        RobotTelemetry.print("## Disabled Exit");
    }

    @Override
    public void autonomousInit() {
        RobotTelemetry.print("@@ Auton Init Starting");
        resetCommandsAndButtons();
        swerve.setLastAngleToCurrentAngle(); // Should set the current falcon angle to the last
        // angle
        swerve.gyro.setAngleOffset(swerve.gyro.getRawPitch().getDegrees());

        Command autonCommand = Auton.getAutonomousCommand();
        if (autonCommand != null) {
            autonCommand.schedule();
            Auton.startAutonTimer();
        }
        if (DriverStation.isFMSAttached()) {
            SmartDashboard.putBoolean("RecordMatch", true);
        }

        if (DriverStation.getMatchTime() != -1) {
            new CountdownLEDCommand(
                            "Auton Countdown", 120, (int) DriverStation.getMatchTime(), false)
                    .schedule();
        }

        RobotTelemetry.print("@@ Auton Init Complete");
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {
        RobotTelemetry.print("@@ Auton Exit");
    }

    @Override
    public void teleopInit() {
        RobotTelemetry.print("$$ Teleop Init Starting");
        resetCommandsAndButtons();

        /*if (vision.botPose.getX() >= 0.3 || vision.isInMap() || vision.multipleTargetsInView()) {
            pose.resetPoseEstimate(Robot.vision.botPose);
        }*/
        swerve.setLastAngleToCurrentAngle(); // Should set the current falcon angle to the last
        // angle

        // ElevatorCommands.safeHome().withTimeout(1).schedule(); // home elevator

        RobotTelemetry.print("$$ Teleop Init Complete");
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {
        RobotTelemetry.print("$$ Teleop Exit");
        // vision.poseOverriden = false;

        // Send the stop recording boolean
        if (DriverStation.isFMSAttached()) {
            SmartDashboard.putBoolean("StopRecording", true);
        }
    }

    @Override
    public void testInit() {
        RobotTelemetry.print("~~ Test Init Starting");
        resetCommandsAndButtons();
        swerve.telemetry.testMode();
        intake.telemetry.testMode();

        RobotTelemetry.print("~~ Test Init Complete");
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {
        RobotTelemetry.print("~~ Test Exit");
    }

    /** This function is called once when a simulation starts */
    public void simulationInit() {
        RobotTelemetry.print("--- Simulation Init Starting ---");

        RobotTelemetry.print("--- Simulation Init Complete ---");
    }

    /** This function is called periodically during a simulation */
    public void simulationPeriodic() {
        PhysicsSim.getInstance().run();
    }
}
