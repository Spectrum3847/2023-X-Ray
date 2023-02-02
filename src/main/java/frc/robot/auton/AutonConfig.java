package frc.robot.auton;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.trajectories.TrajectoriesConfig;

/** Add your docs here. */
public final class AutonConfig {

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                    TrajectoriesConfig.kMaxAngularSpeedRadiansPerSecond,
                    TrajectoriesConfig.kMaxAngularSpeedRadiansPerSecondSquared);

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
