package frc.robot.intakeLauncher;

public class IntakeConfig {

    public double lowerDiameter = 0.8;
    public double frontDiameter = 2.125;
    public double launcherDiamter = 3;

    public double lowerGearRatio = 12 / 15;
    public double frontGearRatio = 18 / 22;
    public double launcherGearRatio = 18 / 12;

    public double falconMaxSpeed = 6380; // RPM

    public double lowerMaxSpeed = falconMaxSpeed;
    public double frontMaxSpeed = falconMaxSpeed;
    public double launcherMaxSpeed = falconMaxSpeed;

    // Speed settings
    public double lowerSlowSpeed = 0;
    public double frontSlowSpeed = 200;
    public double launcherSlowSpeed = -100;

    public double lowerIntakeSpeed = 5000;
    public double frontIntakeSpeed = 4000;
    public double launcherIntakeSpeed = -3000;

    public double lowerEjectSpeed = -6000;
    public double frontEjectSpeed = -6000;
    public double launcherEjectSpeed = 2000;

    public double lowerSpinUpSpeed = 200;

    public double lowerFeedSpeed = -6000;

    public double frontHybridSpeed = 800;
    public double launcherHybridSpeed = frontHybridSpeed;

    public double frontMidCubeSpeed = 1500;
    public double launcherMidCubeSpeed = frontMidCubeSpeed;

    public double frontTopCubeSpeed = 2600;
    public double launcherTopCubeSpeed = frontTopCubeSpeed;

    public double frontFullLaunchSpeed = frontMaxSpeed * 1.0;
    public double launcherFullLaunchSpeed = launcherMaxSpeed * 1.0;

    public double frontAutoMidSpeed = frontMaxSpeed;
    public double launcherAutoMidSpeed = launcherMaxSpeed;

    public double frontBumpTopSpeed = 4820;
    public double launcherBumpTopSpeed = 4820;

    // needs to be configed
    public double frontCommunityTopSpeed = 4820;
    public double launcherCommunityTopSpeed = 4820;

    public double frontBehindRStationMidSpeed = 4805;
    public double launcherBehindRStationMidSpeed = 4805;

    public double frontBehindMStationTopSpeed = 5200; // Not Calibrated
    public double launcherBehindMStationTopSpeed = 5200; // Not Calibrated

    public double currentLimit = 40;
    public double threshold = 40;

    public double velocityKp = 0.065;
    public double velocityKf = 0.0519;

    public IntakeConfig() {}
}
