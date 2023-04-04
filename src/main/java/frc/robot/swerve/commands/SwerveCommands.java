// Created by Spectrum3847
package frc.robot.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Robot;
import frc.robot.pilot.commands.PilotCommands;
import java.util.function.DoubleSupplier;

public class SwerveCommands {
    public static void setupDefaultCommand() {
        Robot.swerve.setDefaultCommand(PilotCommands.pilotHeadingLock());
    }

    public static Command resetSteeringToAbsolute() {
        return new InstantCommand(() -> Robot.swerve.resetSteeringToAbsolute(), Robot.swerve)
                .withName("ResetEncodersToAbsolute")
                .ignoringDisable(true);
    }

    public static Command resetTurnController() {
        return new InstantCommand(() -> Robot.swerve.resetRotationController(), Robot.swerve)
                .withName("ResetTurnController");
    }

    public static Command brakeMode() {
        return new RunCommand(() -> Robot.swerve.brakeMode(true), Robot.swerve)
                .withName("BrakeMode")
                .ignoringDisable(true);
    }

    public static Command brakeMode(double timeout) {
        return brakeMode().withTimeout(timeout);
    }

    public static Command stop() {
        return new SwerveDrive(() -> 0.0, () -> 0.0, () -> 0.0).withName("SwerveStop");
    }

    /** Reset the Theata Controller and then run the SwerveDrive command and pass a goal Supplier */
    public static Command aimDrive(
            DoubleSupplier fwdPositive,
            DoubleSupplier leftPositive,
            DoubleSupplier goalAngleSupplierRadians) {
        return SwerveCommands.resetTurnController()
                .andThen(
                        new SwerveDrive(
                                fwdPositive,
                                leftPositive,
                                () ->
                                        Robot.swerve.calculateRotationController(
                                                goalAngleSupplierRadians),
                                () -> 1.0))
                .withName("AimDrive");
    }
}
