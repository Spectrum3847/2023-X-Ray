// package frc.robot.trajectories.commands;

// import com.pathplanner.lib.auto.PIDConstants;
// import com.pathplanner.lib.auto.SwerveAutoBuilder;
// import frc.robot.Robot;
// import frc.robot.auton.Auton;
// import frc.robot.trajectories.TrajectoriesConfig;

// public
// class PathBuilderEstimatedPose { // Used to create paths for Autonomus and for On-the-Fly
// Generation
//     public static final SwerveAutoBuilder pathBuilder =
//             new SwerveAutoBuilder(
//                     Robot.pose::getEstimatedPose, // Pose2d supplier
//                     Robot.swerve.odometry
//                             ::resetOdometry, // Pose2d consumer, used to reset odometry at the
//                     // beginning of auto
//                     Robot.swerve.config.swerveKinematics, // SwerveDriveKinematics
//                     new PIDConstants(
//                             TrajectoriesConfig.kPTranslationController,
//                             TrajectoriesConfig.kITranslationController,
//                             TrajectoriesConfig
//                                     .kDTranslationController), // PID constants to correct for
//                     // translation error (used to create
//                     // the X and Y PID controllers)
//                     new PIDConstants(
//                             TrajectoriesConfig.kPRotationController,
//                             TrajectoriesConfig.kIRotationController,
//                             TrajectoriesConfig
//                                     .kDRotationController), // PID constants to correct for
// rotation
//                     // error (used to create the
//                     // rotation controller)
//                     Robot.swerve
//                             ::setModuleStatesAuto, // Module states consumer used to output to
// the
//                     // drive
//                     // subsystem
//                     Auton.eventMap, // Gets the event map values to use for running addional
//                     // commands during auto
//                     true, // Should the path be automatically mirrored depending on
//                     // alliance color
//                     // Alliance.
//                     Robot.swerve // The drive subsystem. Used to properly set the requirements of
//                     // path following commands
//                     );
// }
