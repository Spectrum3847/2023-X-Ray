# Swerve
Handles the configuration and main commands of the swerve drive. Any functions that directly relate to the basic operation of the swerve are included here.

PID configuration and commands for x, y, theta controllers and trajectory following are in the trajectory system.

## Tasks
* Write methods for acceleration limiting, may want to test slewratelimits on the pilot inputs first.
* Create a command that changes the center of rotation when turning. Allow pilot to turn around a certain module, or corner of the robot, used for getting around defense.
* Create a way to zero the cancoder off-sets so if needed we can start with the wheels forward and known that works. This is incase our cancoders aren't working and we need to run a match.
* Create an easy way to store the cancoder off-sets to a file on the roboRIO.
* Create a Pose class: has a pose estimater and all the things needed for esimating the robots position.
* Create Test methods, to auto check each module, and compare to known current ranges, for steering and drive on blocks. Check all modules turn to 90 set points, etc.
