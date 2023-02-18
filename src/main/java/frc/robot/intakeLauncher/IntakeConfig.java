package frc.robot.intakeLauncher;

public class IntakeConfig {

    public double lowerDiameter = 0.8;
    public double frontDiameter = 2.125;
    public double launcherDiamter = 3;

    public double lowerGearRatio = 12 / 15;
    public double frontGearRatio = 18 / 22;
    public double launcherGearRatio = 18 / 12;

    public double falconMaxSpeed = 6380; // RPM

    public double lowerMaxSpeed = falconMaxSpeed; // * lowerGearRatio * lowerDiameter * Math.PI;
    public double frontMaxSpeed = falconMaxSpeed; // * frontGearRatio * frontDiameter * Math.PI;
    public double launcherMaxSpeed =
            falconMaxSpeed; // * launcherGearRatio * launcherDiamter * Math.PI;

    // Speed settings
    public double lowerIntakeSpeed = 6000; // lowerMaxSpeed * 0.85;
    public double frontIntakeSpeed = 5000; // frontMaxSpeed * 0.85;
    public double launcherIntakeSpeed = -500;

    public double lowerEjectSpeed = -4000;
    public double frontEjectSpeed = -5000;
    public double launcherEjectSpeed = 2000;

    public double lowerSpinUpSpeed = 800;

    public double lowerFeedSpeed = -3000;
    public double frontMidCubeSpeed = 3000;
    public double launcherMidCubeSpeed = frontMidCubeSpeed;

    public double frontTopCubeSpeed = 4000;
    public double launcherTopCubeSpeed = frontTopCubeSpeed;

    public double frontHybridSpeed = 2000;
    public double launcherHybridSpeed = frontHybridSpeed;

    public double frontFullLaunchSpeed = frontMaxSpeed * 1.0;
    public double launcherFullLaunchSpeed = launcherMaxSpeed * 1.0;

    public double frontAutoMidSpeed = frontMaxSpeed * 0.9;
    public double launcherAutoMidSpeed = launcherMaxSpeed * 0.9;

    public double velocityKp = 0.065;
    public double velocityKf = 0.0519;

    public IntakeConfig() {}
}
