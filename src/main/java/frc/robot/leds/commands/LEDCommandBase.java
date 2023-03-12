package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.leds.LEDs;

public abstract class LEDCommandBase extends CommandBase {
    String name;
    int priority;
    double timeout;
    boolean parallel;
    boolean dynamic; // is not restricted to start at length 0
    double scope; // 0-1 representing percentage of lights used by command
    protected final LEDs ledSubsystem;
    protected int availableLength;
    protected int startingLED;

    /**
     * @param name
     * @param priority see {@link Priority}
     * @param timeout
     * @param scope
     */
    public LEDCommandBase(String name, Priority priority, double timeout, double scope) {
        super();
        setName(name);
        ledSubsystem = Robot.leds;
        // addRequirements(Robot.leds);
        this.name = name;
        this.timeout = timeout;
        this.startingLED = 0;
        checkScope(scope);
        assignPriority(priority);
    }

    public LEDCommandBase(String name, Priority priority, double scope) {
        super();
        setName(name);
        ledSubsystem = Robot.leds;
        // addRequirements(Robot.leds);
        this.name = name;
        this.timeout = -101;
        this.startingLED = 0;
        this.dynamic = false;
        checkScope(scope);
        assignPriority(priority);
    }

    /**
     * Checks multiple scope cases to set parallel status and available led length accordingly. Also
     * checks against out of bounds values.
     *
     * @param scope 0-1 representing percentage of leds used by command. 1 indicates a base led
     *     (uses all remaining leds after parallel leds are shown). Two base leds can't be run at
     *     the same time.
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

    /**
     * Assign integer priority levels to LEDs from enum settings. Tiers should be 30 apart to avoid
     * overlapping (very unlikely)
     *
     * @param priority
     */
    private void assignPriority(Priority priority) {
        switch (priority) {
            case CRITICAL:
                this.priority = 150;
                break;
            case PARALLEL:
                if (!parallel) {
                    DriverStation.reportWarning(this.name + " must have a scope < 1", false);
                    checkScope(0.25);
                }
                this.priority = 120;
                break;
            case DYNAMIC:
                if (!parallel) {
                    DriverStation.reportWarning(this.name + " must have a scope < 1", false);
                    checkScope(0.25);
                }
                this.priority = 120;
                this.dynamic = true;
                break;
            case BASE:
                this.priority = 90;
                break;
            case DEFAULT:
                this.priority = 1;
                break;
            default:
                this.priority = 5;
                DriverStation.reportWarning("Unchecked priority case", false);
        }
    }

    public int getStartingLED() {
        return startingLED;
    }

    public void setStartingLED(int index) {
        startingLED = index;
    }

    public int getEndingLED() {
        return startingLED + availableLength;
    }

    public boolean isParallel() {
        return parallel;
    }

    public boolean isDynamic() {
        return dynamic;
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

    /**
     * An enum specifying priority levels for different tiers of LED commands. Any sequence of LED
     * commands with the same priority level get organized in order of execution with the latest
     * being the highest priority
     */
    public enum Priority {
        /** The highest level of priority; possibly used for system error feedback */
        CRITICAL,
        /**
         * LEDs that can be run alongside other dynamic LEDs and on top of base led animations;
         * specific feedback such as "vision pose good". Because these can be run over base leds,
         * they have higher priority
         */
        PARALLEL,
        /**
         * The same type of LED as {@link Priority#PARALLEL} but moves under existing parallel LEDs
         * instead of overriding them
         */
        DYNAMIC,
        /**
         * LEDs that can be run under parallel animations. There can only be one base LED running at
         * a time
         */
        BASE,
        /**
         * The lowest level of priority. This should be used for a single default command that runs
         * when no other animation sequence is running
         */
        DEFAULT
    }
}
