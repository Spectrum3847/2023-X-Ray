package frc.robot.operator;

import edu.wpi.first.wpilibj.util.Color;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.robot.elevator.ElevatorCommands;
import frc.robot.fourbar.FourBarCommands;
import frc.robot.intakeLauncher.IntakeCommands;
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
    //set up jiggle sometime
    public void setupTeleopButtons() {
        // gamepad.yButton.whileTrue(VisionCommands.printEstimatedPoseInfo());
        gamepad.aButton.whileTrue(IntakeCommands.intake());
        gamepad.xButton.whileTrue(IntakeCommands.launch());
        gamepad.yButton.whileTrue(IntakeCommands.eject());
        gamepad.bButton.whileTrue(FourBarCommands.setMMPosition(37000));
        // no bumper for operator controls, bumper sets intake position instead
        // gamepad.rightBumper.whileTrue(
        
        ElevatorCommands.setOutput(() -> gamepad.rightStick.getY() * 0.5);
        // gamepad.leftBumper.whileTrue(
        FourBarCommands.setManualOutput(() -> gamepad.leftStick.getY() * 0.1);
        gamepad.rightTriggerButton.whileTrue(IntakeCommands.spinUpLauncher());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kGreen, "Green", 5, 3));
        gamepad.bButton.whileTrue(new BlinkLEDCommand(Color.kPink, "Blink Pink", 10, 3));
        gamepad.xButton.whileTrue(new RainbowLEDCommand("rainbow", 15, 3));
        gamepad.yButton.whileTrue(new SnowfallLEDCommand("Snowfall", 20, 3));
    }

    public double getDriveFwdPositive() {
        double fwdPositive = gamepad.leftStick.getY();
        return fwdPositive;
    }

    public double getRightFwdPositive() {
        double fwdPositive = gamepad.rightStick.getY();
        return fwdPositive;
    }

    public void setupTestButtons() {}

    public void rumble(double intensity) {
        this.gamepad.setRumble(intensity, intensity);
    }

    public double getRightStickAngle() {
        return gamepad.rightStick.getDirectionRadians(
                gamepad.rightStick.getY(), gamepad.rightStick.getX());
    }
}
