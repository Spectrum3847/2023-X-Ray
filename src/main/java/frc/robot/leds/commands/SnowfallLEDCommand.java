package frc.robot.leds.commands;

public class SnowfallLEDCommand extends LEDCommandBase {
    /** Creates a new SnowfallLEDCommand. */
    long waitTime;

    long startTime;
    int stage;

    public SnowfallLEDCommand(String name, Priority priority, double timeout, double scope) {
        super(name, priority, timeout, scope);
        this.waitTime = 100;
        this.startTime = System.currentTimeMillis();
        stage = 0;
    }

    public SnowfallLEDCommand(String name, Priority priority, double scope) {
        super(name, priority, scope);
        this.waitTime = 100;
        this.startTime = System.currentTimeMillis();
        stage = 0;
    }

    // Called when the command is initially scheduled.
    @Override
    public void ledInitialize() {
        for (int i = 0; i < availableLength; i++) {
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
            for (int i = 0; i < availableLength; i++) {
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
