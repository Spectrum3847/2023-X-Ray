package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.elevator.commands.ElevatorCommands;

/** All of the commands to schedule LEDs */
public class LEDCommands {

    public static void setupDefaultCommand() {}

    public static void setupLEDTriggers() {
        ElevatorCommands.elevatorUp.whileTrue(elevatorHeightLED());
        Trigger startLEDEnd =
                new Trigger(
                        () ->
                                DriverStation.getMatchTime() <= Robot.leds.config.END_LED_START_TIME
                                        && DriverStation.getMatchTime() != -1
                                        && DriverStation.isTeleop());
        startLEDEnd.onTrue(endLEDSequence());
        // Trigger poseOverriden = new Trigger(() -> Robot.vision.poseOverriden);
        // poseOverriden.whileTrue(success());
    }

    public static Command purpleSolid(int priority, int timeout) {
        return new OneColorLEDCommand(new Color(100, 10, 255), null, priority, timeout);
    }

    public static Command success() {
        return new OneColorLEDCommand(Color.kWhite, "LED Success", 120);
    }

    public static Command failure() {
        return new OneColorLEDCommand(Color.kRed, "LED Fail", 99, 1.5);
    }

    public static Command coneFloorLED() {
        return new OneColorLEDCommand(Color.kYellow, "Yellow Floor Cone", 99);
    }

    public static Command coneShelfLED() {
        return new BlinkLEDCommand(Color.kYellow, "Yellow Shelf Cone", 99);
    }

    public static Command cubeLED() {
        return new OneColorLEDCommand(Color.kPurple, "Purple Cube", 99);
    }

    public static Command leftGrid() {
        return new OneColorLEDCommand(0, (1 / 3), new Color(130, 103, 185), "Left Grid", 80);
    }

    public static Command midGrid() {
        return new OneColorLEDCommand((1 / 3), (2 / 3), new Color(130, 103, 185), "Mid Grid", 80);
    }

    public static Command rightGrid() {
        return new OneColorLEDCommand((2 / 3), 1, new Color(130, 103, 185), "Right Grid", 80);
    }

    public static Command elevatorHeightLED() {
        return new RainbowLEDCommand("Elevator Height LED", 80);
    }

    public static Command endLEDSequence() {
        return new CountdownLEDCommand(
                "End Countdown", 120, Robot.leds.config.END_LED_START_TIME, true);
    }
}
