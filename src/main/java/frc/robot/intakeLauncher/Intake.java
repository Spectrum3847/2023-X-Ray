package frc.robot.intakeLauncher;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotConfig;

public class Intake extends SubsystemBase {
    public static IntakeConfig config = new IntakeConfig();
    int lowerRollerID = RobotConfig.Motors.lowerIntake;
    int midRollerID = RobotConfig.Motors.midIntake;
    int launcherID = RobotConfig.Motors.launcher;
    WPI_TalonFX lowerRollerMotor;
    WPI_TalonFX midRollerMotor;
    WPI_TalonFX launcherMotor;

    public Intake() {
        super();
        lowerRollerMotor = new WPI_TalonFX(lowerRollerID);
        midRollerMotor = new WPI_TalonFX(midRollerID);
        launcherMotor = new WPI_TalonFX(launcherID);

        SupplyCurrentLimitConfiguration currentLimit =
                new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        lowerRollerMotor.configSupplyCurrentLimit(currentLimit);
        midRollerMotor.configSupplyCurrentLimit(currentLimit);
        launcherMotor.configSupplyCurrentLimit(currentLimit);
        lowerRollerMotor.setInverted(TalonFXInvertType.Clockwise);
        midRollerMotor.setInverted(TalonFXInvertType.Clockwise);
        launcherMotor.setInverted(TalonFXInvertType.CounterClockwise);
        lowerRollerMotor.setNeutralMode(NeutralMode.Brake);
        midRollerMotor.setNeutralMode(NeutralMode.Brake);
        launcherMotor.setNeutralMode(NeutralMode.Brake);
        config.updateTalonFXConfig();
        // launcherMotor.setInverted(true);
    }

    public void setRollers(double lower, double upper, double launcher) {
        setLowerRoller(lower);
        setUpperRoller(upper);
        setLauncher(launcher);
    }

    public void setLowerRoller(double speed) {
        lowerRollerMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setUpperRoller(double speed) {
        midRollerMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setLauncher(double speed) {
        launcherMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void stopLowerRoller() {
        lowerRollerMotor.stopMotor();
    }

    public void stopUpperRoller() {
        midRollerMotor.stopMotor();
    }

    public void stopLauncher() {
        launcherMotor.stopMotor();
    }

    public void stopAll() {
        stopLowerRoller();
        stopUpperRoller();
        stopLauncher();
    }
}
