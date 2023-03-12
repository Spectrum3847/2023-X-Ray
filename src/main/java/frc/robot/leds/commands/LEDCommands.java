package frc.robot.leds.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.leds.commands.LEDCommandBase.Priority;

/** All of the commands to schedule LEDs */
public class LEDCommands {
    public static void setupDefaultCommand() {}

    public static void setupLEDTriggers() {
        Trigger elevatorUp =
                new Trigger(() -> Elevator.falconToInches(Robot.elevator.getPosition()) > 12);
        Trigger poseOverriden = new Trigger(() -> Robot.vision.poseOverriden);
        elevatorUp.whileTrue(elevatorHeightLED());
        poseOverriden.whileTrue(success());
    }

    public static Command success() {
        return new OneColorLEDCommand(Color.kGreen, "LED Success", Priority.PARALLEL, 0.25);
    }

    public static Command failure() {
        return new OneColorLEDCommand(Color.kRed, "LED Fail", Priority.PARALLEL, 0.25);
    }

    public static Command coneFloorLED() {
        return new OneColorLEDCommand(Color.kYellow, "Yellow Floor Cone", Priority.BASE, 1);
    }

    public static Command coneShelfLED() {
        return new BlinkLEDCommand(Color.kYellow, "Yellow Shelf Cone", Priority.BASE, 1);
    }

    public static Command cubeLED() {
        return new OneColorLEDCommand(Color.kPurple, "Purple Cube", Priority.BASE, 1);
    }

    public static Command elevatorHeightLED() {
        return new RainbowLEDCommand("Elevator Height LED", Priority.BASE, 1);
    }

    public static Command test() {
        return new OneColorLEDCommand(Color.kSeaGreen, "Test Green", Priority.DYNAMIC, 0.25);
    }

    public static Command purpleSolid(int timeout) {
        return new OneColorLEDCommand(
                new Color(100, 10, 255), "Purple Solid", Priority.BASE, timeout, 1);
    }

    public static Command whiteSolid() {
        return new OneColorLEDCommand(Color.kWhite, "White", Priority.BASE, 1);
    }
}
