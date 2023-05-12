package frc.robot.leds.commands;

import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class FireworkLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;

    double waitTime;
    long startTime;
    int onLEDIndex, finalIndex;
    int explosionIndex, explosionCounter;
    int value = 128;
    int[] hues;
    boolean backwards, explosion, explosionFinished = false;

    public FireworkLEDCommand(String name, int priority) {
        super(name, priority);
        super.prioritizeTimeout = true;
        ledSubsystem = Robot.leds;
        this.waitTime = 1;
        this.startTime = System.currentTimeMillis();
        onLEDIndex = 0;
        this.finalIndex = ledSubsystem.getBufferLength() - 1;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        hues = new int[ledSubsystem.getBufferLength()];
        onLEDIndex = 0;
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

    /* Please don't look at this */
    @Override
    public void ledExecute() {
        if (explosionFinished) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                ledSubsystem.setHSV(i, (int) hues[i], 255, value);
            }
            value--;
        } else if (explosion) {
            final int hue =
                    (explosionIndex + (explosionCounter * 180 / ledSubsystem.getBufferLength()))
                            % 180;
            if (explosionIndex + explosionCounter < ledSubsystem.getBufferLength()) {
                hues[explosionIndex + explosionCounter] = hue;
                ledSubsystem.setHSV(explosionIndex + explosionCounter, hue, 255, 128);
            }
            if (explosionIndex - explosionCounter >= 0
                    && explosionIndex - explosionCounter < ledSubsystem.getBufferLength()) {
                hues[explosionIndex - explosionCounter] = hue;
                ledSubsystem.setHSV(explosionIndex - explosionCounter, hue, 255, 128);
            } else {
                explosionFinished = true;
            }
            explosionCounter++;
        } else {
            if (System.currentTimeMillis() - startTime >= waitTime) {
                for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                    if (i == onLEDIndex) {
                        ledSubsystem.setRGB(i, 255, 255, 255);
                        // simulate momentum
                        // double reverseIndex = Math.abs(ledSubsystem.getBufferLength() - i);
                        if (i < ledSubsystem.getBufferLength()) {
                            // waitTime = Math.pow(Math.E, ((reverseIndex + 5) / 9.75));
                            waitTime = Math.pow(Math.E, ((i + 5) / 9.75));
                        }
                        continue;
                    }
                    if (i == onLEDIndex - 1) {
                        if (finalIndex - onLEDIndex >= 6) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    if (i == onLEDIndex - 2) {
                        if (finalIndex - onLEDIndex >= 7) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    if (i == onLEDIndex - 3) {
                        if (finalIndex - onLEDIndex >= 8) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }

                if (finalIndex - onLEDIndex < 5) {
                    explosionIndex = onLEDIndex;
                    explosionCounter = 0;
                    explosion = true;
                    // this is worse
                } else if (finalIndex - onLEDIndex < 6) {
                    waitTime = 750;
                } else if (finalIndex - onLEDIndex < 7) {
                    waitTime = 350;
                } else if (finalIndex - onLEDIndex < 8) {
                    waitTime = 150;
                }

                onLEDIndex++;
                startTime = System.currentTimeMillis();
            }
        }
        ledSubsystem.sendData();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        explosion = false;
        explosionFinished = false;
        waitTime = 1;
        value = 128;
    }

    @Override
    public boolean isFinished() {
        return value == 0;
    }
}
