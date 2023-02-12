package frc.robot.operator;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.Robot;
import frc.robot.RobotTelemetry;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.elevator.commands.ZeroElevator;
import frc.robot.fourbar.commands.ZeroFourBar;
import frc.robot.intakeLauncher.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.leds.commands.RainbowLEDCommand;
import frc.robot.leds.commands.SnowfallLEDCommand;
import frc.robot.operator.commands.OperatorCommands;

/** Used to add buttons to the operator gamepad and configure the joysticks */
public class OperatorGamepad extends Gamepad {

    OperatorConfig config;

    public OperatorGamepad() {
        super("operator", OperatorConfig.port);
        config = new OperatorConfig();
        gamepad.leftStick.setXinvert(OperatorConfig.xInvert);
        gamepad.leftStick.setYinvert(OperatorConfig.yInvert);

        gamepad.rightStick.setXinvert(OperatorConfig.xInvert);
        gamepad.rightStick.setYinvert(OperatorConfig.yInvert);
    }
    // set up jiggle sometime
    public void setupTeleopButtons() {
        // gamepad.yButton.whileTrue(VisionCommands.printEstimatedPoseInfo());
        gamepad.aButton.whileTrue(IntakeCommands.intake());
        gamepad.xButton.whileTrue(IntakeCommands.launch());
        gamepad.yButton.whileTrue(IntakeCommands.eject());
        gamepad.bButton.whileTrue(ElevatorCommands.setMMPosition(80000));
        gamepad.rightTriggerButton.whileTrue(IntakeCommands.spinUpLauncher());

        gamepad.leftTriggerButton.whileTrue(OperatorCommands.coneIntake());
        gamepad.Dpad.Up.whileTrue(OperatorCommands.coneMid());
        gamepad.Dpad.Left.whileTrue(OperatorCommands.coneTop());
        // gamepad.Dpad.Left.whileTrue(
        //         ElevatorCommands.setMMPosition(50000).alongWith(new FourBarDelay(100, 0,
        // 80000)));
        // gamepad.Dpad.Left.whileTrue(
        // FourBarCommands.setMMPercent(100).alongWith(new ElevatorDelay(20000, 80000, 90)));
        // gamepad.Dpad.Right.whileTrue(OperatorCommands.cubeIntake());

        gamepad.Dpad.Down.whileTrue((new ZeroElevator()));
        gamepad.Dpad.Right.whileTrue((new ZeroFourBar()));
        AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.1)
                .whileTrue(OperatorCommands.manualFourBar());
        AxisButton.create(gamepad, XboxAxis.LEFT_Y, 0.1)
                .whileTrue(OperatorCommands.manualElevator());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kGreen, "Green", 5, 3));
        // gamepad.bButton.whileTrue(new BlinkLEDCommand(Color.kPink, "Blink Pink", 10, 3));
        gamepad.bButton.whileTrue(
                new InstantCommand(() -> RobotTelemetry.print("" + Robot.elevator.getPosition())));
        gamepad.xButton.whileTrue(new RainbowLEDCommand("rainbow", 15, 3));
        gamepad.yButton.whileTrue(new SnowfallLEDCommand("Snowfall", 20, 3));
    }

    public void setupTestButtons() {}

    public void rumble(double intensity) {
        this.gamepad.setRumble(intensity, intensity);
    }

    public double elevatorManual() {
        return gamepad.leftStick.getY() * OperatorConfig.elevatorModifer;
    }

    public double fourBarManual() {
        return gamepad.rightStick.getY() * OperatorConfig.fourBarModifer;
    }
}
