// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Robot;
import java.util.function.DoubleSupplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignToAprilTag extends PIDCommand {

    private static double kP = 0.035;
    private static double tolerance = 2;
    SwerveDrive driveCommand;
    DoubleSupplier fwdPositiveSupplier;
    private static double out;

    /** Creates a new AlignToAprilTag. */
    public AlignToAprilTag(DoubleSupplier fwdPositiveSupplier) {
        super(
                // The controller that the command will use
                new PIDController(kP, 0, 0),
                // This should return the measurement
                () -> Robot.vision.getHorizontalOffset(),
                // This should return the setpoint (can also be a constant)
                () -> 0,
                // This uses the output
                output -> setOutput(output),
                Robot.swerve
                // Use the output here
                );

        this.getController().setTolerance(tolerance);
        driveCommand =
                new SwerveDrive(
                        fwdPositiveSupplier, // Allows pilot to drive fwd and rev
                        () -> getOutput(), // Moves us center to the tag
                        () -> getSteering(), // Aligns to grid
                        () -> 1.0, // full velocity
                        () -> true); // Field relative is true
        // Use addRequirements() here to declare subsystem dependencies.
        // Configure additional PID options by calling `getController` here.
        this.setName("AlignToAprilTag");
    }

    public double getSteering() {
        return Robot.swerve.calculateRotationController(() -> Math.PI);
    }

    public static void setOutput(double output) {
        out = -1 * output;
        if (Math.abs(out) > 1) {
            out = 1 * Math.signum(out);
        }

        out = out * Robot.swerve.config.tuning.maxVelocity * 0.3;
    }

    public static double getOutput() {
        return out;
    }

    @Override
    public void initialize() {
        super.initialize();
        out = 0;
        Robot.swerve.resetRotationController();
        driveCommand.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        driveCommand.execute();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
