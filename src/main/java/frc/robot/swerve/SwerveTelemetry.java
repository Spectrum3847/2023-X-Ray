package frc.robot.swerve;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import frc.robot.Robot;
import java.util.Map;

public class SwerveTelemetry {
    protected ShuffleboardTab tab;
    private Swerve swerve;

    public SwerveTelemetry(Swerve swerve) {
        this.swerve = swerve;
        tab = Shuffleboard.getTab("Swerve");
        tab.addNumber("Heading", () -> swerve.getHeading().getDegrees()).withPosition(0, 0);
        tab.addNumber("Odometry X", () -> swerve.getPoseMeters().getX()).withPosition(0, 1);
        tab.addNumber("Odometry Y", () -> swerve.getPoseMeters().getY()).withPosition(0, 2);
        /*tab.addNumber(
                        "Mod 0 Can Angle",
                        () -> Robot.swerve.mSwerveMods[0].getCanCoderAngle().getDegrees())
                .withPosition(1, 0);
        tab.addNumber(
                        "Mod 1 Can Angle",
                        () -> Robot.swerve.mSwerveMods[1].getCanCoderAngle().getDegrees())
                .withPosition(2, 0);
        tab.addNumber(
                        "Mod 2 Can Angle",
                        () -> Robot.swerve.mSwerveMods[2].getCanCoderAngle().getDegrees())
                .withPosition(3, 0);
        tab.addNumber(
                        "Mod 3 Can Angle",
                        () -> Robot.swerve.mSwerveMods[3].getCanCoderAngle().getDegrees())
                .withPosition(4, 0);
        tab.addNumber(
                        "Mod 0 Integrated Angle",
                        () -> Robot.swerve.mSwerveMods[0].getState().angle.getDegrees())
                .withPosition(1, 1);
        tab.addNumber(
                        "Mod 1 Integrated Angle",
                        () -> Robot.swerve.mSwerveMods[1].getState().angle.getDegrees())
                .withPosition(2, 1);
        tab.addNumber(
                        "Mod 2 Integrated Angle",
                        () -> Robot.swerve.mSwerveMods[2].getState().angle.getDegrees())
                .withPosition(3, 1);
        tab.addNumber(
                        "Mod 3 Integrated Angle",
                        () -> Robot.swerve.mSwerveMods[3].getState().angle.getDegrees())
                .withPosition(4, 1);*/
    }

    public void testMode() {
        moduleLayout("Mod 0", 0, tab).withPosition(1, 0);
        moduleLayout("Mod 1", 1, tab).withPosition(2, 0);
        moduleLayout("Mod 2", 2, tab).withPosition(3, 0);
        moduleLayout("Mod 3", 3, tab).withPosition(4, 0);
    }

    public void logModuleStates(String key, SwerveModuleState[] states) {
        Robot.log.logger.recordOutput(key, states);
    }

    public void logModuleAbsolutePositions() {
        for (SwerveModule mod : swerve.mSwerveMods) {
            Robot.log.logger.recordOutput(
                    "Mod " + mod.moduleNumber + " Absolute", mod.getCanCoderAngle().getDegrees());
        }
    }

    public void logVisionPose() {
        Robot.log.logger.recordOutput("VisionPose", Robot.vision.botPose);
    }

    public ShuffleboardLayout moduleLayout(String name, int moduleNum, ShuffleboardTab tab) {
        ShuffleboardLayout modLayout = tab.getLayout(name, BuiltInLayouts.kGrid);
        // m_mod0Layout.withSize(1, 2);
        modLayout.withProperties(Map.of("Label position", "TOP"));

        // mod0 Cancoder Angle
        SuppliedValueWidget<Double> mod0CancoderAngleWidget =
                modLayout.addNumber(
                        "Cancoder Angle",
                        () -> Robot.swerve.mSwerveMods[moduleNum].getCanCoderAngle().getDegrees());
        mod0CancoderAngleWidget.withPosition(0, 0);

        // mod0 Integrated Angle
        SuppliedValueWidget<Double> mod0IntegratedAngleWidget =
                modLayout.addNumber(
                        "Integrated Angle",
                        () -> Robot.swerve.mSwerveMods[moduleNum].getState().angle.getDegrees());
        mod0IntegratedAngleWidget.withPosition(0, 1);

        // mod0 Velocity
        SuppliedValueWidget<Double> mod0VelocityWidget =
                modLayout.addNumber(
                        "Wheel Velocity",
                        () -> Robot.swerve.mSwerveMods[moduleNum].getState().speedMetersPerSecond);
        mod0VelocityWidget.withPosition(0, 2);
        return modLayout;
    }
}
