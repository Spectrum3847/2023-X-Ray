package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class OneColorLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;
    private final int r, g, b;

    public OneColorLEDCommand(int r, int g, int b, String name, int priority, int timeout) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public OneColorLEDCommand(Color color, String name, int priority, int timeout) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout);
    }

    @Override
    public void ledInitialize() {
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }

    @Override
    public void ledExecute() {}
}
