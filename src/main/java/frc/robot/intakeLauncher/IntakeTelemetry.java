package frc.robot.intakeLauncher;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeTelemetry {

    protected ShuffleboardTab tab;

    @SuppressWarnings("unused")
    private Intake intake;

    boolean intailized = false;

    public IntakeTelemetry(Intake intake) {
        this.intake = intake;

        SmartDashboard.putNumber("launchValue", 10);
        tab = Shuffleboard.getTab("Intake");
        tab.addNumber("Launcher Velocity", () -> intake.launcherMotor.getVelocity())
                .withPosition(0, 0);
        tab.addNumber("Front Velocity", () -> intake.frontMotor.getVelocity()).withPosition(0, 1);
        tab.addNumber("Lower Velocity", () -> intake.lowerMotor.getVelocity()).withPosition(0, 2);
        tab.add("Set Launch Velocity", "double", Intake.config.frontBumpTopSpeed)
                .withPosition(0, 3);
    }

    public void testMode() {
        if (!intailized) {

            intailized = true;
        }
    }
}
