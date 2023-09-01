package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class PieceLEDCommand extends LEDCommandBase {
    /** Creates a new PieceLEDCommand. */
    private final LEDs ledSubsystem;

    double waitTime;
    long startTime;
    int onLEDIndex;
    boolean backwards = false;
    int r, g, b;

    public PieceLEDCommand(String name, int priority, double timeout, int r, int g, int b) {
        super(name, priority, timeout);
        ledSubsystem = Robot.leds;
        this.waitTime = 0;
        this.startTime = System.currentTimeMillis();
        this.r = r;
        this.g = g;
        this.b = b;
        onLEDIndex = 0;
    }

    public PieceLEDCommand(String name, int priority, int r, int g, int b) {
        super(name, priority);
        ledSubsystem = Robot.leds;
        this.waitTime = 0;
        this.startTime = System.currentTimeMillis();
        this.r = r;
        this.g = g;
        this.b = b;
        onLEDIndex = 0;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            if (i == onLEDIndex) {
                ledSubsystem.setRGB(i, 130, 103, 185);
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
        if (DriverStation.isDisabled()) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                ledSubsystem.setRGB(i, 30, 3, 85);
            }
            ledSubsystem.sendData();
        } else if (DriverStation.isAutonomous() && DriverStation.getMatchTime() != -1) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                ledSubsystem.setRGB(i, 0, 0, 0);
            }
            ledSubsystem.sendData();
        } else {
            if (System.currentTimeMillis() - startTime >= waitTime) {
                for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                    if (i == onLEDIndex) {
                        ledSubsystem.setRGB(i, r, g, b);
                        // waitTime = Math.abs(ledSubsystem.getBufferLength() / 2 - i) * 5;
                        continue;
                    }
                    if (i == onLEDIndex + 1) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    if (i == onLEDIndex - 1) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    if (i == onLEDIndex - 2) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    if (i == onLEDIndex + 2) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    if (i == onLEDIndex + 3) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    if (i == onLEDIndex - 3) {
                        ledSubsystem.setRGB(i, r, g, b);
                        continue;
                    }
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }
                ledSubsystem.sendData();
                if (onLEDIndex + 1 > ledSubsystem.getBufferLength()) {
                    backwards = true;
                } else if (onLEDIndex - 1 < 0) {
                    backwards = false;
                }

                if (backwards) {
                    onLEDIndex--;
                } else {
                    onLEDIndex++;
                }

                startTime = System.currentTimeMillis();
            }
        }
    }
}
