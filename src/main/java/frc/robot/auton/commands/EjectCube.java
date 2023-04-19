// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.auton.Auton;

public class EjectCube extends CommandBase {
    Command launchCube;

    public EjectCube() {
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize() {
        if (Auton.score3rd.getSelected() == true) {
            launchCube = AutonCommands.secondShotLaunch();
        } else {
            launchCube = new WaitCommand(0);
        }

        launchCube.initialize();
    }

    @Override
    public void execute() {
        launchCube.execute();
    }

    @Override
    public void end(boolean interrupted) {
        launchCube.end(interrupted);
        AutonCommands.retractIntake().execute();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
