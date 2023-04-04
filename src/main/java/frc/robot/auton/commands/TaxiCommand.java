// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.swerve.commands.SwerveDrive;

// Wait 5 seconds before driving out of tarmac
public class TaxiCommand extends ParallelCommandGroup {
    /** Creates a new TestPathFollowing. */
    public TaxiCommand() {
        addCommands(
                new WaitCommand(5),
                new SwerveDrive(() -> -2, () -> 0.0, () -> 0.0).withTimeout(1.5));
    }

    Rotation2d finalRotation() {
        return new Rotation2d(Math.PI);
    }
}
