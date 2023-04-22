package frc.robot.leds.commands;

import frc.robot.Robot;
import frc.robot.leds.LEDs;

public class FireworkLEDCommand extends LEDCommandBase {
    private final LEDs ledSubsystem;

    double waitTime;
    long startTime;
    int onLEDIndex;
    int explosionIndex, explosionCounter;
    int value = 128;
    boolean backwards, explosion, explosionFinished = false;

    public FireworkLEDCommand(String name, int priority) {
        super(name, priority);
        ledSubsystem = Robot.leds;
        this.waitTime = 1;
        this.startTime = System.currentTimeMillis();
        onLEDIndex = ledSubsystem.getBufferLength() - 1;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        onLEDIndex = ledSubsystem.getBufferLength() - 1;
        for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
            if (i == onLEDIndex) {
                ledSubsystem.setRGB(i, 255, 255, 255);
                continue;
            }
            ledSubsystem.setRGB(i, 0, 0, 0);
        }
        ledSubsystem.sendData();
        onLEDIndex--;
    }

    /* Please don't look at this */
    @Override
    public void ledExecute() {
        if (explosionFinished) {
            for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                final int hue = (explosionIndex + (i * 180 / ledSubsystem.getBufferLength())) % 180;
                ledSubsystem.setHSV(i, hue, 255, value);
            }
            value--;
        } else if (explosion) {
            final int hue =
                    (explosionIndex + (explosionCounter * 180 / ledSubsystem.getBufferLength()))
                            % 180;
            if (explosionIndex + explosionCounter < ledSubsystem.getBufferLength()) {
                ledSubsystem.setHSV(explosionIndex + explosionCounter, hue, 255, 128);
                System.out.println("positive ran, index: " + (explosionIndex + explosionCounter));
            } else {
                explosionFinished = true;
                // explosion = false;
            }
            if (explosionIndex - explosionCounter >= 0) {
                ledSubsystem.setHSV(explosionIndex - explosionCounter, hue, 255, 128);
                System.out.println("negative ran, index: " + (explosionIndex - explosionCounter));
            }
            explosionCounter++;
            // ledSubsystem.sendData();
        } else {
            if (System.currentTimeMillis() - startTime >= waitTime) {
                for (int i = 0; i < ledSubsystem.getBufferLength(); i++) {
                    if (i == onLEDIndex) {
                        ledSubsystem.setRGB(i, 255, 255, 255);
                        // simulate momentum
                        double reverseIndex = Math.abs(ledSubsystem.getBufferLength() - i);
                        if (i < ledSubsystem.getBufferLength()) {
                            // waitTime = Math.exp(reverseIndex / 10);
                            waitTime = Math.pow(Math.E, ((reverseIndex + 5) / 9.75));
                        }
                        continue;
                    }
                    if (i == onLEDIndex + 1) {
                        if (onLEDIndex - 6 >= 0) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    if (i == onLEDIndex + 2) {
                        if (onLEDIndex - 7 >= 0) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    if (i == onLEDIndex + 3) {
                        if (onLEDIndex - 8 >= 0) {
                            ledSubsystem.setRGB(i, 255, 255, 255);
                        } else {
                            ledSubsystem.setRGB(i, 0, 0, 0);
                        }
                        continue;
                    }
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }
                // ledSubsystem.sendData();

                if (onLEDIndex - 5 < 0) {
                    explosionIndex = onLEDIndex;
                    explosionCounter = 0;
                    explosion = true;
                    // this is worse
                } else if (onLEDIndex - 6 < 0) {
                    waitTime = 750;
                } else if (onLEDIndex - 7 < 0) {
                    waitTime = 350;
                } else if (onLEDIndex - 8 < 0) {
                    waitTime = 150;
                }

                onLEDIndex--;
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
        return false;
    }
}
