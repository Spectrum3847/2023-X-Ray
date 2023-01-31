package frc.robot.operator;

import edu.wpi.first.wpilibj.util.Color;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.robot.elevator.ElevatorCommands;
import frc.robot.fourbar.FourBarCommands;
import frc.robot.intake.IntakeCommands;
import frc.robot.leds.commands.BlinkLEDCommand;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.leds.commands.RainbowLEDCommand;
import frc.robot.leds.commands.SnowfallLEDCommand;

/** Used to add buttons to the operator gamepad and configure the joysticks */
public class OperatorGamepad extends Gamepad {

    public OperatorGamepad() {
        super("operator", OperatorConfig.port);
        gamepad.leftStick.setXinvert(OperatorConfig.xInvert);
        gamepad.leftStick.setYinvert(OperatorConfig.yInvert);

        gamepad.rightStick.setXinvert(OperatorConfig.xInvert);
        gamepad.rightStick.setYinvert(OperatorConfig.yInvert);
    }

    public void setupTeleopButtons() {
        // gamepad.aButton.whileTrue(operatorCommands.aimoperatorDrive(Math.PI * 1 /
        // 2).withName("Snap
        // 90"));
        // gamepad.bButton.whileTrue(operatorCommands.fpvoperatorSwerve());
        /*gamepad.bButton.whileTrue(
        operatorCommands.aimoperatorDrive(() -> Robot.vision.getRadiansToTarget())
                .withName("Aim to target"));*/
        // gamepad.xButton.whileTrue(new LockSwerve());
        /* get information about target and robot yaw */
        // gamepad.xButton.whileTrue(VisionCommands.printYawInfo());
        // gamepad.yButton.whileTrue(new SpinMove());
        // gamepad.yButton.whileTrue(VisionCommands.printEstimatedPoseInfo());
        gamepad.aButton.whileTrue(IntakeCommands.intake());
        gamepad.xButton.whileTrue(IntakeCommands.launch());
        gamepad.yButton.whileTrue(IntakeCommands.eject());
        gamepad.rightBumper.whileTrue(
                ElevatorCommands.setOutput(() -> gamepad.rightStick.getY() * 0.5));
        gamepad.leftBumper.whileTrue(
                FourBarCommands.setManualOutput(() -> gamepad.rightStick.getY() * 0.1));

        // Right Stick points the robot in that direction
        // Trigger rightX = AxisButton.create(gamepad, XboxAxis.RIGHT_X, 0.5,
        // ThresholdType.DEADBAND);
        // Trigger rightY = AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.5,
        // ThresholdType.DEADBAND);
        // rightX.or(rightY).whileTrue(operatorCommands.stickSteer());

        // Reorient the robot to the current heading

    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kGreen, "Green", 5, 3));
        gamepad.bButton.whileTrue(new BlinkLEDCommand(Color.kPink, "Blink Pink", 10, 3));
        gamepad.xButton.whileTrue(new RainbowLEDCommand("rainbow", 15, 3));
        gamepad.yButton.whileTrue(new SnowfallLEDCommand("Snowfall", 20, 3));
    }

    public void setupTestButtons() {}

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

    public void rumble(double intensity) {
        this.gamepad.setRumble(intensity, intensity);
    }
}
