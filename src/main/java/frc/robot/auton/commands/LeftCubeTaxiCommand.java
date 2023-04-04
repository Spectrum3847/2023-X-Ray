// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.swerve.commands.SwerveDrive;

// Wait 5 seconds before driving out of tarmac
public class LeftCubeTaxiCommand extends SequentialCommandGroup {
    /** Creates a new TestPathFollowing. */
    public LeftCubeTaxiCommand() {
        addCommands(
                AutonCommands.simpleLaunchCube(),
                new SwerveDrive(() -> -0.25, () -> -1, () -> 0.0).withTimeout(0.5),
                new SwerveDrive(() -> -2.0, () -> 0.0, () -> 0.0).withTimeout(3));
    }

    Rotation2d finalRotation() {
        return new Rotation2d(Math.PI);
    }
}
