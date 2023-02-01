package frc.robot.intakeLauncher;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    int lowerRoller = 43;
    int upperRoller = 44;
    int launcher = 42;
    WPI_TalonFX lowerRollerMotor;
    WPI_TalonFX upperRollerMotor;
    WPI_TalonFX launcherMotor;

    public Intake() {
        super();
        lowerRollerMotor = new WPI_TalonFX(lowerRoller);
        upperRollerMotor = new WPI_TalonFX(upperRoller);
        launcherMotor = new WPI_TalonFX(launcher);

        SupplyCurrentLimitConfiguration currentLimit =
                new SupplyCurrentLimitConfiguration(true, 40, 40, 0);
        lowerRollerMotor.configSupplyCurrentLimit(currentLimit);
        upperRollerMotor.configSupplyCurrentLimit(currentLimit);
        launcherMotor.configSupplyCurrentLimit(currentLimit);
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
        upperRollerMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setLauncher(double speed) {
        launcherMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void stopLowerRoller() {
        lowerRollerMotor.stopMotor();
    }

    public void stopUpperRoller() {
        upperRollerMotor.stopMotor();
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
