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
                                "OverCharge1",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel - 0.7)))
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OverCharge2",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBalanceSpeed,
                                                        AutonConfig.kMaxBalanceAccel))))
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OverCharge3",
                                                new PathConstraints(
                                                        AutonConfig.kMaxSpeed,
                                                        AutonConfig.kMaxAccel - 0.7))))
                .andThen(new AutoBalance())
                .andThen(new LockSwerve().withTimeout(0.1))
                .andThen(AutonCommands.secondShot());
    }

    public static Command CleanSide() {
        return (AutonCommands.coneMid()
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "CleanSide1",
                                                        new PathConstraints(
                                                                AutonConfig.kMaxCleanSpeed - 0.5,
                                                                AutonConfig.kMaxCleanAccel - 0.5))))
                        .andThen(AutonCommands.alignToGridMid())
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "CleanSide2",
                                                        new PathConstraints(
                                                                AutonConfig.kMaxCleanSpeed - 0.5,
                                                                AutonConfig.kMaxCleanAccel
                                                                        - 0.5)))))
                .withTimeout(14.85)
                .andThen(AutonCommands.ejectCone().withTimeout(0.15));
    }

    public static Command BumpSide3() {
        return (Auton.getAutoBuilder()
                        .fullAuto(
                                PathPlanner.loadPathGroup(
                                        "Bump1",
                                        new PathConstraints(
                                                AutonConfig.kMaxBumpSpeed,
                                                AutonConfig.kMaxBumpAccel - .5)))
                        .andThen(AutonCommands.alignToGridMid())
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "Bump2",
                                                        new PathConstraints(
                                                                AutonConfig.kMaxBumpSpeed,
                                                                AutonConfig.kMaxBumpAccel)))))
                .withTimeout(14.8)
                .andThen(AutonCommands.secondShotLaunch().withTimeout(0.2));
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

    public static Command BumpSide2() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Bump1",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed, AutonConfig.kMaxBumpAccel)))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "Bump2CenterOption",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBumpSpeed,
                                                        AutonConfig.kMaxBumpAccel))));
    }

    public static Command CleanSide2() {
        return AutonCommands.coneMid()
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "CleanSide1",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 0.5,
                                                        AutonConfig.kMaxCleanAccel - 0.5))))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "CleanSide2CenterOption",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 0.5,
                                                        AutonConfig.kMaxCleanAccel - 0.5))));
    }
}
