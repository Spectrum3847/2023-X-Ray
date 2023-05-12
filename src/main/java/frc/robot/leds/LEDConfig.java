package frc.robot.leds;

import frc.robot.Robot;
import frc.robot.RobotConfig;

/** Add your docs here. */
public class LEDConfig {
    public final int ADDRESSABLE_LED = RobotConfig.ledPWMport;
    public final int LED_COUNT = 47;
    public final int END_LED_START_TIME = 10;

    public LEDConfig() {
        switch (Robot.config.getRobotType()) {
            case XRAY2023:
                // Set all the constants specifically for the competition robot
                break;
            case PRACTICE2023:
                // Set all the constants specifically for the practice robot
                break;
            case SIM:
            case REPLAY:
                // Set all the constants specifically for the simulation
                break;
            default:
                break;
        }
    }
}
