package frc.robot.intakeLauncher;

public class IntakeConfig {

    double lowerDiameter = 0.8;
    double frontDiameter = 2.125;
    double launcherDiamter = 3;

    double lowerGearRatio = 12 / 15;
    double frontGearRatio = 18 / 22;
    double launcherGearRatio = 18 / 12;

    double falconMaxSpeed = 6380; // RPM

    double lowerMaxSpeed = falconMaxSpeed * lowerGearRatio * lowerDiameter * Math.PI;
    double frontMaxSpeed = falconMaxSpeed * frontGearRatio * frontDiameter * Math.PI;
    double launcherMaxSpeed = falconMaxSpeed * launcherGearRatio * launcherDiamter * Math.PI;

    // Speed settings
    double lowerIntakeSpeed = lowerMaxSpeed * 0.85;
    double frontIntakeSpeed = frontMaxSpeed * 0.85;
    double launcherIntakeSpeed = launcherMaxSpeed * -0.1;

    double lowerEjectSpeed = lowerMaxSpeed * -0.5;
    double frontEjectSpeed = frontMaxSpeed * -0.5;
    double launcherEjectSpeed = launcherMaxSpeed * 0.5;

    double lowerSpinUpSpeed = lowerMaxSpeed * 0.1;

    double lowerFeedSpeed = lowerMaxSpeed * -0.5;
    double frontMidCubeSpeed = frontMaxSpeed * 0.6;
    double launcherMidCubeSpeed = launcherMaxSpeed * 0.5;

    double frontTopCubeSpeed = frontMaxSpeed * 0.8;
    double launcherTopCubeSpeed = launcherMaxSpeed * 0.8;

    double frontHybridSpeed = frontMaxSpeed * 0.3;
    double launcherHybridSpeed = launcherMaxSpeed * 0.3;

    double frontFullLaunchSpeed = frontMaxSpeed * 1.0;
    double launcherFullLaunchSpeed = launcherMaxSpeed * 1.0;

    double frontAutoMidSpeed = frontMaxSpeed * 0.9;
    double launcherAutoMidSpeed = launcherMaxSpeed * 0.9;

    double lowerSpeedKp = 0.065;
    double lowerSpeedKf = 0.0519;

    double frontSpeedKp = 0.065;
    double frontSpeekKf = 0.0519;

    double launcherSpeedKp = 0.065;
    double launcherSpeedKf = 0.0519;

    double lowerPositionKp = 0.5;
    double lowerPositionKf = 0.5;

    double frontPositionKp = 0.5;
    double frontPositionKf = 0.5;

    double launcherPositionKp = 0.5;
    double launcherPositionKf = 0.5;

    public IntakeConfig() {}
}
