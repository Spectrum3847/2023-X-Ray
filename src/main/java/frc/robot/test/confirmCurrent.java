// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.SpectrumLib.util.Util;
import frc.robot.RobotTelemetry;

public class confirmCurrent extends CommandBase {
    private WPI_TalonFX motor;
    private double current;
    private double targetCurrent;
    private String name;

    public confirmCurrent(String name, WPI_TalonFX motor, double targetCurrent) {
        this.motor = motor;
        this.targetCurrent = targetCurrent;
        this.name = name;
    }

    @Override
    public void initialize() {
        current = motor.getSupplyCurrent();
        RobotTelemetry.print(
                name + " current is in range: " + Util.closeTo(current, targetCurrent, 0.1));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
