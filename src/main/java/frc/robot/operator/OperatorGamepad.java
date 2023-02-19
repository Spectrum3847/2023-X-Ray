package frc.robot.operator;

import edu.wpi.first.wpilibj.util.Color;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.ZeroFourBarRoutine;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
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
        gamepad.aButton.whileTrue(OperatorCommands.cubeMid());
        gamepad.bButton.whileTrue(OperatorCommands.cubeTop());
        gamepad.xButton.whileTrue(OperatorCommands.coneMid());
        gamepad.yButton.whileTrue(OperatorCommands.coneTop());
        gamepad.rightTriggerButton.whileTrue(OperatorCommands.coneIntake());
        gamepad.leftTriggerButton.whileTrue(OperatorCommands.cubeIntake());
        gamepad.rightBumper.whileTrue(OperatorCommands.coneShelfIntake());
        gamepad.leftBumper.whileTrue(OperatorCommands.homeAndSlowIntake());
        gamepad.Dpad.Up.whileTrue(IntakeCommands.intake());
        gamepad.Dpad.Down.whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.whileTrue(OperatorCommands.coneFloorLED());
        gamepad.Dpad.Right.whileTrue(OperatorCommands.cubeLED());
        gamepad.selectButton.whileTrue(ElevatorCommands.zeroElevatorRoutine());
        gamepad.startButton.whileTrue(new ZeroFourBarRoutine());

        AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.1)
                .whileTrue(OperatorCommands.manualFourBar());
        AxisButton.create(gamepad, XboxAxis.LEFT_Y, 0.1)
                .whileTrue(OperatorCommands.manualElevator());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kYellow, "Yellow", 5, 3));
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
