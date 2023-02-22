// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.swerve.commands.SwerveDrive;

// Wait 5 seconds before driving out of tarmac
public class LeftCubeTaxiCommand extends ParallelCommandGroup {
    /** Creates a new TestPathFollowing. */
    public LeftCubeTaxiCommand() {
        addCommands(
                OperatorCommands.cubeTop().withTimeout(2),
                new WaitCommand(2)
                        .andThen(
                                new SwerveDrive(() -> -0.25, () -> -1, () -> 0.0, false)
                                        .withTimeout(0.5))
                        .andThen(
                                new SwerveDrive(() -> -2.0, () -> 0.0, () -> 0.0, false)
                                        .withTimeout(3)));
    }

    Rotation2d finalRotation() {
        return new Rotation2d(Math.PI);
    }
}
