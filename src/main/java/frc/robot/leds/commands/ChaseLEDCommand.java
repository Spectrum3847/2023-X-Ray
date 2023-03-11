package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.DriverStation;

public class ChaseLEDCommand extends LEDCommandBase {
    /** Creates a new ChaseLEDCommand. */
    double waitTime;

    long startTime;
    int onLEDIndex;
    boolean backwards = false;

    public ChaseLEDCommand(String name, int priority, double timeout, double scope) {
        super(name, priority, timeout, scope);
        this.waitTime = 0;
        this.startTime = System.currentTimeMillis();
        onLEDIndex = 0;
    }

    public ChaseLEDCommand(String name, int priority, double scope) {
        super(name, priority, scope);
        this.waitTime = 0;
        this.startTime = System.currentTimeMillis();
        onLEDIndex = 0;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        for (int i = 0; i < availableLength; i++) {
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
            for (int i = 0; i < availableLength; i++) {
                ledSubsystem.setRGB(i, 30, 3, 85);
            }
            ledSubsystem.sendData();
        } else {
            if (System.currentTimeMillis() - startTime >= waitTime) {
                for (int i = 0; i < availableLength; i++) {
                    if (i == onLEDIndex) {
                        ledSubsystem.setRGB(i, 130, 103, 185);
                        // waitTime = Math.abs(availableLength / 2 - i) * 5;
                        continue;
                    }
                    if (i == onLEDIndex + 1) {
                        ledSubsystem.setRGB(i, 130, 103, 185);
                        continue;
                    }
                    if (i == onLEDIndex - 1) {
                        ledSubsystem.setRGB(i, 130, 103, 185);
                        continue;
                    }
                    if (i == onLEDIndex - 2) {
                        ledSubsystem.setRGB(i, 80, 53, 135);
                        continue;
                    }
                    if (i == onLEDIndex + 2) {
                        ledSubsystem.setRGB(i, 80, 53, 135);
                        continue;
                    }
                    if (i == onLEDIndex + 3) {
                        ledSubsystem.setRGB(i, 30, 3, 85);
                        continue;
                    }
                    if (i == onLEDIndex - 3) {
                        ledSubsystem.setRGB(i, 30, 3, 85);
                        continue;
                    }
                    ledSubsystem.setRGB(i, 0, 0, 0);
                }
                ledSubsystem.sendData();
                if (onLEDIndex + 1 > availableLength) {
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
