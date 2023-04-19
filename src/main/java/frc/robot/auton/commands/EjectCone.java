// Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.auton.Auton;
import frc.robot.intakeLauncher.commands.IntakeCommands;

public class EjectCone extends CommandBase {
    Command ejectConeCommand;

    public EjectCone() {
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize() {
        if (Auton.score3rd.getSelected() == true) {
            ejectConeCommand = IntakeCommands.floorEject();
        } else {
            ejectConeCommand = new WaitCommand(0);
        }

        ejectConeCommand.initialize();
    }

    @Override
    public void execute() {
        ejectConeCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        ejectConeCommand.end(interrupted);
        AutonCommands.retractIntake().execute();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
