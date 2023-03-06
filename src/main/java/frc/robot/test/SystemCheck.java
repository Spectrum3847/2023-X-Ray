// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.test.commands.ConfirmCommand;

public class SystemCheck extends SubsystemBase {
  public final CheckScheduler scheduler;

  /** Creates a new SystemCheck. */
  public SystemCheck() {
    scheduler = new CheckScheduler(this);
    scheduler.addCheck(new Check("Test", new ConfirmCommand("Test")));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
