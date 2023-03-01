package frc.robot.operator;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.fourbar.commands.ZeroFourBarRoutine;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
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
    // set up jiggle sometime
    public void setupTeleopButtons() {
        gamepad.aButton
                .and(noRightBumper())
                .whileTrue(OperatorCommands.cubeMid().alongWith(PilotCommands.rumble(1, 99)));
        // gamepad.aButton.and(noRightBumper()).whileTrue(AutonCommands.rightStationTop());
        gamepad.aButton
                .and(rightBumper())
                .whileTrue(OperatorCommands.cubeHybrid().alongWith(PilotCommands.rumble(1, 99)));
        gamepad.bButton
                .and(noRightBumper())
                .whileTrue(OperatorCommands.cubeTop().alongWith(PilotCommands.rumble(1, 99)));
        gamepad.bButton
                .and(rightBumper())
                .whileTrue(
                        OperatorCommands.cubeChargeStation()
                                .alongWith(PilotCommands.rumble(1, 99)));
        gamepad.xButton.and(noRightBumper()).whileTrue(OperatorCommands.coneMid());
        gamepad.xButton.and(rightBumper()).whileTrue(OperatorCommands.coneHybrid());
        gamepad.yButton.and(noRightBumper()).whileTrue(OperatorCommands.coneTop());
        gamepad.rightTriggerButton.and(noRightBumper()).whileTrue(OperatorCommands.coneIntake());
        gamepad.rightTriggerButton.and(rightBumper()).whileTrue(OperatorCommands.coneShelfIntake());
        gamepad.leftTriggerButton.and(noRightBumper()).whileTrue(OperatorCommands.cubeIntake());
        gamepad.leftTriggerButton
                .and(rightBumper())
                .whileTrue(OperatorCommands.coneStandingIntake());

        gamepad.leftBumper.whileTrue(OperatorCommands.homeAndSlowIntake());
        gamepad.Dpad.Up.and(noRightBumper()).whileTrue(IntakeCommands.intake());
        gamepad.Dpad.Down.and(noRightBumper()).whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.and(noRightBumper()).whileTrue(OperatorCommands.coneFloorLED());
        gamepad.Dpad.Right.and(noRightBumper()).whileTrue(OperatorCommands.cubeLED());
        gamepad.selectButton.whileTrue(ElevatorCommands.zeroElevatorRoutine());
        gamepad.startButton.whileTrue(new ZeroFourBarRoutine());

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
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kYellow, "Yellow", 5, 3));
        gamepad.bButton.toggleOnTrue(
                ElevatorCommands.coastMode()
                        .alongWith(FourBarCommands.coastMode().ignoringDisable(true)));
    }

    public void setupTestButtons() {}

    private Trigger noRightBumper() {
        return gamepad.rightBumper.negate();
    }

    private Trigger rightBumper() {
        return gamepad.rightBumper;
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
