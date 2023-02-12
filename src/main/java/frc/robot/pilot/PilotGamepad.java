package frc.robot.pilot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.IntakeCommands;
import frc.robot.leds.commands.BlinkLEDCommand;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.leds.commands.RainbowLEDCommand;
import frc.robot.leds.commands.SnowfallLEDCommand;
import frc.robot.pilot.commands.PilotCommands;
import frc.robot.pose.commands.PoseCommands;

/** Used to add buttons to the pilot gamepad and configure the joysticks */
public class PilotGamepad extends Gamepad {

    public PilotGamepad() {
        super("PILOT", PilotConfig.port);
        gamepad.leftStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.leftStick.configCurves(
                PilotConfig.throttleExp,
                Robot.swerve.config.tuning.maxVelocity * PilotConfig.throttleScaler);
        gamepad.leftStick.setXinvert(PilotConfig.xInvert);
        gamepad.leftStick.setYinvert(PilotConfig.yInvert);

        gamepad.rightStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.rightStick.configCurves(
                PilotConfig.steeringExp,
                Robot.swerve.config.tuning.maxAngularVelocity * PilotConfig.steeringScaler);
        gamepad.rightStick.setXinvert(PilotConfig.xInvert);
        gamepad.rightStick.setYinvert(PilotConfig.yInvert);

        gamepad.triggers.setTwistDeadband(PilotConfig.steeringDeadband);
        gamepad.triggers.configTwistCurve(
                PilotConfig.steeringExp,
                Robot.swerve.config.tuning.maxAngularVelocity * PilotConfig.steeringScaler);
        gamepad.triggers.setTwistInvert(PilotConfig.steeringInvert);
    }

    public void setupTeleopButtons() {
        // gamepad.aButton.whileTrue(PilotCommands.aimPilotDrive(Math.PI * 1 / 2).withName("Snap
        // 90"));
        // gamepad.bButton.whileTrue(PilotCommands.fpvPilotSwerve());
        /*gamepad.bButton.whileTrue(
        PilotCommands.aimPilotDrive(() -> Robot.vision.getRadiansToTarget())
                .withName("Aim to target"));*/
        // gamepad.xButton.whileTrue(new LockSwerve());
        /* get information about target and robot yaw */
        // gamepad.xButton.whileTrue(VisionCommands.printYawInfo());
        // gamepad.yButton.whileTrue(new SpinMove());
        // gamepad.yButton.whileTrue(VisionCommands.printEstimatedPoseInfo());
        gamepad.aButton.whileTrue(IntakeCommands.intake());
        gamepad.xButton.whileTrue(IntakeCommands.launch());
        gamepad.yButton.whileTrue(IntakeCommands.eject());
        gamepad.bButton.whileTrue(ElevatorCommands.setMMPosition(30000));
        gamepad.rightBumper.whileTrue(
                ElevatorCommands.setOutput(() -> gamepad.rightStick.getY() * 0.5));
        gamepad.leftBumper.whileTrue(
                FourBarCommands.setManualOutput(() -> gamepad.rightStick.getY() * 0.1));

        // Right Stick points the robot in that direction
        // Trigger rightX = AxisButton.create(gamepad, XboxAxis.RIGHT_X, 0.5,
        // ThresholdType.DEADBAND);
        // Trigger rightY = AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.5,
        // ThresholdType.DEADBAND);
        // rightX.or(rightY).whileTrue(PilotCommands.stickSteer());

        // Reorient the robot to the current heading
        gamepad.Dpad.Up.whileTrue(
                PoseCommands.resetHeading(0).alongWith(PilotCommands.rumble(0.5, 1)));
        gamepad.Dpad.Left.whileTrue(
                PoseCommands.resetHeading(90).alongWith(PilotCommands.rumble(0.5, 1)));
        gamepad.Dpad.Down.whileTrue(
                PoseCommands.resetHeading(180).alongWith(PilotCommands.rumble(0.5, 1)));
        gamepad.Dpad.Right.whileTrue(
                PoseCommands.resetHeading(270).alongWith(PilotCommands.rumble(0.5, 1)));
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kGreen, "Green", 5, 3));
        gamepad.bButton.whileTrue(new BlinkLEDCommand(Color.kPink, "Blink Pink", 10, 3));
        gamepad.xButton.whileTrue(new RainbowLEDCommand("rainbow", 15, 3));
        gamepad.yButton.whileTrue(new SnowfallLEDCommand("Snowfall", 20, 3));
    }

    public void setupTestButtons() {}

    private Trigger leftGrid() {
        return gamepad.leftBumper.and(gamepad.rightBumper.negate());
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
