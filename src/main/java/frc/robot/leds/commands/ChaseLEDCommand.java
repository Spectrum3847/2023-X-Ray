package frc.robot.leds.commands;

import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class ChaseLEDCommand extends LEDCommandBase {
    /** Creates a new ChaseLEDCommand. */
    private final LEDs ledSubsystem;

    long waitTime;
    long startTime;
    int onLEDIndex;

    public ChaseLEDCommand(String name, int priority, int timeout) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.waitTime = 1;
        this.startTime = System.currentTimeMillis();
        onLEDIndex = 0;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            if (i == onLEDIndex) {
                ledSubsystem.setRGB(i, 255, 255, 255);
                continue;
            }
            ledSubsystem.setRGB(i, 0, 0, 0);
        }
        ledSubsystem.sendData();
        onLEDIndex++;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void ledExecute() {
        if (System.currentTimeMillis() - startTime >= waitTime) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                if (i == onLEDIndex) {
                    ledSubsystem.setRGB(i, 255, 255, 255);
                    continue;
                }
                if (i == onLEDIndex + 1) {
                    ledSubsystem.setRGB(i, 150, 150, 150);
                    continue;
                }
                if (i == onLEDIndex - 1) {
                    ledSubsystem.setRGB(i, 150, 150, 150);
                    continue;
                }
                if (i == onLEDIndex + 2) {
                    ledSubsystem.setRGB(i, 75, 75, 75);
                    continue;
                }
                if (i == onLEDIndex - 2) {
                    ledSubsystem.setRGB(i, 75, 75, 75);
                    continue;
                }
                ledSubsystem.setRGB(i, 0, 0, 0);
            }
            ledSubsystem.sendData();
            onLEDIndex = onLEDIndex + 1 > ledSubsystem.getBufferLength() ? 0 : onLEDIndex + 1;
            startTime = System.currentTimeMillis();
        }
    }
}
