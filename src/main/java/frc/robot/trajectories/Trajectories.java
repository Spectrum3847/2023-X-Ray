// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.trajectories;

import com.pathplanner.lib.server.PathPlannerServer;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import java.util.function.DoubleSupplier;

public class Trajectories extends SubsystemBase {
    public double targetYposition = 0;
    public PIDController thetaController;
    public PIDController xController =
            new PIDController(
                    TrajectoriesConfig.kPTranslationController,
                    0,
                    TrajectoriesConfig.kDTranslationController);;
    public PIDController yController =
            new PIDController(
                    TrajectoriesConfig.kPTranslationController,
                    0,
                    TrajectoriesConfig.kDTranslationController);;
    public Rotation2d startAngle;

    /** Creates a new Trajectory. */
    public Trajectories() {
        thetaController =
                new PIDController(
                        TrajectoriesConfig.kPRotationController,
                        0,
                        TrajectoriesConfig.kDRotationController);
        // Setup thetaController used for auton and automatic turns
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        xController =
                new PIDController(
                        TrajectoriesConfig.kPTranslationController,
                        0,
                        TrajectoriesConfig.kDTranslationController);

        yController =
                new PIDController(
                        TrajectoriesConfig.kPTranslationController,
                        0,
                        TrajectoriesConfig.kDTranslationController);

        PathPlannerServer.startServer(
                5811); // 5811 = port number. adjust this according to your needs
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

    public DoubleSupplier calculateYSupplier(DoubleSupplier goalY) {
        return () ->
                yController.calculate(
                        Robot.swerve.odometry.getPoseMeters().getY(), goalY.getAsDouble());
    }

    public DoubleSupplier calculateYSupplier(double goalY) {
        return calculateYSupplier(() -> goalY);
    }

    public void addTargetYDistance(double y) {
        targetYposition += y;
    }

    public void resetTargetYDistance() {
        targetYposition = Robot.swerve.odometry.getPoseMeters().getY();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
