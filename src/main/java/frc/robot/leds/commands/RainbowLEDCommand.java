package frc.robot.leds.commands;

import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class RainbowLEDCommand extends LEDCommandBase {
    /** Creates a new RainbowCommand. */
    private final LEDs ledSubsystem;

    private int rainbowFirstPixelHue;

    public RainbowLEDCommand(String name, int priority, int timeout) {
        super(name, priority, timeout);
        this.ledSubsystem = Robot.leds;
        this.rainbowFirstPixelHue = 20;
    }

    public void ledInitialize() {}

    // Called when the command is initially scheduled.

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void ledExecute() {
        // For every pixel
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            final int hue =
                    (rainbowFirstPixelHue + (i * 180 / ledSubsystem.getBufferLength())) % 180;
            // Set the value
            ledSubsystem.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        rainbowFirstPixelHue += 3;
        // Check bounds
        rainbowFirstPixelHue %= 180;
        ledSubsystem.sendData();
    }
}
