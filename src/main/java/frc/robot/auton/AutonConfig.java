package frc.robot.auton;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.swerve.SwerveConfig;

/** Add your docs here. */
public final class AutonConfig {
    public static final double posAangle = 142; // Infront of Left Ball (Cargo B)
    public static final double posA90angle = 91.5; // Sideways A setup, intake toward field boarder
    public static final double posBangle = 120;
    public static final double posCangle = 205; // Pointing to Cargo D
    public static final double posDangle = 272; // 263; //Infront of Right Ball (Cargo E)
    public static final double posD90angle =
            181.5; // Sideways D Setup intake facing the driver station

    public static final double kMaxSpeed = 2.7;
    public static final double kMaxAccel = 2.4; // 2 worked but took too long
    public static final double kMaxAngularSpeedRadiansPerSecond = SwerveConfig.maxAngularVelocity;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared =
            SwerveConfig.maxAngularVelocity;

    public static final double kPXController = 0.6;
    public static final double kDXController = 0;
    public static final double kPYController = kPXController;
    public static final double kDYController = kDXController;
    public static final double kPThetaController = 5;
    public static final double kDThetaController = 0.01;

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                    kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

    public static enum AutoPosition {
        ORIGIN;

        public Pose2d getPose() {
            switch (this) {
                case ORIGIN:
                    return new Pose2d();
                default:
                    return new Pose2d();
            }
        }
    }
}
