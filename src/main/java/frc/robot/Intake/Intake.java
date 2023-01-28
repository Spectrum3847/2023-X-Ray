package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    int lowerRoller = 43;
    int upperRoller = 44;
    int launcher = 42;
    WPI_VictorSPX lowerRollerMotor;
    WPI_VictorSPX upperRollerMotor;
    WPI_VictorSPX launcherMotor;

    public Intake() {
        super();
        lowerRollerMotor = new WPI_VictorSPX(lowerRoller);
        upperRollerMotor = new WPI_VictorSPX(upperRoller);
        launcherMotor = new WPI_VictorSPX(launcher);
        // launcherMotor.setInverted(true);
    }

    public void setRollers(double lower, double upper, double launcher) {
        setLowerRoller(lower);
        setUpperRoller(upper);
        setLauncher(launcher);
    }

    public void setLowerRoller(double speed) {
        lowerRollerMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void setUpperRoller(double speed) {
        upperRollerMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    public void setLauncher(double speed) {
        launcherMotor.set(VictorSPXControlMode.PercentOutput, speed);
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
