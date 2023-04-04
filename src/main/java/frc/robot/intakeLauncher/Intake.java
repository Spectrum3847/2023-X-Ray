package frc.robot.intakeLauncher;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotConfig;

public class Intake extends SubsystemBase {
    public static IntakeConfig config = new IntakeConfig();
    public IntakeTelemetry telemetry;
    public IntakeMotor lowerMotor;
    public IntakeMotor frontMotor;
    public IntakeMotor launcherMotor;
    private DigitalInput cubeSensor;

    public Intake() {
        super();
        lowerMotor =
                new IntakeMotor(
                        config, RobotConfig.Motors.lowerRoller, TalonFXInvertType.Clockwise);
        frontMotor =
                new IntakeMotor(
                        config, RobotConfig.Motors.frontRoller, TalonFXInvertType.Clockwise);
        launcherMotor =
                new IntakeMotor(
                        config, RobotConfig.Motors.launcher, TalonFXInvertType.CounterClockwise);
        ;

        cubeSensor = new DigitalInput(0);
        telemetry = new IntakeTelemetry(this);
    }

    public boolean getCubeSensor() {
        return !cubeSensor.get();
    }

    public void setCurrentLimits(double limit, double threshold) {
        lowerMotor.setCurrentLimit(limit, threshold);
        frontMotor.setCurrentLimit(limit, threshold);
        launcherMotor.setCurrentLimit(limit, threshold);
    }

    public void setVelocities(double lower, double front, double launcher) {
        lowerMotor.setVelocity(lower);
        frontMotor.setVelocity(front);
        launcherMotor.setVelocity(launcher);
    }

    public void setPercentOutputs(double lower, double front, double launcher) {
        lowerMotor.setPercent(lower);
        frontMotor.setPercent(front);
        launcherMotor.setPercent(launcher);
    }

    public void launch() {
        lowerMotor.setVelocity(config.lowerFeedSpeed);
        frontMotor.setVelocity(frontMotor.setpoint);
        launcherMotor.setVelocity(launcherMotor.setpoint);
    }

    public void stopAll() {
        lowerMotor.stop();
        frontMotor.stop();
        launcherMotor.stop();
    }

    public double getFrontCurrent() {
        return frontMotor.getCurrent();
    }

    public double getFrontRPM() {
        return frontMotor.getVelocity();
    }
}
