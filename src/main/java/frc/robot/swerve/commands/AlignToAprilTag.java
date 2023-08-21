// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.leds.commands.LEDCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.vision.VisionConfig;
import java.util.function.DoubleSupplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AlignToAprilTag extends PIDCommand {

    private static double lowKP = 0.035;
    private static double highKP = 0.06;
    private static double tolerance = 2;
    SwerveDrive driveCommand;
    DoubleSupplier fwdPositiveSupplier;
    private static double out;
    private int pipelineIndex;

    /**
     * Creates a new AlignToAprilTag //TODO: change naming, not just apriltag anymore
     *
     * @param fwdPositiveSupplier
     * @param offset
     * @param pipeline which limelight pipeline to set to. Apriltag, retro, detector
     */
    public AlignToAprilTag(DoubleSupplier fwdPositiveSupplier, double offset, int pipelineIndex) {
        super(
                // The controller that the command will use
                new PIDController(lowKP, 0, 0),
                // This should return the measurement
                () -> (-Robot.vision.getHorizontalOffset()),
                // This should return the setpoint (can also be a constant)
                () -> offset,
                // This uses the output
                output -> setOutput(output),
                Robot.swerve);

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
        this.pipelineIndex = pipelineIndex;
        this.setName("AlignToAprilTag");
    }

    public double getSteering() {
        return Robot.swerve.calculateRotationController(() -> 0);
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
        // getLedCommand(tagID).initialize();
        Robot.swerve.resetRotationController();
        driveCommand.initialize();
        if (Robot.vision.getVerticalOffset() > 16) {
            this.getController().setP(highKP);
        } else {
            this.getController().setP(lowKP);
        }

        Robot.vision.setLimelightPipeline(pipelineIndex);
    }

    @Override
    public void execute() {
        super.execute();
        driveCommand.execute();
        // getLedCommand(tagID).execute();
    }

    @Override
    public void end(boolean interrupted) {
        // getLedCommand(tagID).end(interrupted);
        Robot.vision.setLimelightPipeline(VisionConfig.aprilTagPipeline);
        driveCommand.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    private Command getLedCommand() {
        double tagID = Robot.vision.getClosestTagID();
        switch ((int) tagID) {
            case 1:
            case 6:
                return LEDCommands.leftGrid();
            case 2:
            case 7:
                return LEDCommands.midGrid();
            case 3:
            case 8:
                return LEDCommands.rightGrid();
        }
        return new OneColorLEDCommand((2 / 3), 1, new Color(130, 103, 185), "Full Wrong", 80);
    }
}
