package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class CountdownLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;
    private final int r;
    private final int g;
    private final int b;
    private int ledStart, ledEnd;

    public CountdownLEDCommand(int r, int g, int b, String name, int priority, double timeout) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.r = r;
        this.g = g;
        this.b = b;
        this.ledStart = 0;
        this.ledEnd = ledSubsystem.getBufferLength();
    }

    public CountdownLEDCommand(Color color, String name, int priority, double timeout) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout);
    }

    @Override
    public void ledInitialize() {}

    @Override
    public void ledExecute() {
        for (int i = ledStart; i < ledEnd; i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }
}
