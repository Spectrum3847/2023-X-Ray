package frc.robot.leds;

import frc.robot.Robot;
import frc.robot.RobotConfig;

/** Add your docs here. */
public class LEDConfig {
    public final int ADDRESSABLE_LED = RobotConfig.ledPWMport;
    public final int LED_COUNT = 60;

    public LEDConfig() {
        switch (Robot.config.getRobotType()) {
            case COMP:
                // Set all the constants specifically for the competition robot
                break;
            case PRACTICE:
                // Set all the constants specifically for the practice robot
                break;
            case SIM:
            case REPLAY:
                // Set all the constants specifically for the simulation
                break;
        }
    }
}
