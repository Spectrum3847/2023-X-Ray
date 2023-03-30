// Created by Spectrum3847
// Based on Code from Team364 - BaseFalconSwerve
// https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.swerve.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.swerve.Swerve;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class SwerveDrive extends CommandBase {
    private boolean openLoop;

    private Swerve swerve;
    private DoubleSupplier fwdPositiveSupplier;
    private DoubleSupplier leftPositiveSupplier;
    private DoubleSupplier ccwPositiveSupplier;
    private DoubleSupplier velocityScalarSupplier;
    private BooleanSupplier isFieldRelativeSupplier;
    private Translation2d centerOfRotationMeters;

    /**
     * Creates a SwerveDrive command that allows for simple x and y translation of the robot and r
     * rotation.
     *
     * @param isFieldRelative
     * @param leftPositiveSupplier
     * @param fwdPositiveSupplier
     */
    public SwerveDrive(
            DoubleSupplier fwdPositiveSupplier,
            DoubleSupplier leftPositiveSupplier,
            DoubleSupplier ccwPositiveSupplier,
            DoubleSupplier velocityScalar,
            BooleanSupplier isFieldRelative,
            boolean openLoop,
            Translation2d centerOfRotationMeters) {
        this.swerve = Robot.swerve;
        addRequirements(swerve);
        this.isFieldRelativeSupplier = isFieldRelative;
        this.velocityScalarSupplier = velocityScalar;
        this.openLoop = openLoop;
        this.leftPositiveSupplier = leftPositiveSupplier;
        this.fwdPositiveSupplier = fwdPositiveSupplier;
        this.ccwPositiveSupplier = ccwPositiveSupplier;
        this.centerOfRotationMeters = centerOfRotationMeters;
    }

    public SwerveDrive(
            DoubleSupplier fwdPositiveSupplier,
            DoubleSupplier leftPositiveSupplier,
            DoubleSupplier ccwPositiveSupplier,
            DoubleSupplier velocityScalar,
            BooleanSupplier isFieldRelative,
            boolean openLoop) {
        this(
                fwdPositiveSupplier,
                leftPositiveSupplier,
                ccwPositiveSupplier,
                velocityScalar,
                isFieldRelative,
                openLoop,
                new Translation2d());
    }

    public SwerveDrive(
            DoubleSupplier fwdPositiveSupplier,
            DoubleSupplier leftPositiveSupplier,
            DoubleSupplier ccwPositiveSupplier,
            DoubleSupplier velocityScalar,
            BooleanSupplier isFieldRelative) {
        this(
                fwdPositiveSupplier,
                leftPositiveSupplier,
                ccwPositiveSupplier,
                velocityScalar,
                isFieldRelative,
                false,
                new Translation2d());
    }

    public SwerveDrive(
            DoubleSupplier fwdPositiveSupplier,
            DoubleSupplier leftPositiveSupplier,
            DoubleSupplier ccwPositiveSupplier,
            DoubleSupplier velocityScalar) {
        this(
                fwdPositiveSupplier,
                leftPositiveSupplier,
                ccwPositiveSupplier,
                velocityScalar,
                () -> true);
    }

    public SwerveDrive(
            DoubleSupplier fwdPositiveSupplier,
            DoubleSupplier leftPositiveSupplier,
            DoubleSupplier ccwPositiveSupplier) {
        this(fwdPositiveSupplier, leftPositiveSupplier, ccwPositiveSupplier, () -> 1.0);
    }

    public void intialize() {}

    @Override
    public void execute() {
        double fwdPositive = fwdPositiveSupplier.getAsDouble();
        double leftPositive = leftPositiveSupplier.getAsDouble();
        double ccwPositive = ccwPositiveSupplier.getAsDouble();
        double velocityScalar = velocityScalarSupplier.getAsDouble();
        boolean fieldRelative = isFieldRelativeSupplier.getAsBoolean();

        velocityScalar = Math.abs(velocityScalar);
        velocityScalar = velocityScalar > 1 ? 1.0 : velocityScalar;

        fwdPositive = fwdPositive * velocityScalar;
        leftPositive = leftPositive * velocityScalar;
        ccwPositive = ccwPositive * velocityScalar;

        swerve.drive(
                fwdPositive,
                leftPositive,
                ccwPositive,
                fieldRelative,
                openLoop,
                centerOfRotationMeters);
    }

    public void end(boolean interrupted) {
        swerve.stop();
    }
}
