package frc.robot.auton.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.commands.LockSwerve;
import java.util.List;

public class AutoPaths {
    public static Command OverCharge() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Over Charge (1)",
                                new PathConstraints(AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel)))
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "Over Charge (2)",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBalanceSpeed,
                                                        AutonConfig.kMaxBalanceAccel))))
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "Over Charge (3)",
                                                new PathConstraints(
                                                        AutonConfig.kMaxSpeed,
                                                        AutonConfig.kMaxAccel))))
                .andThen(new AutoBalance())
                .andThen(new LockSwerve().withTimeout(0.1))
                .andThen(AutonCommands.secondShot());
    }

    private static List<PathPlannerTrajectory> clean2path =
            PathPlanner.loadPathGroup(
                    "CleanSide2",
                    new PathConstraints(
                            AutonConfig.kMaxCleanSpeed - 0.5, AutonConfig.kMaxCleanAccel - 0.5));

    private static SequentialCommandGroup cleanSide =
            Auton.getAutoBuilder()
                    .fullAuto(
                            PathPlanner.loadPathGroup(
                                    "CleanSide1",
                                    new PathConstraints(
                                            AutonConfig.kMaxCleanSpeed - 0.5,
                                            AutonConfig.kMaxCleanAccel - 0.5)))
                    .andThen(AutonCommands.alignToGridMid())
                    .andThen(Auton.getAutoBuilder().fullAuto(clean2path))
                    .andThen(AutonCommands.autonConeFloorGoal().withTimeout(0.5));

    public static Command CleanSide() {
        return cleanSide;
    }

    public static Command BumpSide2() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Bump 2 + 1 (1)",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed - 2,
                                        AutonConfig.kMaxBumpAccel - 2)))
                .andThen(AutonCommands.alignToGridMid().withTimeout(AutonConfig.midCubeAlignTime2));
    }

    public static Command CleanSide2() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Clean Side (1)",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed - 1,
                                        AutonConfig.kMaxBumpAccel - 1)))
                .andThen(AutonCommands.alignToGridMid().withTimeout(AutonConfig.midCubeAlignTime2));
    }

    public static Command BumpSide3() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Bump 2 + 1 (1)",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed, AutonConfig.kMaxBumpAccel)))
                .andThen(AutonCommands.alignToGridMid().withTimeout(AutonConfig.midCubeAlignTime))
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "Bump 2 + 1 (2)",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBumpSpeed,
                                                        AutonConfig.kMaxBumpAccel)))
                                .andThen(AutonCommands.alignToGridLowCone()));
    }

    public static Command BallBottomBalance() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "3 Ball Bottom w Balance",
                                new PathConstraints(AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel)))
                .andThen(new AutoBalance())
                .andThen(new LockSwerve().withTimeout(0.1))
                .andThen(AutonCommands.thirdShotBalance());
    }

    public static Command BallBottomAngle() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "3 Ball Bottom w Angle",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAngleAccel)));
    }

    public static Command Special() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Special",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxSpecialAccel)));
    }
}
