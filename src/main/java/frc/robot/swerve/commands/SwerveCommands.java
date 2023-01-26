// Created by Spectrum3847
package frc.robot.swerve.commands;

import frc.robot.Robot;
import frc.robot.pilot.commands.PilotCommands;

public class SwerveCommands {
    public static void setupDefaultCommand() {
        Robot.swerve.setDefaultCommand(PilotCommands.pilotSwerve());
    }
}
