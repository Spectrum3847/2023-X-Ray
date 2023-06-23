// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Robot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveToCubeNode extends PIDCommand {
    /* Config settings */
    private static double kP = 0.06;
    private static double verticalSetpoint = -15; // neg
    private double tolerance = 1;
    private double horizontalOffset = 0; // positive is right (driver POV)

    private static double out = 0;
    private Command alignToTag;
    /** Creates a new DriveToCubeNode. */
    /**
     * Aligns to a cube node in both X and Y axes
     *
     * @param horizontalOffset adjustable offset in the Y axis in case robot isn't completely
     *     aligned. Default value should be 0
     */
    public DriveToCubeNode(double horizontalOffset) {
        super(
                // The controller that the command will use
                new PIDController(kP, 0, 0),
                // This should return the measurement
                () -> getVerticalOffset(),
                // This should return the setpoint (can also be a constant)
                () -> verticalSetpoint,
                // This uses the output
                output -> {
                    setOutput(output);
                });
        alignToTag = new AlignToAprilTag(() -> getOutput(), horizontalOffset);
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
        // Configure additional PID options by calling `getController` here.
        this.getController().setTolerance(tolerance);
    }

    @Override
    public void initialize() {
        super.initialize();
        alignToTag.initialize();
        out = 0;
    }

    @Override
    public void execute() {
        super.execute();
        alignToTag.execute();
    }

    @Override
    public void end(boolean interrupted) {
        alignToTag.end(interrupted);
        Robot.swerve.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // return Math.abs(out) <= 0.05;
        return false;
    }

    private static void setOutput(double output) {
        out = output;
        if (Math.abs(out) > 1) {
            out = 1 * Math.signum(out);
        }
        out = out * Robot.swerve.config.tuning.maxVelocity * 0.3;
    }

    public static double getOutput() {
        return out;
    }

    private static double getVerticalOffset() {
        double offset = Robot.vision.getVerticalOffset();
        return (offset == 0) ? verticalSetpoint : offset;
    }
}
