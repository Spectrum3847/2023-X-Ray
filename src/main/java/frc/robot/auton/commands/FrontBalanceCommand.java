// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Robot;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.swerve.commands.SwerveDrive;

// Wait 5 seconds before driving out of tarmac
public class FrontBalanceCommand extends ParallelCommandGroup {
    /** Creates a new TestPathFollowing. */
    public FrontBalanceCommand() {
        addCommands(
                new SwerveDrive(() -> -AutonConfig.balanceDriveSpeed, () -> 0.0, () -> 0.0, false)
                        .until(
                                () ->
                                        (Math.abs(Robot.swerve.gyro.getRollRate())
                                                        > AutonConfig.stopDrivingVelocity)
                                                || (Math.abs(
                                                                Robot.swerve
                                                                                .gyro
                                                                                .getRawPitch()
                                                                                .getDegrees()
                                                                        + AutonConfig.gryoOffset)
                                                        < AutonConfig.stopDrivingAngle))
                        .andThen(new LockSwerve()));
    }
}
