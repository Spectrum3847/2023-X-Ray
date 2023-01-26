package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

/** All of the commands to schedule LEDs */
public class LEDCommands {
    public static void setupDefaultCommand() {}

    public static Command purpleSolid(int priority, int timeout) {
        return new OneColorLEDCommand(new Color(100, 10, 255), null, priority, timeout);
    }
}
