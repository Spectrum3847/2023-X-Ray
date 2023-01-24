// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trajectories;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.auton.AutonConfig;
import java.util.function.DoubleSupplier;

public class Trajectories extends SubsystemBase {
    public PIDController thetaController;
    public PIDController xController =
            new PIDController(AutonConfig.kPXController, 0, AutonConfig.kDXController);;
    public PIDController yController =
            new PIDController(AutonConfig.kPYController, 0, AutonConfig.kDYController);;
    public Rotation2d startAngle;

    /** Creates a new Trajectory. */
    public Trajectories() {
        thetaController =
                new PIDController(
                        TrajectoriesConfig.kPThetaController,
                        0,
                        TrajectoriesConfig.kDThetaController);
        // Setup thetaController used for auton and automatic turns
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        xController =
                new PIDController(
                        TrajectoriesConfig.kPXController, 0, TrajectoriesConfig.kDXController);

        yController =
                new PIDController(
                        TrajectoriesConfig.kPYController, 0, TrajectoriesConfig.kDYController);
    }

    public void resetTheta() {
        startAngle = Robot.pose.getHeading();
        thetaController.reset();
    }

    public Rotation2d getStartAngle() {
        return startAngle;
    }

    public double calculteTheta(double goalAngleRadians) {
        return thetaController.calculate(Robot.pose.getHeading().getRadians(), goalAngleRadians);
    }

    public DoubleSupplier calculateThetaSupplier(DoubleSupplier goalAngleSupplierRadians) {
        return () -> calculteTheta(goalAngleSupplierRadians.getAsDouble());
    }

    public DoubleSupplier calculteThetaSupplier(double goalAngle) {
        return calculateThetaSupplier(() -> goalAngle);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
