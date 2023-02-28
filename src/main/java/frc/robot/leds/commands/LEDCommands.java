package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

/** All of the commands to schedule LEDs */
public class LEDCommands {
    public static void setupDefaultCommand() {}

    public static Command purpleSolid(int priority, int timeout) {
        return new OneColorLEDCommand(new Color(100, 10, 255), null, priority, timeout);
    }

    public static Command success() {
        return new OneColorLEDCommand(Color.kGreen, "LED Success", 99, 1.5);
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

    public static Command elevatorHeightLED() {
        return new RainbowLEDCommand("Elevator Height LED", 99);
    }
}
