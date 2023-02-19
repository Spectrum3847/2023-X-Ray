package frc.robot.pilot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.AxisButton.ThresholdType;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.Robot;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.commands.PilotCommands;
import frc.robot.pose.commands.PoseCommands;
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.trajectories.commands.PositionPaths;

/** Used to add buttons to the pilot gamepad and configure the joysticks */
public class PilotGamepad extends Gamepad {

    public PilotGamepad() {
        super("PILOT", PilotConfig.port);
        gamepad.leftStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.leftStick.configCurves(
                PilotConfig.throttleExp,
                PilotConfig.throttleScaler * Robot.swerve.config.tuning.maxVelocity);
        gamepad.leftStick.setXinvert(PilotConfig.xInvert);
        gamepad.leftStick.setYinvert(PilotConfig.yInvert);

        gamepad.rightStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.rightStick.configCurves(
                PilotConfig.steeringExp,
                PilotConfig.steeringScaler * Robot.swerve.config.tuning.maxAngularVelocity);
        gamepad.rightStick.setXinvert(PilotConfig.xInvert);
        gamepad.rightStick.setYinvert(PilotConfig.yInvert);

        gamepad.triggers.setTwistDeadband(PilotConfig.steeringDeadband);
        gamepad.triggers.configTwistCurve(
                PilotConfig.steeringExp,
                PilotConfig.steeringScaler * Robot.swerve.config.tuning.maxAngularVelocity);
        gamepad.triggers.setTwistInvert(PilotConfig.steeringInvert);
    }

    public void setupTeleopButtons() {
        gamepad.xButton.whileTrue(new LockSwerve()).and(noBumpers());
        gamepad.yButton.whileTrue(IntakeCommands.launch()).and(noBumpers());
        gamepad.aButton.whileTrue(IntakeCommands.eject()).and(noBumpers());
        gamepad.bButton.whileTrue(OperatorCommands.homeAndSlowIntake()).and(noBumpers());
        // gamepad.rightBumper.whileTrue(
        //         ElevatorCommands.setOutput(() -> gamepad.rightStick.getY() * 0.5));
        // gamepad.leftBumper.whileTrue(
        //         FourBarCommands.setManualOutput(() -> gamepad.rightStick.getY() * 0.5));

        leftGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid1Left());
        leftGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid1Middle());
        leftGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid1Right());
        middleGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid2Left());
        middleGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid2Middle());
        middleGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid2Right());
        rightGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid3Left());
        rightGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid3Middle());
        rightGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid3Right());

        // Stick steer when the right stick is moved passed 0.5 and bumpers aren't pressed
        stickSteerTriggers();

        // Reorient the robot to the current heading
        gamepad.Dpad.Up.whileTrue(
                        PoseCommands.resetHeading(0).alongWith(PilotCommands.rumble(0.5, 1)))
                .and(noBumpers());
        gamepad.Dpad.Left.whileTrue(
                        PoseCommands.resetHeading(90).alongWith(PilotCommands.rumble(0.5, 1)))
                .and(noBumpers());
        gamepad.Dpad.Down.whileTrue(
                        PoseCommands.resetHeading(180).alongWith(PilotCommands.rumble(0.5, 1)))
                .and(noBumpers());
        gamepad.Dpad.Right.whileTrue(
                        PoseCommands.resetHeading(270).alongWith(PilotCommands.rumble(0.5, 1)))
                .and(noBumpers());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kWhite, "White", 5, 3));
    }

    public void setupTestButtons() {}

    private Trigger leftGrid() {
        return gamepad.leftBumper.and(gamepad.rightBumper.negate());
    }

    private Trigger rightGrid() {
        return gamepad.rightBumper.and(gamepad.leftBumper.negate());
    }

    private Trigger middleGrid() {
        return gamepad.rightBumper.and(gamepad.leftBumper);
    }

    public double getDriveFwdPositive() {
        double fwdPositive = gamepad.leftStick.getY();
        return fwdPositive;
    }

    public double getDriveLeftPositive() {
        double leftPositive = gamepad.leftStick.getX();
        return leftPositive;
    }

    // Positive is counter-clockwise, left Trigger is positive
    public double getDriveCCWPositive() {
        double ccwPositive = gamepad.triggers.getTwist();
        return ccwPositive;
    }

    // Return the angle created by the left stick in radians, 0 is up, pi/2 is left
    public Double getDriveAngle() {
        return gamepad.leftStick.getDirectionRadians(getDriveFwdPositive(), getDriveLeftPositive());
    }

    // Return the angle created by the right stick in radians, 0 is up, pi/2 is left
    public double getRightStickAngle() {
        return gamepad.rightStick.getDirectionRadians(
                gamepad.rightStick.getY(), gamepad.rightStick.getX());
    }

    private void stickSteerTriggers() {
        // Right Stick points the robot in that direction, when bumpers aren't pressed
        Trigger rightX = AxisButton.create(gamepad, XboxAxis.RIGHT_X, 0.5, ThresholdType.DEADBAND);
        Trigger rightY = AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.5, ThresholdType.DEADBAND);
        (rightX.or(rightY)).and(noBumpers()).whileTrue(PilotCommands.stickSteer());
    }

    private Trigger noBumpers() {
        return gamepad.rightBumper.negate().and(gamepad.rightBumper.negate());
    }

    public double getLeftYRaw() {
        return gamepad.getRawAxis(1) * -1;
    }

    public double getLeftXRaw() {
        return gamepad.getRawAxis(0) * -1;
    }

    public void rumble(double intensity) {
        this.gamepad.setRumble(intensity, intensity);
    }
}
