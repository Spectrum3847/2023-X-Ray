package frc.robot.fourbar;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.SpectrumLib.subsystems.angleMech.AngleMechSubsystem;
import frc.robot.RobotConfig;

public class FourBar extends AngleMechSubsystem {
    public static FourBarConfig config = new FourBarConfig();

    public FourBar() {
        super(config);
        motorLeader = new WPI_TalonFX(RobotConfig.Motors.fourBarMotor);
        setupFalconLeader();
        motorLeader.setInverted(TalonFXInvertType.Clockwise);
        motorLeader.configSupplyCurrentLimit(config.supplyLimit);
    }
}
