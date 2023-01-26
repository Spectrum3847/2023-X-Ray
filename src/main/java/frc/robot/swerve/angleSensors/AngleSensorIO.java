package frc.robot.swerve.angleSensors;

import edu.wpi.first.math.geometry.Rotation2d;
import java.util.function.Supplier;

public abstract class AngleSensorIO implements Supplier<Rotation2d> {

    public abstract Rotation2d getPosition();
}
