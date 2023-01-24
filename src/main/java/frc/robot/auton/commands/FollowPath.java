package frc.robot.auton.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.EventMarker;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonConfig;
import frc.robot.trajectories.commands.FollowTrajectory;
import java.util.HashMap;
import java.util.List;

public class FollowPath extends SequentialCommandGroup {

    /** Creates a new Drive1Meter. */
    public FollowPath(String path, Boolean isFirstPath) {
        // An example trajectory to follow. All units in meters.
        PathPlannerTrajectory traj =
                PathPlanner.loadPath(path, AutonConfig.kMaxSpeed, AutonConfig.kMaxAccel);

        addCommands(
                AutonCommands.checkFirstPath(traj, isFirstPath),
                new FollowPath(new FollowTrajectory(traj), traj.getMarkers(), Auton.eventMap));
    }

    public FollowPath(
            FollowTrajectory followTrajectory,
            List<EventMarker> markers,
            HashMap<String, Command> eventMap) {}
}
