package frc.robot.test;

import edu.wpi.first.wpilibj2.command.Command;

public class Check {
    String name;
    Command command;

    //useless at the beginning but may be useful if you need to do additional checks that can't be done in the command itself. 
    public Check(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void run() {
        command.schedule();
    }

    public Command getCommand() {
        return command;
    }


    
}
