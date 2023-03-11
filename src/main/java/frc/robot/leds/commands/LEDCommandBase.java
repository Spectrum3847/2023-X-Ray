package frc.robot.leds.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public abstract class LEDCommandBase extends CommandBase {
    String name;
    int priority;
    double timeout;
    boolean parallel;
    double scope; // 0-1 representing percentage of lights used by command
    protected final LEDs ledSubsystem;
    protected int availableLength;

    public LEDCommandBase(String name, int priority, double timeout, double scope) {
        super();
        setName(name);
        ledSubsystem = Robot.leds;
        // addRequirements(Robot.leds);
        this.name = name;
        this.priority = priority;
        this.timeout = timeout;
        checkScope(scope);
    }

    public LEDCommandBase(String name, int priority, double scope) {
        super();
        setName(name);
        ledSubsystem = Robot.leds;
        // addRequirements(Robot.leds);
        this.name = name;
        this.priority = priority;
        this.timeout = -101;
        checkScope(scope);
    }

    /**
     * Checks multiple scope cases to set parallel status and available led length accordingly. Also
     * checks against out of bounds values.
     *
     * @param scope 0-1 representing percentage of lights used by command. 1 indicates an
     *     interrupting led command.
     */
    private void checkScope(double scope) {
        if (scope < 0) scope = 0;
        if (scope > 1) scope = 1;
        if (scope == 1) {
            this.parallel = false;
        } else {
            this.parallel = true;
        }
        this.scope = scope;

        // available length shouldn't be 0 if using a scope
        availableLength = (int) (ledSubsystem.getBufferLength() * scope);
        if (availableLength == 0) availableLength = 1;
    }

    public boolean isParallel() {
        return parallel;
    }

    @Override
    public void initialize() {
        Robot.leds.scheduler.addAnimation(name, this, priority, timeout);
    }

    public abstract void ledInitialize();

    public abstract void ledExecute();

    public boolean runsWhenDisabled() {
        return true;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Robot.leds.scheduler.removeAnimation(name);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
