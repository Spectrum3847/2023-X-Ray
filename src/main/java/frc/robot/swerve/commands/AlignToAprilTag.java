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
    PIDCommand fwdControllerCommand;
    DoubleSupplier fwdPositiveSupplier;
    private static double horizontalOutput;
    private static double verticalOutput;

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
                output -> setHorizontalOutput(output),
                Robot.swerve
                // Use the output here
                );
        this.getController().setTolerance(tolerance);
        fwdControllerCommand =
                new PIDCommand(
                        // The controller that the command will use
                        new PIDController(kP, 0, 0),
                        // This should return the measurement
                        () -> Robot.vision.getVerticalOffset(),
                        // This should return the setpoint (can also be a constant)
                        () -> (-14),
                        // This uses the output
                        output -> setVerticalOutput(output),
                        Robot.vision // this is bad
                        // Use the output here
                        );
        fwdControllerCommand.getController().setTolerance(tolerance);
        driveCommand =
                new SwerveDrive(
                        () -> getVerticalOutput(), // Allows pilot to drive fwd and rev
                        () -> getHorizontalOutput(), // Moves us center to the tag
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

    public static void setHorizontalOutput(double output) {
        horizontalOutput = calculateOutput(output, false);
    }

    public static void setVerticalOutput(double output) {
        verticalOutput = calculateOutput(output, true);
    }

    public static double calculateOutput(double output, boolean vertical) {
        if (!vertical) {
            output = -1 * output;
        }
        if (Math.abs(horizontalOutput) > 1) {
            output = 1 * Math.signum(horizontalOutput);
        }

        return output * Robot.swerve.config.tuning.maxVelocity * 0.3;
    }

    public static double getHorizontalOutput() {
        return horizontalOutput;
    }

    public static double getVerticalOutput() {
        return verticalOutput;
    }

    @Override
    public void initialize() {
        super.initialize();
        horizontalOutput = 0;
        verticalOutput = 0;
        Robot.swerve.resetRotationController();
        driveCommand.initialize();
        fwdControllerCommand.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        driveCommand.execute();
        fwdControllerCommand.execute();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
