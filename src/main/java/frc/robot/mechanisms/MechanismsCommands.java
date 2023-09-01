package frc.robot.mechanisms;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.swerve.commands.SwerveDrive;

public class MechanismsCommands {

    public static Command coneStandingIntake() {
        return ElevatorCommands.coneStandingIntake()
                .alongWith(FourBarCommands.coneStandingIntake())
                .withTimeout(0.3)
                .andThen(
                        new ConeIntake()
                                .withTimeout(2)
                                .alongWith(
                                        new SwerveDrive(
                                                        () -> 1, // drive fwd at full speed
                                                        () -> 0,
                                                        () -> 0,
                                                        () -> 1.0, // full velocity
                                                        () -> false // Robot Relative
                                                        )
                                                .withTimeout(1)
                                                .andThen(homeSystems().withTimeout(1))))
                .withName("MechanismsStandingCone");
    }

    public static Command cubeIntake() {
        return ElevatorCommands.cubeIntake()
                .alongWith(FourBarCommands.cubeIntake())
                .withTimeout(0.3)
                .andThen(
                        new CubeIntake()
                                .withTimeout(1)
                                .alongWith(
                                        new SwerveDrive(
                                                        () -> 1, // drive fwd at full speed
                                                        () -> 0,
                                                        () -> 0,
                                                        () -> 1.0, // full velocity
                                                        () -> false // Robot Relative
                                                        )
                                                .withTimeout(1)
                                                .andThen(homeSystems().withTimeout(1))))
                .withName("MechanismsStandingCone");
    }

    /** Goes to 0 */
    public static Command homeSystems() {
        return FourBarCommands.home()
                .alongWith(ElevatorCommands.safeHome())
                .withName("MechanismsHomeSystems");
    }
}
