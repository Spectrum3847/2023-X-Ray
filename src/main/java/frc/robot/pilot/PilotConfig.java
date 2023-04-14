package frc.robot.pilot;

import edu.wpi.first.math.geometry.Translation2d;

/** Constants used by the Pilot Gamepad */
public class PilotConfig {
    public static final int port = 0;

    public static final double slowModeScaler = 0.3;
    public static final double elevatorUpModeScaler = 0.2;

    public static final double throttleDeadband = 0.2;
    public static final double throttleExp = 1.5;
    public static final double throttleScaler =
            1.0; // Multiples by SwerveMaxVelocity to determine max speed
    public static final boolean xInvert = true;
    public static final boolean yInvert = true;

    public static final double steeringDeadband = 0.1;
    public static final double steeringExp = 4;
    public static final double steeringScaler =
            0.4; // Multiplies by Swerve Max Angular Velocity to determine max steering
    public static final boolean steeringInvert = true;

    public static final double alignmentOffset = 8;

    public static final Translation2d intakeCoRmeters = new Translation2d(0, 0);
}
