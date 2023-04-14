package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class OneColorLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;
    private final int r, g, b;
    private int ledStart, ledEnd;

    public OneColorLEDCommand(int r, int g, int b, String name, int priority, double timeout) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.r = r;
        this.g = g;
        this.b = b;
        this.ledStart = 0;
        this.ledEnd = ledSubsystem.getBufferLength();
    }

    public OneColorLEDCommand(Color color, String name, int priority, double timeout) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout);
    }

    public OneColorLEDCommand(Color color, String name, int priority) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                -100);
    }

    public OneColorLEDCommand(
            double ledStartPercent,
            double ledPercentLength,
            Color color,
            String name,
            int priority) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                -100);
        this.ledStart = (int) (ledSubsystem.getBufferLength() * ledStartPercent);
        this.ledEnd = (int) (ledSubsystem.getBufferLength() * ledPercentLength);
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
