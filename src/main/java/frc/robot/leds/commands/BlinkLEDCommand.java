package frc.robot.leds.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.SpectrumLib.util.Util;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class BlinkLEDCommand extends LEDCommandBase {
    LEDs ledSubsystem;
    long startTime;
    int waitTime; // Wait time in milliseconds
    int r, g, b;
    boolean on = true;

    public BlinkLEDCommand(
            int waitTimeMS, int r, int g, int b, String name, int priority, int timeout) {
        super(name, priority, timeout);
        this.ledSubsystem = Robot.leds;
        this.startTime = (long) Units.secondsToMilliseconds(Util.getTime());
        this.waitTime = waitTimeMS;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public BlinkLEDCommand(int waitTime, Color color, String name, int priority, int timeout) {
        this(
                waitTime,
                new Color8Bit(color).red,
                new Color8Bit(color).green,
                new Color8Bit(color).blue,
                name,
                priority,
                timeout);
    }

    public BlinkLEDCommand(Color color, String name, int priority, int timeout) {
        this(500, color, name, priority, timeout);
    }

    private long getTime() {
        return (long) Units.secondsToMilliseconds(Util.getTime());
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        startTime = getTime();
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void ledExecute() {
        if (getTime() - startTime >= waitTime) {
            if (on) {
                for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }
                ledSubsystem.sendData();
                on = false;
            } else {
                for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                    ledSubsystem.setRGB(i, r, g, b);
                }
                ledSubsystem.sendData();
                on = true;
            }
            startTime = getTime();
        }
    }
}
