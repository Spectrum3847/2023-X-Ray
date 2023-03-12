package frc.robot.leds.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.SpectrumLib.util.Util;

public class BlinkLEDCommand extends LEDCommandBase {
    long startTime;
    int waitTime; // Wait time in milliseconds
    int r, g, b;
    boolean on = true;

    public BlinkLEDCommand(
            int waitTimeMS,
            int r,
            int g,
            int b,
            String name,
            Priority priority,
            double timeout,
            double scope) {
        super(name, priority, timeout, scope);
        this.startTime = (long) Units.secondsToMilliseconds(Util.getTime());
        this.waitTime = waitTimeMS;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public BlinkLEDCommand(
            int waitTime,
            Color color,
            String name,
            Priority priority,
            double timeout,
            double scope) {
        this(
                waitTime,
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout,
                scope);
    }

    public BlinkLEDCommand(
            Color color, String name, Priority priority, double timeout, double scope) {
        this(500, color, name, priority, timeout, scope);
    }

    public BlinkLEDCommand(Color color, String name, Priority priority, double scope) {
        this(500, color, name, priority, -101, scope);
    }

    private long getTime() {
        return (long) Units.secondsToMilliseconds(Util.getTime());
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        startTime = getTime();
        for (int i = 0; i < availableLength; i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void ledExecute() {
        if (getTime() - startTime >= waitTime) {
            if (on) {
                for (int i = 0; i < availableLength; i++) {
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }
                ledSubsystem.sendData();
                on = false;
            } else {
                for (int i = 0; i < availableLength; i++) {
                    ledSubsystem.setRGB(i, r, g, b);
                }
                ledSubsystem.sendData();
                on = true;
            }
            startTime = getTime();
        }
    }
}
