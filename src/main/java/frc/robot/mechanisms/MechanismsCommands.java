package frc.robot.mechanisms;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.fourbar.commands.FourBarDelay;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.intakeLauncher.commands.IntakeCommands;
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
                                                        () -> false // drive robot relative
                                                        )
                                                .withTimeout(1)
                                                .andThen(homeSystems().withTimeout(1))))
                .withName("MechanismsStandingCone");
    }

    public static Command cubeFloor() {
        return ElevatorCommands.cubeFloorGoal()
        .alongWith(FourBarCommands.cubeFloorGoal(), IntakeCommands.cubeFloorLaunch()).withTimeout(0.5).andThen(homeSystems())
        .withName("MechanismsCubeFloor");
    }

    public static Command cubeMid() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(IntakeCommands.midCubeSpinUp().alongWith(ElevatorCommands.cubeMid()))
                .withTimeout(1.5)
                .withName("MechanismsCubeMid");
    }

    public static Command cubeTop() {
        return IntakeCommands.intake()
        .withTimeout(0.1)
        .andThen(IntakeCommands.topCubeSpinUp().alongWith(ElevatorCommands.cubeTop())).withTimeout(1.5)
        .withName("MechanismsCubeTop");
    }

    public static Command coneMid() {
        return IntakeCommands.slowIntake()
        .alongWith(ElevatorCommands.coneMid(), FourBarCommands.coneMid()).withTimeout(1)
        .withName("MechanismsConeMid");
    }

    public static Command coneTop() {
        return IntakeCommands.slowIntake()
        .alongWith(
                ElevatorCommands.coneTop(),
                new FourBarDelay(
                        FourBar.config.safePositionForElevator,
                        FourBar.config.coneTop,
                        Elevator.config.safePositionForFourBar)).withTimeout(1.5)
        .withName("MechanismsConeTop");
    }
 
    public static Command launchAndRetract() {
        return IntakeCommands.autoLaunch()
                .withTimeout(0.2)
                .andThen(homeSystems())
                .withTimeout(0.5)
                .withName("MechanismsLaunch&Retract");
    }

    public static Command ejectAndRetract() {
        return IntakeCommands.autoEject().withTimeout(1.5).withName("MechanismsEject&Retract");
    }

    /** Goes to 0 */
    public static Command homeSystems() {
        return FourBarCommands.home()
                .alongWith(ElevatorCommands.safeHome())
                .withName("MechanismsHomeSystems");
    }
}
