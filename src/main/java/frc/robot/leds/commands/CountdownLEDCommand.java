package frc.robot.leds.commands;

import edu.wpi.first.math.util.Units;
import frc.SpectrumLib.util.Util;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class CountdownLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;
    private final double colorFrequency, stripFrequency; // ms
    private int r = 255;
    private int g = 255;
    private int b = 0;
    private int ledStart, ledEnd;
    private int countdown; // seconds
    private long colorTime, stripTime; // ms
    private double startTime; // s
    private final int lastIndex;
    private boolean sendFirework;

    /**
     * Create an LED sequence that counts down with the leds with a given timeout
     *
     * @param name
     * @param priority
     * @param countdown seconds
     */
    public CountdownLEDCommand(String name, int priority, int countdown, boolean firework) {
        super(name, priority, -101);
        ledSubsystem = Robot.leds;
        this.countdown = countdown;
        this.colorFrequency = Units.secondsToMilliseconds(countdown) / 255;
        this.stripFrequency =
                Units.secondsToMilliseconds(countdown) / (ledSubsystem.getBufferLength() + 3);
        this.lastIndex = ledSubsystem.getBufferLength() - 1;
        this.ledStart = lastIndex;
        this.ledEnd = 0;
        this.sendFirework = firework;
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
        // decrement g to reach full red by end of countdown
        if (getMS() - colorTime >= colorFrequency) {
            g--;
            colorTime = getMS();
        }

        if (getMS() - stripTime >= stripFrequency) {
            ledStart--;
            stripTime = getMS();
        }

        for (int i = ledSubsystem.getBufferLength() - 1; i >= 0; i--) {
            if (i <= ledStart && i >= ledEnd) {
                ledSubsystem.setRGB(i, r, g, b);
            } else {
                ledSubsystem.setRGB(i, 0, 0, 0);
            }
        }

        ledSubsystem.sendData();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        startTime = 0;
        ledStart = lastIndex;
        if (!interrupted && sendFirework) {
            new FireworkLEDCommand("Firework", priority).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return (startTime != 0 && Util.getTime() >= startTime + countdown);
    }
}
