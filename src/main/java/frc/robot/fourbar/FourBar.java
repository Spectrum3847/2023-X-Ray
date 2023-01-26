package frc.robot.FourBar;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.SpectrumLib.subsystems.angleMech.AngleMechConfig;
import frc.SpectrumLib.subsystems.angleMech.AngleMechSubsystem;
import frc.robot.RobotConfig;

public class FourBar extends AngleMechSubsystem {
    public static AngleMechConfig config = new FourBarConfig();

    public FourBar() {
        super(config);
        motorLeader = new WPI_TalonFX(RobotConfig.Motors.fourBarMotor);
        setupFalconLeader();
        motorLeader.setInverted(TalonFXInvertType.Clockwise);
        motorLeader.configSupplyCurrentLimit(config.supplyLimit);
    }
}
