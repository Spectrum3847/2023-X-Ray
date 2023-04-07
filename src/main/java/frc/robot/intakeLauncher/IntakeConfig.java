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
    public double lowerSlowSpeed = 200;
    public double frontSlowSpeed = 200;
    public double launcherSlowSpeed = -100;

    public double lowerIntakeSpeed = 3000;
    public double frontIntakeSpeed = 4000;
    public double launcherIntakeSpeed = -3500;

    public double lowerEjectSpeed = -6000;
    public double frontEjectSpeed = -6000;
    public double launcherEjectSpeed = 2000;

    public double lowerFloorSpeed = -4000;
    public double frontFloorSpeed = -4000;
    public double launcherFloorSpeed = launcherEjectSpeed;

    public double lowerSpinUpSpeed = 100;

    public double lowerFeedSpeed = -2000;

    public double frontHybridSpeed = 800;
    public double launcherHybridSpeed = frontHybridSpeed;

    public double frontMidCubeSpeed = 1300;
    public double launcherMidCubeSpeed = frontMidCubeSpeed;

    public double frontTopCubeSpeed = 2150;
    public double launcherTopCubeSpeed = 2000;

    public double frontChargeStationLaunchSpeed = 1000;
    public double launcherChargeStationLaunchSpeed = 5000;

    public double frontAutoMidSpeed = frontMaxSpeed;
    public double launcherAutoMidSpeed = launcherMaxSpeed;

    public double lowerBumpTopSpeed = lowerSpinUpSpeed;
    public double frontBumpTopSpeed = 4820;
    public double launcherBumpTopSpeed = 4820;

    public double frontFirstShotSpeed =
            5370 * (.82); // correct 4820 wrong //3800 was a little too far //3650 was slightly
    // too far
    // //3600 slighty too short //3700 too much
    public double launcherFirstShotSpeed =
            6250 * (.82); // correct //3800 was a little too far //3650 was slighty too far
    // //3600 slighty
    // too short

    public double frontSecondShotSpeed =
            900 * (1.5); // correct w/distance = .135 from tape line and .18 // 4000 slightly too short
    public double launcherSecondShotSpeed =
            2800 * (1.5); // correct w/distance = .135 from tape line and .18 // 4000 slightly too short

            public double frontCleanShotSpeed =
            900; // correct w/distance = .135 from tape line and .18 // 4000 slightly too short
    public double launcherCleanShotSpeed =
            2800; // correct w/distance = .135 from tape line and .18 // 4000 slightly too short

    public double frontAngleThirdShotSpeed =
            900 * (2.25); // correct w/distance = .135 from tape line and .18 // 4000 slightly
    // too short
    public double launcherAngleThirdShotSpeed =
            4000 * (1.8); // correct w/distance = .135 from tape line and .18 // 4000 slightly
    // too short

    public double frontThirdShotBalanceSpeed =
            3400*(1.5); // correct when touching charge station with .5 sec spin up time
    public double launcherThirdShotBalanceSpeed =
            5000*(1.5); // correct when touching charge station with .5 sec spin up time

    public double currentLimit = 20;
    public double threshold = 40;

    public double velocityKp = 0.065;
    public double velocityKf = 0.0519;

    public IntakeConfig() {}
}
