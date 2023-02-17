package frc.robot.intakeLauncher;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.SpectrumLib.util.Conversions;
import frc.robot.RobotConfig;

public class Intake extends SubsystemBase {
    public static IntakeConfig config = new IntakeConfig();
    int lowerRollerID = RobotConfig.Motors.lowerRoller;
    int frontRollerID = RobotConfig.Motors.frontRoller;
    int launcherID = RobotConfig.Motors.launcher;
    WPI_TalonFX lowerRollerMotor;
    WPI_TalonFX frontRollerMotor;
    WPI_TalonFX launcherMotor;

    public Intake() {
        super();
        lowerRollerMotor = new WPI_TalonFX(lowerRollerID);
        frontRollerMotor = new WPI_TalonFX(frontRollerID);
        launcherMotor = new WPI_TalonFX(launcherID);

        SupplyCurrentLimitConfiguration currentLimit =
                new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        lowerRollerMotor.configSupplyCurrentLimit(currentLimit);
        frontRollerMotor.configSupplyCurrentLimit(currentLimit);
        launcherMotor.configSupplyCurrentLimit(currentLimit);
        lowerRollerMotor.setInverted(TalonFXInvertType.Clockwise);
        frontRollerMotor.setInverted(TalonFXInvertType.Clockwise);
        launcherMotor.setInverted(TalonFXInvertType.CounterClockwise);
        lowerRollerMotor.setNeutralMode(NeutralMode.Brake);
        frontRollerMotor.setNeutralMode(NeutralMode.Brake);
        launcherMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void setPIDF() {
        lowerRollerMotor.config_kP(0, config.lowerSpeedKp);
        lowerRollerMotor.config_kF(0, config.lowerSpeedKf);
        frontRollerMotor.config_kP(0, config.frontSpeedKp);
        frontRollerMotor.config_kF(0, config.frontSpeekKf);
        launcherMotor.config_kP(0, config.launcherSpeedKp);
        launcherMotor.config_kF(0, config.launcherSpeedKf);

        lowerRollerMotor.config_kP(1, config.lowerPositionKp);
        lowerRollerMotor.config_kF(1, config.lowerPositionKf);
        frontRollerMotor.config_kP(1, config.frontPositionKp);
        frontRollerMotor.config_kF(1, config.frontPositionKf);
        launcherMotor.config_kP(1, config.launcherPositionKp);
        launcherMotor.config_kF(1, config.launcherPositionKf);
    }

    public void setRollers(double lower, double front, double launcher) {
        setLowerRoller(lower);
        setFrontRoller(front);
        setLauncher(launcher);
    }

    public void setLowerSpeed(double speedRPM) {
        double speedFalcon = Conversions.RPMToFalcon(speedRPM, config.lowerGearRatio);
        lowerRollerMotor.selectProfileSlot(0, 0);
        lowerRollerMotor.set(TalonFXControlMode.Velocity, speedFalcon);
    }

    public void setFrontSpeed(double speedRPM) {
        double speedFalcon = Conversions.RPMToFalcon(speedRPM, config.frontGearRatio);
        frontRollerMotor.selectProfileSlot(0, 0);
        frontRollerMotor.set(TalonFXControlMode.Velocity, speedFalcon);
    }

    public void setLauncherSpeed(double speedRPM) {
        double speedFalcon = Conversions.RPMToFalcon(speedRPM, config.launcherGearRatio);
        launcherMotor.selectProfileSlot(0, 0);
        launcherMotor.set(TalonFXControlMode.Velocity, speedFalcon);
    }

    public void setRollerSpeeds(double lower, double front, double launcher) {
        setLowerSpeed(lower);
        setFrontSpeed(front);
        setLauncherSpeed(launcher);
    }

    public void holdLowerPosition() {
        lowerRollerMotor.selectProfileSlot(1, 0);
        lowerRollerMotor.set(
                TalonFXControlMode.Position, lowerRollerMotor.getSelectedSensorPosition());
    }

    public void holdFrontPosition() {
        frontRollerMotor.selectProfileSlot(1, 0);
        frontRollerMotor.set(
                TalonFXControlMode.Position, frontRollerMotor.getSelectedSensorPosition());
    }

    public void holdLauncherPosition() {
        launcherMotor.selectProfileSlot(1, 0);
        launcherMotor.set(TalonFXControlMode.Position, launcherMotor.getSelectedSensorPosition());
    }

    public void holdRollerPositions() {
        holdLowerPosition();
        holdFrontPosition();
        holdLauncherPosition();
    }

    public void setLowerRoller(double speed) {
        lowerRollerMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setFrontRoller(double speed) {
        frontRollerMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setLauncher(double speed) {
        launcherMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void stopLowerRoller() {
        lowerRollerMotor.stopMotor();
    }

    public void stopFrontRoller() {
        frontRollerMotor.stopMotor();
    }

    public void stopLauncher() {
        launcherMotor.stopMotor();
    }

    public void stopAll() {
        stopLowerRoller();
        stopFrontRoller();
        stopLauncher();
    }
}
