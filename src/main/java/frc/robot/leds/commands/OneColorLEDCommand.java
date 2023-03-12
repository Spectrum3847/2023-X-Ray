package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class OneColorLEDCommand extends LEDCommandBase {
    private final int r, g, b;

    public OneColorLEDCommand(
            int r, int g, int b, String name, Priority priority, double timeout, double scope) {
        super(name, priority, timeout, scope);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public OneColorLEDCommand(
            Color color, String name, Priority priority, double timeout, double scope) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout,
                scope);
    }

    public OneColorLEDCommand(Color color, String name, Priority priority, double scope) {
        this(
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                -100,
                scope);
    }

    @Override
    public void ledInitialize() {}

    @Override
    public void ledExecute() {
        for (int i = 0; i < availableLength; i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }
}
