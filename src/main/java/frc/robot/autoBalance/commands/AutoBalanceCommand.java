package frc.robot.autoBalance.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.autoBalance.AutoBalanceConfig;

public class AutoBalanceCommand extends CommandBase {
    private double balanaceEffort; // The effort the robot should use to balance
    private double turningEffort; // The effort the robot should use to turn

    /*Creates a new GeneratePath.
     * * @param firstPoints*/
    public AutoBalanceCommand() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.swerve);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        turningEffort =
                Robot.trajectories
                        .calculateThetaSupplier(() -> AutoBalanceConfig.angleSetPoint)
                        .getAsDouble();
        balanaceEffort =
                (AutoBalanceConfig.balancedAngle - Robot.swerve.gyro.getRawPitch().getDegrees())
                        * AutoBalanceConfig.kP;
        Robot.swerve.drive(balanaceEffort, 0, turningEffort, false, true);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.swerve.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(Robot.swerve.gyro.getRawPitch().getDegrees()) < 2;
    }
}
