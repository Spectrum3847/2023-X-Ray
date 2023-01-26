package frc.robot.FourBar;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import frc.SpectrumLib.subsystems.angleMech.AngleMechConfig;

public class FourBarConfig extends AngleMechConfig {

    /* Current Limiting */
    public int currentLimit = 10;
    public int tirggerThresholdLimit = 10;
    public double PeakCurrentDuration = 0.0;
    public boolean EnableCurrentLimit = true;
    public SupplyCurrentLimitConfiguration supplyLimit =
            new SupplyCurrentLimitConfiguration(
                    EnableCurrentLimit, currentLimit, tirggerThresholdLimit, PeakCurrentDuration);

    public boolean kInverted = true;

    public final int minAngle = 0;
    public final int maxAngle = 90;

    // Physical Constants
    public final double pulleyDiameterInches = 2;
    public final double pulleyDiameterMeters = pulleyDiameterInches * 0.0254;

    public final double gearRatio = 1;

    public final double pulleyCircumferenceMeters = pulleyDiameterMeters * Math.PI;
    public final double pulleyCircumferenceInches = pulleyDiameterInches * Math.PI;

    public FourBarConfig() {
        super("FourBar");
        updateTalonFXConfig();
    }
}
