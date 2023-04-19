// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.auton.Auton;

public class CubeHybridSpinUp extends CommandBase {
    Command spinUp;

    public CubeHybridSpinUp() {
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize() {
        if (Auton.score3rd.getSelected() == true) {
            spinUp = AutonCommands.cubeHybridSpinUp();
        } else {
            spinUp = new WaitCommand(0);
        }
        spinUp.initialize();
    }

    @Override
    public void execute() {
        spinUp.execute();
    }

    @Override
    public void end(boolean interrupted) {
        spinUp.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
