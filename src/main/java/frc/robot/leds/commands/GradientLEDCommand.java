package frc.robot.leds.commands;

import edu.wpi.first.math.util.Units;
import frc.SpectrumLib.util.Util;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class GradientLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;
    private final double colorFrequency, stripFrequency; // ms
    private int r = 255;
    private int g = 255;
    private int b = 0;
    private int ledStart, ledEnd;
    private int countdown; // seconds
    private long colorTime, stripTime; // ms
    private double startTime; // s

    /**
     * Create an LED sequence that starts with a single color and slowly turns into a gradient
     *
     * @param name
     * @param priority
     * @param countdown seconds
     */
    public GradientLEDCommand(String name, int priority, int countdown) {
        super(name, priority, -101);
        ledSubsystem = Robot.leds;
        this.countdown = countdown;
        this.colorFrequency = Units.secondsToMilliseconds(countdown) / 255;
        this.stripFrequency =
                Units.secondsToMilliseconds(countdown) / ledSubsystem.getBufferLength();
        this.ledStart = 0;
        this.ledEnd = ledSubsystem.getBufferLength();
    }

    @Override
    public void ledInitialize() {
        g = 255;
        startTime = Util.getTime();
        colorTime = getMS();
        stripTime = getMS();
    }

    private long getMS() {
        return (long) Units.secondsToMilliseconds(Util.getTime());
    }

    @Override
    public void ledExecute() {
        if (getMS() - colorTime >= colorFrequency) {
            g--;
            colorTime = getMS();
        }

        if (getMS() - stripTime >= stripFrequency) {
            ledStart++;
            stripTime = getMS();
        }

        for (int i = ledStart; i < ledEnd; i++) {
            ledSubsystem.setRGB(i, r, g, b);
        }
        ledSubsystem.sendData();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        startTime = 0;
        ledStart = 0;
    }

    @Override
    public boolean isFinished() {
        return (startTime != 0 && Util.getTime() >= startTime + countdown);
    }
}
