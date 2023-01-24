# Trajectory

PID configuration and commands for x, y, & theta controllers and trajectory following commands.
This is seperated from Auton because we can use parts of this code in Telop as well for automated tasks, espcially the spin in place commands, and eventually the automated trajectory generation and being able to automatically drive to loading or scoring locations.

## Tasks
* Command to follow a trajectory
* Command to generate a trajectory from where we currently are, to some point on the field
* Command to write to orient wheels before following first command. (More below)

"The other option is to create a command that will take in the trajectory you want to run, and orient the modules based on the first state. Here’s some example steps for that:

Get the heading(direction of travel) of the first state
Use this heading to get the X and Y velocity components of some velocity. Doesn’t really matter what you choose for the velocity since we will just ignore it. If you just use a velocity of 1 you can just ignore the velocity in the calculations altogether:
xVel = cos(heading)
yVel = sin(heading)
Use your kinematics to convert these field relative speeds into swerve module states
Set your module rotations to match the rotations of these states. Do no apply the speed component
Wait for some amount of time so the modules can finish rotating
Then you can run your path following command."
