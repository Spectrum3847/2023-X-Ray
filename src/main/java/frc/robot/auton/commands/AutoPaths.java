package frc.robot.auton.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.commands.LockSwerve;

public class AutoPaths {
    public static Command OverCharge() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Over Charge",
                                new PathConstraints(AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel)))
                .andThen(new AutoBalance())
                .andThen(new LockSwerve().withTimeout(0.1))
                .andThen(AutonCommands.secondShot());
    }

    public static Command CleanSide() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Clean Side",
                                new PathConstraints(
                                        AutonConfig.kMaxCleanSpeed, AutonConfig.kMaxCleanAccel)));
    }

    public static Command BumpSide2() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Bump 2 + 1 (1)",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed, AutonConfig.kMaxBumpAccel)))
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
