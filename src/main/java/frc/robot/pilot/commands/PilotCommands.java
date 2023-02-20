package frc.robot.pilot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.pilot.PilotConfig;
import frc.robot.swerve.commands.SwerveDrive;
import frc.robot.trajectories.TrajectoriesCommands;
import java.util.function.DoubleSupplier;

/** Should contain commands used only by the pilot controller and to rumble pilot command */
public class PilotCommands {

    /** Set default command to turn off the rumble */
    public static void setupDefaultCommand() {
        Robot.pilotGamepad.setDefaultCommand(
                rumble(0, 9999).repeatedly().withName("DisablePilotRumble"));
    }

    /** Field Oriented Drive */
    public static Command pilotSwerve() {
        return new SwerveDrive(
                        () -> Robot.pilotGamepad.getDriveFwdPositive(),
                        () -> Robot.pilotGamepad.getDriveLeftPositive(),
                        () -> Robot.pilotGamepad.getDriveCCWPositive())
                .withName("PilotSwerve");
    }

    public static Command slowMode() {
        return new SwerveDrive(
                        () -> Robot.pilotGamepad.getDriveFwdPositive() * PilotConfig.slowModeScaler,
                        () ->
                                Robot.pilotGamepad.getDriveLeftPositive()
                                        * PilotConfig.slowModeScaler,
                        () -> Robot.pilotGamepad.getDriveCCWPositive() * PilotConfig.slowModeScaler)
                .withName("SlowMode");
    }

    /** Robot Oriented Drive */
    public static Command fpvPilotSwerve() {
        return new SwerveDrive(
                        () -> Robot.pilotGamepad.getDriveFwdPositive(),
                        () -> Robot.pilotGamepad.getDriveLeftPositive(),
                        () -> Robot.pilotGamepad.getDriveCCWPositive(),
                        false)
                .withName("fpvPilotSwerve");
    }

    public static Command slowModeFPV() {
        return new SwerveDrive(
                        () -> Robot.pilotGamepad.getDriveFwdPositive() * PilotConfig.slowModeScaler,
                        () ->
                                Robot.pilotGamepad.getDriveLeftPositive()
                                        * PilotConfig.slowModeScaler,
                        () -> Robot.pilotGamepad.getDriveCCWPositive() * PilotConfig.slowModeScaler,
                        false)
                .withName("SlowModeFPV");
    }

    public static Command snakeDrive() {
        return TrajectoriesCommands.resetThetaController()
                .andThen(
                        new SwerveDrive(
                                () -> Robot.pilotGamepad.getDriveFwdPositive(),
                                () -> Robot.pilotGamepad.getDriveLeftPositive(),
                                Robot.trajectories.calculateThetaSupplier(
                                        () -> Robot.pilotGamepad.getDriveAngle()),
                                true,
                                false,
                                PilotConfig.intakeCoRmeters))
                .withName("SnakeDrive");
    }

    /**
     * Drive the robot and control orientation using the right stick
     *
     * @return
     */
    public static Command stickSteer() {
        return aimPilotDrive(() -> Robot.pilotGamepad.getRightStickAngle()).withName("StickSteer");
    }

    /** Drive while aiming to a specific angle, uses theta controller from Trajectories */
    public static Command aimPilotDrive(double goalAngleRadians) {
        return aimPilotDrive(() -> goalAngleRadians);
    }

    /** Reset the Theata Controller and then run the SwerveDrive command and pass a goal Supplier */
    public static Command aimPilotDrive(DoubleSupplier goalAngleSupplierRadians) {
        return TrajectoriesCommands.resetThetaController()
                .andThen(
                        new SwerveDrive(
                                () -> Robot.pilotGamepad.getDriveFwdPositive(),
                                () -> Robot.pilotGamepad.getDriveLeftPositive(),
                                Robot.trajectories.calculateThetaSupplier(goalAngleSupplierRadians),
                                true,
                                false))
                .withName("AimPilotDrive");
    }

    /** Command that can be used to rumble the pilot controller */
    public static Command rumble(double intensity, double durationSeconds) {
        return new RunCommand(() -> Robot.pilotGamepad.rumble(intensity), Robot.pilotGamepad)
                .withTimeout(durationSeconds)
                .withName("RumblePilot");
    }

    /**
     * Overrides Estimated and Odometry Pose to be Vision pose. Should be used at the start of the
     * match in Disabled when the camera can see at least two tags in order to correctly get the
     * starting position. Can also be used if odometry is very inaccurate for some reason.
     */
    public static Command resetEstimatedPose() {
        return new RunCommand(
                        () -> {
                            if (Robot.vision.botPose.getX() <= 0.3) {
                                Robot.pose.resetPoseEstimate(Robot.vision.botPose);
                            }
                        })
                .withName("ResetOdometryPose")
                .ignoringDisable(true);
    }
}
