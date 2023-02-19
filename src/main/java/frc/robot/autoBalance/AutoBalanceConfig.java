package frc.robot.autoBalance;

public class AutoBalanceConfig {
    public static final double balancedAngle = 0; // The angle the robot should be at when balanced
    public static final double kP = 0.007; // The proportional constant for the PID controller
    public static final double angleSetPoint =
            0; // The angle the PID controller should try to reach
    public static final double kTurn = 0.007; // The constant for the turn PID controller
}
