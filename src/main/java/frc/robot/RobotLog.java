package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedPowerDistribution;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class RobotLog {

    public Logger logger = Logger.getInstance();

    /** Setups logging with advantageKit */
    public RobotLog(Robot robot) {
        if (RobotConfig.enableLogging) {
            // Record metadata
            logger.recordMetadata("MAC Address", Robot.MAC);
            logger.recordMetadata("RuntimeType", Robot.getRuntimeType().toString());
            logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
            logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
            logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
            logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
            logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
            switch (BuildConstants.DIRTY) {
                case 0:
                    logger.recordMetadata("GitDirty", "All changes committed");
                    break;
                case 1:
                    logger.recordMetadata("GitDirty", "Uncomitted changes");
                    break;
                default:
                    logger.recordMetadata("GitDirty", "Unknown");
                    break;
            }
            // Set up data receivers & replay source
            switch (Robot.config.getRobotType()) {
                    // Running on a comp robot, log to a USB stick
                case COMP:
                case PRACTICE:
                    logger.addDataReceiver(new WPILOGWriter("/media/sda1/"));
                    logger.addDataReceiver(new NT4Publisher());
                    LoggedPowerDistribution.getInstance(0, Robot.config.PowerDistributionType);
                    break;

                    // Running a physics simulator, log to local folder
                case SIM:
                    logger.addDataReceiver(new WPILOGWriter(""));
                    logger.addDataReceiver(new NT4Publisher());
                    break;

                    // Replaying a log, set up replay source
                case REPLAY:
                    robot.setUseTiming(false); // Run as fast as possible
                    String logPath = LogFileUtil.findReplayLog();
                    logger.setReplaySource(new WPILOGReader(logPath));
                    logger.addDataReceiver(
                            new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
                    break;
            }

            // Start AdvantageKit logger
            logger.start();
        } else {
            logger.end();
        }
    }
}
