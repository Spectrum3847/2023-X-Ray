package frc.robot.pilot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.pose.commands.PoseCommands;
import frc.robot.swerve.commands.HeadingLock;
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.swerve.commands.SwerveCommands;
import frc.robot.swerve.commands.SwerveDrive;
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
                        () -> Robot.pilotGamepad.getDriveCCWPositive(),
                        () -> Robot.pilotGamepad.getPilotScalar(),
                        () -> !Robot.pilotGamepad.fpvButton().getAsBoolean())
                .withName("PilotSwerve");
    }

    public static Command pilotHeadingLock() {
        return new HeadingLock(
                        () -> Robot.pilotGamepad.getDriveFwdPositive(),
                        () -> Robot.pilotGamepad.getDriveLeftPositive(),
                        () -> Robot.pilotGamepad.getPilotScalar(),
                        () -> !Robot.pilotGamepad.fpvButton().getAsBoolean())
                .withName("PilotHeadingLock");
    }

    /*public static Command snakeDrive() {
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
    }*/

    /**
     * Drive the robot and control orientation using the right stick
     *
     * @return
     */
    public static Command stickSteer() {
        return aimPilotDrive(() -> Robot.pilotGamepad.getRightStickCardinals())
                .withName("PilotStickSteer");
    }

    /** Drive while aiming to a specific angle, uses theta controller from Trajectories */
    public static Command aimPilotDrive(double goalAngleRadians) {
        return aimPilotDrive(() -> goalAngleRadians);
    }

    /** Reset the Theata Controller and then run the SwerveDrive command and pass a goal Supplier */
    public static Command aimPilotDrive(DoubleSupplier goalAngleSupplierRadians) {
        return SwerveCommands.resetTurnController()
                .andThen(
                        new SwerveDrive(
                                () -> Robot.pilotGamepad.getDriveFwdPositive(),
                                () -> Robot.pilotGamepad.getDriveLeftPositive(),
                                () ->
                                        Robot.swerve.calculateRotationController(
                                                goalAngleSupplierRadians),
                                () -> Robot.pilotGamepad.getPilotScalar()))
                .withName("AimPilotDrive");
    }

    /** Command that can be used to rumble the pilot controller */
    public static Command rumble(double intensity, double durationSeconds) {
        return new RunCommand(() -> Robot.pilotGamepad.rumble(intensity), Robot.pilotGamepad)
                .withTimeout(durationSeconds)
                .withName("RumblePilot");
    }

    /** Command that can be used to rumble the pilot controller */
    public static Command conditionalRumble(
            double elevatorPosition, double intensity, double durationSeconds) {
        return new RunCommand(
                        () -> {
                            if (Elevator.falconToInches(Robot.elevator.getPosition())
                                    >= Elevator.config.cubeTop - 0.5) {
                                Robot.pilotGamepad.rumble(intensity);
                            }
                        },
                        Robot.pilotGamepad)
                .withTimeout(durationSeconds)
                .withName("ConditionalRumblePilot");
    }

    /** Reorient the Robot */
    public static Command reorient(double angle) {
        return PoseCommands.resetHeading(angle)
                .alongWith(rumble(0.5, 1), SwerveCommands.resetSteeringToAbsolute())
                .withName("PilotReorient");
    }

    /** LockSwerve */
    public static Command lockSwerve() {
        return new LockSwerve().withName("PilotLockSwerve");
    }

    /** Reset steering if a falcon is being weird */
    public static Command resetSteering() {
        return SwerveCommands.resetSteeringToAbsolute();
    }

    // /**
    //  * Overrides Estimated and Odometry Pose to be Vision pose. Should be used at the start of
    // the
    //  * match in Disabled when the camera can see at least two tags in order to correctly get the
    //  * starting position. Can also be used if odometry is very inaccurate for some reason.
    //  */
    // public static Command resetEstimatedPose() {
    //     return new RunCommand(
    //                     () -> {
    //                         if (Robot.vision.botPose.getX() <= 0.3) {
    //                             Robot.pose.resetPoseEstimate(Robot.vision.botPose);
    //                         }
    //                     })
    //             .withName("ResetOdometryPose")
    //             .ignoringDisable(true);
    // }
}
