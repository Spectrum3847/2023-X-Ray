// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.gyros;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public interface GyroIO {

    public Rotation2d getRawYaw();

    public Rotation2d getRawAngle();

    public Rotation2d getRawPitch();

    public Rotation2d getRawRoll();

    public double getRollRate();

    public double getPitchRate();

    public void setAngleOffset(double currentAngle);

    public double getAngleOffset();
}
