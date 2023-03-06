//Created by Spectrum3847
package frc.robot.test;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.test.commands.ConfirmCommand;

/**
 * A scheduler using Shuffleboard that is responsible for scheduling different system checks to be run in test mode. Different 
 * system checks composed of commands can be used to verify that systems are working properly. See {@link Check} for composing system checks.
 * 
 */
public class CheckScheduler {

    private SystemCheck systemCheck; //this may be bad
    protected ShuffleboardTab tab;
    private String status = "Idle";
    public ArrayList<Check> checks = new ArrayList<Check>();


    public CheckScheduler(SystemCheck systemCheck) {
        this.systemCheck = systemCheck;
        tab = Shuffleboard.getTab("Test");
        tab.addString("Start", () -> "Press A to start").withPosition(0, 0); //change to "Press A to continue"
        tab.addString("Status", () -> status).withPosition(0, 1);
        startScheduler();
    }

    public void startScheduler() {
        new ConfirmCommand("Start Tests").andThen(() -> runScheduler(), systemCheck).schedule();
    }

    public void runScheduler() {
        for(int i = 0; i < checks.size(); i++) {
            waitForConfirmation(checks.get(i));
            checks.remove(i);
        }
    }


    public void waitForConfirmation(Check check) {
        new ConfirmCommand("Waiting for A").alongWith(new InstantCommand(() -> status = "Waiting", systemCheck)).andThen(() -> runCheck(check), systemCheck).schedule();
    }

    public void runCheck(Check check) {
        status = "Running " + check.getName();
        check.run();

    }

    public void addCheck(Check check) {
        checks.add(check);
    }

    public void removeCheck(Check check) {
        checks.remove(check);
    }

    public void clearChecks() {
        checks.clear();
    }


    
}
