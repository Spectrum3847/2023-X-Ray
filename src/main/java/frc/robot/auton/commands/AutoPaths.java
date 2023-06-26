package frc.robot.auton.commands;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.commands.LockSwerve;

public class AutoPaths {
        public static Command CleanSide() {
                return Auton.getAutoBuilder()
                        .fullAuto(PathPlanner.loadPathGroup("CleanSide1", new PathConstraints(4, 3)))
                        .andThen(AutonCommands.alignToGridMidFast())
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "CleanSide2", new PathConstraints(4, 3.05))))
                        .andThen(AutonCommands.alignToGridHighFast());
            }
        
            public static Command BumpSide() {
                return Auton.getAutoBuilder()
                        .fullAuto(PathPlanner.loadPathGroup("Bump1", new PathConstraints(4, 2.6)))
                        .andThen(
                                AutonCommands.coolShotFast()
                                        .alongWith(AutonCommands.faceForward())
                                        .withTimeout(0.55))
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "Bump2", new PathConstraints(4, 2.6)))
                                        .andThen(AutonCommands.alignToGridHighFast()));
            }
        
            public static Command CleanSideWBalance() {
                return Auton.getAutoBuilder()
                        .fullAuto(PathPlanner.loadPathGroup("CleanSide1", new PathConstraints(4, 3)))
                        .andThen(AutonCommands.alignToGridMidFast())
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "CleanSide2Balance",
                                                        new PathConstraints(4, 3))))
                        .andThen((new AutoBalance()))
                        .andThen(new LockSwerve().withTimeout(0.1));
            }
    
        public static Command OverCharge() {
        return AutonCommands.coneMid()
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OverCharge1",
                                                new PathConstraints(
                                                        AutonConfig.kMaxSpeed,
                                                        AutonConfig.kMaxAccel - 0.7)))
                                .andThen(
                                        Auton.getAutoBuilder()
                                                .fullAuto(
                                                        PathPlanner.loadPathGroup(
                                                                "OverCharge2",
                                                                new PathConstraints(
                                                                        AutonConfig
                                                                                .kMaxBalanceSpeed,
                                                                        AutonConfig
                                                                                .kMaxBalanceAccel))))
                                .andThen(
                                        Auton.getAutoBuilder()
                                                .fullAuto(
                                                        PathPlanner.loadPathGroup(
                                                                "OverCharge3",
                                                                new PathConstraints(
                                                                        AutonConfig.kMaxSpeed,
                                                                        AutonConfig.kMaxAccel
                                                                                - 0.7))))
                                .andThen(new AutoBalance())
                                .andThen(new LockSwerve().withTimeout(0.1))
                                .andThen(AutonCommands.secondShot()));
    }

    public static Command OldCleanSide() {
        return (AutonCommands.coneMid()
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "OldCleanSide1",
                                                        new PathConstraints(
                                                                AutonConfig.kMaxCleanSpeed - 0.5,
                                                                AutonConfig.kMaxCleanAccel - 0.5))))
                        .andThen(AutonCommands.alignToGridMid())
                        .andThen(
                                Auton.getAutoBuilder()
                                        .fullAuto(
                                                PathPlanner.loadPathGroup(
                                                        "OldCleanSide2",
                                                        new PathConstraints(
                                                                AutonConfig.kMaxCleanSpeed - 0.5,
                                                                AutonConfig.kMaxCleanAccel
                                                                        - 0.5)))))
                .withTimeout(14.85)
                .andThen(AutonCommands.ejectCone().withTimeout(0.15));
    }

    public static Command OldBumpSide3() {
        return (Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "OldBump1",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed, AutonConfig.kMaxBumpAccel - 1)))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldBump2",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBumpSpeed,
                                                        AutonConfig.kMaxBumpAccel - 1)))));
        // .withTimeout(14.8)
        // .andThen(new EjectCube().withTimeout(0.2));
    }

    public static Command OldBallBottomBalance() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Old3 Ball Bottom w Balance",
                                new PathConstraints(AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel)))
                .andThen(new AutoBalance())
                .andThen(new LockSwerve().withTimeout(0.1))
                .andThen(AutonCommands.thirdShotBalance());
    }

    public static Command OldBallBottomAngle() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "Old3 Ball Bottom w Angle",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxAngleAccel)));
    }

    public static Command OldSpecial() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "OldSpecial",
                                new PathConstraints(
                                        AutonConfig.kMaxSpeed, AutonConfig.kMaxSpecialAccel)));
    }

    public static Command OldBumpSide2() {
        return Auton.getAutoBuilder()
                .fullAuto(
                        PathPlanner.loadPathGroup(
                                "OldBump1",
                                new PathConstraints(
                                        AutonConfig.kMaxBumpSpeed, AutonConfig.kMaxBumpAccel)))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldBump2CenterOption",
                                                new PathConstraints(
                                                        AutonConfig.kMaxBumpSpeed,
                                                        AutonConfig.kMaxBumpAccel))));
    }

    public static Command OldCleanSide2() {
        return AutonCommands.coneMid()
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldCleanSide1",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 0.5,
                                                        AutonConfig.kMaxCleanAccel - 0.5))))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldCleanSide2CenterOption",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 0.5,
                                                        AutonConfig.kMaxCleanAccel - 0.5))));
    }

    public static Command OldCleanSide2Good() {
        return AutonCommands.coneMid()
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldClean Side 2",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 1,
                                                        AutonConfig.kMaxCleanAccel - 1.5))))
                .andThen(AutonCommands.alignToGridMid());
    }

    public static Command OldCleanSide3Good() {
        return AutonCommands.coneMid()
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldClean Side 2",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 1,
                                                        AutonConfig.kMaxCleanAccel - 1.5))))
                .andThen(AutonCommands.alignToGridMid())
                .andThen(
                        Auton.getAutoBuilder()
                                .fullAuto(
                                        PathPlanner.loadPathGroup(
                                                "OldClean Side 2 (2)",
                                                new PathConstraints(
                                                        AutonConfig.kMaxCleanSpeed - 1,
                                                        AutonConfig.kMaxCleanAccel - 1.5))));
    }
}
