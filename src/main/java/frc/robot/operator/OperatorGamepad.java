package frc.robot.operator;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.CountdownLEDCommand;
import frc.robot.leds.commands.LEDCommands;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.pilot.commands.PilotCommands;

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

    public void setupTeleopButtons() {

        /* Intaking */
        gamepad.leftBumper.whileTrue(OperatorCommands.homeAndSlowIntake());
        gamepad.rightTriggerButton.and(rightBumper()).whileTrue(OperatorCommands.coneIntake());
        gamepad.leftTriggerButton.and(noRightBumper()).whileTrue(OperatorCommands.cubeIntake());
        gamepad.leftTriggerButton
                .and(rightBumper())
                .whileTrue(OperatorCommands.coneStandingIntake());
        gamepad.rightTriggerButton
                .and(noRightBumper())
                .whileTrue(OperatorCommands.coneShelfIntake());
        gamepad.yButton.and(rightBumper()).whileTrue(OperatorCommands.airIntake());

        /* Cube Scoring */
        gamepad.aButton
                .and(rightBumper())
                .whileTrue(OperatorCommands.cubeFloorGoal().alongWith(PilotCommands.rumble(1, 99)));
        gamepad.bButton
                .and(rightBumper())
                .whileTrue(
                        OperatorCommands.cubeChargeStation()
                                .alongWith(PilotCommands.rumble(1, 99)));
        gamepad.aButton
                .and(noRightBumper())
                .whileTrue(OperatorCommands.cubeMid().alongWith(PilotCommands.rumble(1, 99)));
        gamepad.bButton
                .and(noRightBumper())
                .whileTrue(
                        OperatorCommands.cubeTop()
                                .alongWith(
                                        PilotCommands.conditionalRumble(
                                                Elevator.config.cubeTop, 1, 99)));

        /* Cone Scoring */
        gamepad.xButton.and(rightBumper()).whileTrue(OperatorCommands.coneFloorGoal());
        gamepad.xButton.and(noRightBumper()).whileTrue(OperatorCommands.coneMid());
        gamepad.yButton.and(noRightBumper()).whileTrue(OperatorCommands.coneTop());

        /* Miscellaneous */
        gamepad.Dpad.Up.and(noRightBumper()).whileTrue(IntakeCommands.intake());
        gamepad.Dpad.Down.and(noRightBumper()).whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.and(noRightBumper()).whileTrue(LEDCommands.coneFloorLED());
        gamepad.Dpad.Right.and(noRightBumper()).whileTrue(LEDCommands.cubeLED());
        gamepad.selectButton.whileTrue(ElevatorCommands.zeroElevatorRoutine());
        gamepad.startButton.whileTrue(FourBarCommands.zeroFourBarRoutine());
        gamepad.selectButton.and(gamepad.startButton).onTrue(OperatorCommands.cancelCommands());

        AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.1)
                .and(noRightBumper())
                .whileTrue(OperatorCommands.manualFourBar());
        AxisButton.create(gamepad, XboxAxis.LEFT_Y, 0.1)
                .and(noRightBumper())
                .whileTrue(OperatorCommands.manualElevator());

        AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.1)
                .and(rightBumper())
                .whileTrue(OperatorCommands.slowManualFourBar());
        AxisButton.create(gamepad, XboxAxis.LEFT_Y, 0.1)
                .and(rightBumper())
                .whileTrue(OperatorCommands.slowManualElevator());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(LEDCommands.coneFloorLED());
        gamepad.yButton.whileTrue(LEDCommands.cubeLED());
        gamepad.bButton
                .and(bothTriggers())
                .and(bothBumpers())
                .whileTrue(new CountdownLEDCommand("Manual Countdown", 120, 10, true));
        gamepad.bButton.toggleOnTrue(OperatorCommands.coastMode());
    }

    public void setupTestButtons() {}

    private Trigger noRightBumper() {
        return gamepad.rightBumper.negate();
    }

    private Trigger rightBumper() {
        return gamepad.rightBumper;
    }

    private Trigger bothBumpers() {
        return gamepad.rightBumper.and(gamepad.leftBumper);
    }

    private Trigger bothTriggers() {
        return gamepad.rightTriggerButton.and(gamepad.leftTriggerButton);
    }

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
