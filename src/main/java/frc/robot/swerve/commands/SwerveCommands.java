// Created by Spectrum3847
package frc.robot.swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.pilot.commands.PilotCommands;

public class SwerveCommands {
    public static void setupDefaultCommand() {
        Robot.swerve.setDefaultCommand(PilotCommands.pilotSwerve());
    }

    public static Command resetSteeringToAbsolute(){
        return new InstantCommand(() -> Robot.swerve.resetSteeringToAbsolute(), Robot.swerve)
                .withName("ResetEncodersToAbsolute");
    }
}
