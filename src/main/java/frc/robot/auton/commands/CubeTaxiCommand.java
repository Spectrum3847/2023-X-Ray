//Created by Spectrum3847
package frc.robot.auton.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.operator.commands.OperatorCommands;
import frc.robot.swerve.commands.SwerveDrive;

//Wait 5 seconds before driving out of tarmac
public class CubeTaxiCommand extends ParallelCommandGroup {
  /** Creates a new TestPathFollowing. */
  public CubeTaxiCommand() {
    addCommands(
      OperatorCommands.cubeTop().withTimeout(0.2).andThen(IntakeCommands.launch()),
      new SwerveDrive(() -> -0.2, () -> 0.0, () -> 0.0, false).withTimeout(1.5)
    );
  }

  Rotation2d finalRotation() {
    return new Rotation2d(Math.PI);
  }
}