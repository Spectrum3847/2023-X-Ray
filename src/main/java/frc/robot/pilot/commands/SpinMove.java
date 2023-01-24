package frc.robot.pilot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.swerve.Swerve;
import frc.robot.swerve.SwerveConfig;
import java.util.function.DoubleSupplier;

public class SpinMove extends CommandBase {
    private Translation2d translation;
    private boolean centerHasBeenSet = false;

    private Swerve swerve;
    private DoubleSupplier leftPositiveSupplier;
    private DoubleSupplier fwdPositiveSupplier;
    private DoubleSupplier ccwPositiveSupplier;
    private Translation2d centerOfRotationMeters;

    /** Creates a new DodgeDrive. */
    public SpinMove() {
        swerve = Robot.swerve;
        centerOfRotationMeters = new Translation2d();
        fwdPositiveSupplier = Robot.pilotGamepad::getDriveFwdPositive;
        leftPositiveSupplier = Robot.pilotGamepad::getDriveLeftPositive;
        ccwPositiveSupplier = Robot.pilotGamepad::getDriveCCWPositive;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        centerHasBeenSet = false;
    }

    @Override
    public void execute() {
        double fwdPositive = fwdPositiveSupplier.getAsDouble();
        double leftPositive = leftPositiveSupplier.getAsDouble();
        double ccwPositive = ccwPositiveSupplier.getAsDouble();

        translation = new Translation2d(fwdPositive, leftPositive);

        Rotation2d heading = translation.getAngle().minus(Robot.pose.getHeading());
        double angle = heading.getDegrees();

        if (Math.abs(ccwPositive) >= 0.2 && !centerHasBeenSet) {
            Translation2d offsets[] = SwerveConfig.moduleOffsets(Units.inchesToMeters(3));
            if (angle < 45 && angle >= -45) {
                // negative rotation is clockwise
                // positive rotation is counter-clockwise
                if (ccwPositive > 0) {
                    centerOfRotationMeters = SwerveConfig.frontRightLocation.plus(offsets[1]);
                } else {
                    centerOfRotationMeters = SwerveConfig.frontLeftLocation.plus(offsets[0]);
                }
            } else if (angle >= 45 && angle < 135) {
                if (ccwPositive > 0) {
                    centerOfRotationMeters = SwerveConfig.frontLeftLocation.plus(offsets[0]);
                } else {
                    centerOfRotationMeters = SwerveConfig.backLeftLocation.plus(offsets[2]);
                }
            } else if (angle >= 135 || angle < -135) {
                if (ccwPositive > 0) {
                    centerOfRotationMeters = SwerveConfig.backLeftLocation.plus(offsets[2]);
                } else {
                    centerOfRotationMeters = SwerveConfig.backRightLocation.plus(offsets[3]);
                }
            } else if (angle >= -135 && angle < -45) {
                if (ccwPositive > 0) {
                    centerOfRotationMeters = SwerveConfig.backRightLocation.plus(offsets[3]);
                } else {
                    centerOfRotationMeters = SwerveConfig.frontRightLocation.plus(offsets[1]);
                }
            }
            centerHasBeenSet = true;
        }
        swerve.drive(fwdPositive, leftPositive, ccwPositive, true, false, centerOfRotationMeters);
    }

    public void end(boolean interrupted) {
        swerve.stop();
    }
}
