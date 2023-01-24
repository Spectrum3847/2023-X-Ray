package frc.robot.leds.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public abstract class LEDCommandBase extends CommandBase {
    String name;
    int priority;
    int timeout;

    public LEDCommandBase(String name, int priority, int timeout) {
        super();
        setName(name);
        addRequirements(Robot.leds);
        this.name = name;
        this.priority = priority;
        this.timeout = timeout;
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
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
