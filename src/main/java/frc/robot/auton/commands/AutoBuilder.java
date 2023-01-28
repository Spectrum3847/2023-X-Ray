package frc.robot.auton.commands;

import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import frc.robot.Robot;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonConfig;
import frc.robot.swerve.SwerveConfig;

public
class AutoBuilder { // Create the AutoBuilder. This only needs to be created once when robot code
    // starts, not every
    // time you want to create an auto command. A good place to put this is in RobotContainer along
    // with your subsystems.
    public static final SwerveAutoBuilder autoBuilder =
            new SwerveAutoBuilder(
                    Robot.swerve.odometry::getPoseMeters, // Pose2d supplier
                    Robot.swerve.odometry
                            ::resetOdometry, // Pose2d consumer, used to reset odometry at the
                    // beginning of auto
                    SwerveConfig.swerveKinematics, // SwerveDriveKinematics
                    new PIDConstants(
                            AutonConfig.kPTranslationController,
                            AutonConfig.kITranslationController,
                            AutonConfig.kDTranslationController), // PID constants to correct for
                    // translation error (used to create
                    // the X and Y PID controllers)
                    new PIDConstants(
                            AutonConfig.kPRotationController,
                            AutonConfig.kIRotationController,
                            AutonConfig
                                    .kDRotationController), // PID constants to correct for rotation
                    // error (used to create the
                    // rotation controller)
                    Robot.swerve
                            ::setModuleStates, // Module states consumer used to output to the drive
                    // subsystem
                    Auton.eventMap,
                    true, // Should the path be automatically mirrored depending on
                    // alliance color
                    // Alliance.
                    Robot.swerve // The drive subsystem. Used to properly set the requirements of
                    // path following commands
                    );
}
