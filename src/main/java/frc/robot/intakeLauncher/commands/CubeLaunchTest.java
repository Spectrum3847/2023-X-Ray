// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intakeLauncher.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.intakeLauncher.Intake;

public class CubeLaunchTest extends CommandBase {
    double frontSpeed = 0;
    double launchSpeed = 0;
    /** Creates a new CubeLaunchTest. */

    /**
     * Creates a new CubeLaunchTest
     *
     * @param launchSpeed Launcher rpm. Front launcher is set to launch speed.
     */
    public CubeLaunchTest(double launchSpeed) {
        this.launchSpeed = launchSpeed;
        this.frontSpeed = launchSpeed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    /**
     * Creates a new CubeLaunchTest
     *
     * @param launchSpeed Launcher rpm. Front launcher is set to launch speed.
     */
    public CubeLaunchTest(double launchSpeed, double frontSpeed) {
        this.launchSpeed = launchSpeed;
        this.frontSpeed = frontSpeed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Robot.intake.setVelocities(Intake.config.lowerSpinUpSpeed, frontSpeed, launchSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
