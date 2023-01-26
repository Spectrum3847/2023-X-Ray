package frc.robot.leds.commands;

import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class SnowfallLEDCommand extends LEDCommandBase {
    /** Creates a new SnowfallLEDCommand. */
    private final LEDs ledSubsystem;

    long waitTime;
    long startTime;
    int stage;

    public SnowfallLEDCommand(String name, int priority, int timeout) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.waitTime = 100;
        this.startTime = System.currentTimeMillis();
        stage = 0;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            if (i % 4 == stage) {
                ledSubsystem.setRGB(i, 255, 255, 255);
                continue;
            }
            ledSubsystem.setRGB(i, 20, 120, 255);
        }
        ledSubsystem.sendData();
        stage++;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void ledExecute() {
        if (System.currentTimeMillis() - startTime >= waitTime) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                if (i % 4 == stage) {
                    ledSubsystem.setRGB(i, 255, 255, 255);
                    continue;
                }
                ledSubsystem.setRGB(i, 20, 120, 255);
            }
            ledSubsystem.sendData();
            stage = stage + 1 > 3 ? 0 : stage + 1;
            startTime = System.currentTimeMillis();
        }
    }
}
