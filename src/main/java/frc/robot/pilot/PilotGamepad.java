package frc.robot.pilot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.AxisButton.ThresholdType;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.commands.PilotCommands;
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.swerve.commands.SwerveCommands;
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
        fpvButton().and(noBumpers()).whileTrue(PilotCommands.fpvPilotSwerve()); // X and Not A
        slowModeButton().and(noBumpers()).whileTrue(PilotCommands.slowMode()); // A and not X
        slowFpvButton().and(noBumpers()).whileTrue(PilotCommands.slowModeFPV()); // A and X

        // gamepad.yButton.and(noBumpers()).whileTrue(); Y IS FREE

        gamepad.bButton.and(noBumpers()).whileTrue(OperatorCommands.homeAndSlowIntake());

        gamepad.rightBumper.whileTrue(
                ElevatorCommands.setOutput(() -> gamepad.rightStick.getY() * 0.5));
        gamepad.leftBumper.whileTrue(
                FourBarCommands.setManualOutput(() -> gamepad.rightStick.getY() * 0.5));

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

        gamepad.Dpad.Up.and(noBumpers()).whileTrue(IntakeCommands.launch());
        gamepad.Dpad.Down.and(noBumpers()).whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.and(noBumpers()).whileTrue(new LockSwerve());
        // Right is free

        // Reorient the robot to the current heading, reset swerve ot absolute sensors, and rumble
        // controller
        gamepad.Dpad.Up.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(0));
        gamepad.Dpad.Left.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(90));
        gamepad.Dpad.Down.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(180));
        gamepad.Dpad.Right.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(270));

        // Start and Select Buttons
        gamepad.startButton.whileTrue(
                SwerveCommands
                        .resetSteeringToAbsolute()); // Reset steering if a falcon is being weird
        // gamepad.selectButton.whileTrue():
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kWhite, "White", 5, 3));
    }

    public void setupTestButtons() {}

    private Trigger noBumpers() {
        return gamepad.rightBumper.negate().and(gamepad.leftBumper.negate());
    }

    private Trigger leftBumperOnly() {
        return gamepad.leftBumper.and(gamepad.rightBumper.negate());
    }

    private Trigger rightBumperOnly() {
        return gamepad.rightBumper.and(gamepad.leftBumper.negate());
    }

    private Trigger bothBumpers() {
        return gamepad.rightBumper.and(gamepad.leftBumper);
    }

    private Trigger leftGrid() {
        return leftBumperOnly();
    }

    private Trigger rightGrid() {
        return rightBumperOnly();
    }

    private Trigger middleGrid() {
        return bothBumpers();
    }

    private Trigger slowModeButton() {
        return gamepad.aButton.and(gamepad.xButton.negate());
    }

    private Trigger fpvButton() {
        return gamepad.xButton.and(gamepad.aButton.negate());
    }

    private Trigger slowFpvButton() {
        return gamepad.xButton.and(gamepad.aButton);
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
