package frc.robot.intakeLauncher;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.SpectrumLib.util.Conversions;

public class IntakeMotor {
    public WPI_TalonFX motor;
    IntakeConfig config;
    double setpoint = 0;

    public IntakeMotor(IntakeConfig config, int id, TalonFXInvertType inverted) {
        this.config = config;
        this.motor = new WPI_TalonFX(id);
        motor.configFactoryDefault();

        SupplyCurrentLimitConfiguration currentLimit =
                new SupplyCurrentLimitConfiguration(
                        true, Intake.config.currentLimit, Intake.config.threshold, 0);
        motor.configSupplyCurrentLimit(currentLimit);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        motor.setInverted(inverted);
        motor.setNeutralMode(NeutralMode.Brake);
        motor.configVoltageCompSaturation(12);
        motor.enableVoltageCompensation(true);

        motor.config_kP(0, config.velocityKp);
        motor.config_kF(0, config.velocityKf);
    }

    public void setCurrentLimit(double limit, double threshold) {
        SupplyCurrentLimitConfiguration currentLimit =
                new SupplyCurrentLimitConfiguration(true, limit, threshold, 0);
        motor.configSupplyCurrentLimit(currentLimit);
    }

    public void setVelocity(double speedRPM) {
        setpoint = speedRPM;
        double speedFalcon = Conversions.RPMToFalcon(speedRPM, 1);
        setFalconSpeed(speedFalcon);
    }

    private void setFalconSpeed(double speedFalcon) {
        motor.selectProfileSlot(0, 0);
        motor.set(TalonFXControlMode.Velocity, speedFalcon);
    }

    public double getVelocity() {
        return Conversions.falconToRPM(motor.getSelectedSensorVelocity(), 1);
    }

    public double getPosition() {
        return motor.getSelectedSensorPosition();
    }

    public void setPercent(double percent) {
        motor.set(TalonFXControlMode.PercentOutput, percent);
    }

    public void stop() {
        motor.stopMotor();
    }

    public double getCurrent() {
        return motor.getSupplyCurrent();
    }
}
