package frc.robot.operator.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.commands.ElevatorCommands;
import frc.robot.fourbar.FourBar;
import frc.robot.fourbar.commands.FourBarCommands;
import frc.robot.fourbar.commands.FourBarDelay;
import frc.robot.intakeLauncher.commands.ConeIntake;
import frc.robot.intakeLauncher.commands.CubeIntake;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.OperatorConfig;

public class OperatorCommands {
    public static void setupDefaultCommand() {
        Robot.operatorGamepad.setDefaultCommand(
                rumble(0, 9999).repeatedly().withName("DisableOperatorRumble"));
    }

    /* Intaking Commands */

    public static Command coneShelfIntake() {
        return new ConeIntake(true)
                .alongWith(ElevatorCommands.coneShelf(), FourBarCommands.coneShelf())
                .withName("OperatorShelfCone")
                .finallyDo((b) -> homeSystems().withTimeout(1.5).schedule());
    }

    public static Command coneStandingIntake() {
        return new ConeIntake()
                .alongWith(
                        ElevatorCommands.coneStandingIntake(), FourBarCommands.coneStandingIntake())
                .withName("OperatorStandingCone")
                .finallyDo((b) -> homeSystems().withTimeout(1).schedule());
    }

    public static Command airIntake() {
        return IntakeCommands.airIntake().withName("OperatorAirIntake");
    }

    /* Position Commands */

    public static Command coneIntake() {
        return new ConeIntake()
                .alongWith(ElevatorCommands.coneIntake(), FourBarCommands.coneIntake())
                .withName("OperatorFloorCone")
                .finallyDo((b) -> finishConeIntake());
    }

    // Called by finally do, to let the intake hop up, and keep intaking for a bit after button
    // release
    public static void finishConeIntake() {
        IntakeCommands.intake()
                .alongWith(ElevatorCommands.hopElevator(), FourBarCommands.home())
                .withTimeout(0.75)
                .withName("OperatorFinishConeIntake")
                .schedule();
    }

    /* Move to coneFloor position and eject cone */
    public static Command coneFloorGoal() {
        return ElevatorCommands.coneFloorGoal()
                .alongWith(
                        FourBarCommands.coneFloorGoal(),
                        new WaitCommand(0.2).andThen(IntakeCommands.floorEject()))
                .finallyDo((b) -> homeSystems().withTimeout(1).schedule())
                .withName("OperatorConeFloorGoal");
    }

    public static Command coneMid() {
        return IntakeCommands.slowIntake()
                .alongWith(ElevatorCommands.coneMid(), FourBarCommands.coneMid())
                .withName("OperatorConeMid");
    }

    public static Command coneTop() {
        return IntakeCommands.slowIntake()
                .alongWith(
                        ElevatorCommands.coneTop(),
                        new FourBarDelay(
                                FourBar.config.safePositionForElevator,
                                FourBar.config.coneTop,
                                Elevator.config.safePositionForFourBar))
                .withName("OperatorConeTop");
    }

    public static Command cubeIntake() {
        return new CubeIntake()
                .alongWith(ElevatorCommands.cubeIntake(), FourBarCommands.cubeIntake())
                .withName("OperatorCubeIntake");
    }

    public static Command cubeFloorGoal() {
        return ElevatorCommands.cubeFloorGoal()
                .alongWith(FourBarCommands.cubeFloorGoal(), IntakeCommands.cubeFloorLaunch())
                .withName("OperatorCubeFloor")
                .finallyDo((b) -> homeSystems().withTimeout(1).schedule());
    }

    public static Command cubeMid() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(IntakeCommands.midCubeSpinUp().alongWith(ElevatorCommands.cubeMid()))
                .withName("OperatorCubeMid");
    }

    public static Command cubeTop() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(IntakeCommands.topCubeSpinUp().alongWith(ElevatorCommands.cubeTop()))
                .withName("OperatorCubeTop");
    }

    public static Command cubeChargeStation() {
        return IntakeCommands.intake()
                .withTimeout(0.1)
                .andThen(IntakeCommands.behindChargeStationSpinUp().alongWith(homeSystems()))
                .withName("OperatorCubeCS");
    }

    /** Sets Elevator and Fourbar to coast mode */
    public static Command coastMode() {
        return ElevatorCommands.coastMode()
                .alongWith(FourBarCommands.coastMode())
                .withName("OperatorCoastMode");
    }

    public static Command homeAndSlowIntake() {
        return IntakeCommands.slowIntake()
                .alongWith(homeSystems())
                .withName("OperatorSlowHomeIntake");
    }

    /** Goes to 0 */
    public static Command homeSystems() {
        return FourBarCommands.home()
                .alongWith(ElevatorCommands.safeHome())
                .withName("OperatorHomeSystems");
    }

    public static Command manualElevator() {
        return new RunCommand(
                        () ->
                                Robot.elevator.setManualOutput(
                                        Robot.operatorGamepad.elevatorManual()),
                        Robot.elevator)
                .withName("OperatorManualElevator");
    }

    public static Command slowManualElevator() {
        return new RunCommand(
                        () ->
                                Robot.elevator.setManualOutput(
                                        Robot.operatorGamepad.elevatorManual()
                                                * OperatorConfig.slowModeScalar),
                        Robot.elevator)
                .withName("OperatorManualSlowElevator");
    }

    public static Command manualFourBar() {
        return new RunCommand(
                        () -> Robot.fourBar.setManualOutput(Robot.operatorGamepad.fourBarManual()),
                        Robot.fourBar)
                .withName("OperatorManualFourBar");
    }

    public static Command slowManualFourBar() {
        return new RunCommand(
                        () ->
                                Robot.fourBar.setManualOutput(
                                        Robot.operatorGamepad.fourBarManual()
                                                * OperatorConfig.slowModeScalar),
                        Robot.fourBar)
                .withName("OperatorManualSlowFourBar");
    }

    /** Command that can be used to rumble the pilot controller */
    public static Command rumble(double intensity, double durationSeconds) {
        return new RunCommand(() -> Robot.operatorGamepad.rumble(intensity), Robot.operatorGamepad)
                .withTimeout(durationSeconds)
                .withName("OperatorRumble");
    }

    public static Command cancelCommands() {
        return new InstantCommand(() -> CommandScheduler.getInstance().cancelAll())
                .withName("OperatorCancelAll");
    }
}
