// Created by Spectrum3847
package frc.robot.swerve;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.SpectrumLib.swerve.SwerveModuleConfig;
import frc.robot.RobotConfig.Motors;

public final class SwerveConfig {

    public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(18.75); // (17.5); // (18.75); 2022
    public static final double wheelBase = Units.inchesToMeters(21.75); // (20.5); // (21.75); 2022
    public static final double wheelDiameter = Units.inchesToMeters(3.8195);
    public static final double wheelCircumference = wheelDiameter * Math.PI;

    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    public static final double driveGearRatio = (6.75 / 1); // (8.16 / 1.0); // 6.75:1
    public static final double angleGearRatio =
            (150 / 7); // (12.8 / 1.0); // (50.0 / 14.0) * (60.0 / 10.0); //12.8:1

    public static final Translation2d frontLeftLocation =
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0);
    public static final Translation2d frontRightLocation =
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0);
    public static final Translation2d backLeftLocation =
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0);
    public static final Translation2d backRightLocation =
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0);

    public static final SwerveDriveKinematics swerveKinematics =
            new SwerveDriveKinematics(
                    frontLeftLocation, frontRightLocation, backLeftLocation, backRightLocation);

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 20;
    public static final int anglePeakCurrentLimit = 30;
    public static final double anglePeakCurrentDuration = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 40;
    public static final int drivePeakCurrentLimit = 40;
    public static final double drivePeakCurrentDuration = 0.0;
    public static final boolean driveEnableCurrentLimit = true;

    /* Angle Motor PID Values */
    public static final double angleKP = 0.6; // 364 = 0.6; SDS = 0.2;
    public static final double angleKI = 0.0;
    public static final double angleKD = 12; // 364 = 12.0; SDS = 0.1;
    public static final double angleKF = 0.0;
    public static final int angleAllowableError =
            0; // increase to reduce jitter, (2048 * angleGearRatio) / 360.0) = 1 degree

    /* Drive Motor PID Values */
    public static final double driveKP = 0.1;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values */
    public static final double driveKS =
            (0.605 / 12); // /12 to convert from volts to %output for CTRE
    public static final double driveKV = (1.72 / 12);
    public static final double driveKA = (0.193 / 12);

    /* Swerve Profiling Values */
    public static final double maxVelocity =
            ((6380 / 60) / driveGearRatio) * wheelDiameter * Math.PI * 0.95; // meters per second
    public static final double maxAccel = maxVelocity * 1.5; // take 1/2 sec to get to max speed.
    public static final double maxAngularVelocity =
            maxVelocity / Math.hypot(trackWidth / 2.0, wheelBase / 2.0);
    public static final double maxAngularAcceleration = Math.pow(maxAngularVelocity, 2);

    /* Neutral Modes */
    public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
    public static final NeutralMode driveNeutralMode = NeutralMode.Coast;

    /* Motor Inverts */
    public static final boolean driveMotorInvert = true; // True = MK4i
    public static final boolean angleMotorInvert = true; // True = MK4i

    /* Angle Encoder Invert */
    public static final boolean canCoderInvert = false;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 {
        public static final int driveMotorID = Motors.driveMotor0;
        public static final int angleMotorID = Motors.angleMotor0;
        public static final int canCoderID = 3;
        public static final double angleOffsetC = 193.007812;
        public static final double angleOffsetP = 0;
        public static double angleOffset = angleOffsetC;
        public static final SwerveModuleConfig config =
                new SwerveModuleConfig(
                        driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
        public static final int driveMotorID = Motors.driveMotor1;
        public static final int angleMotorID = Motors.angleMotor1;
        public static final int canCoderID = 13;
        public static final double angleOffsetC = 171.5625;
        public static final double angleOffsetP = 0;
        public static double angleOffset = angleOffsetC;
        public static final SwerveModuleConfig config =
                new SwerveModuleConfig(
                        driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }

    /* Back Left Module - Module 2 */
    public static final class Mod2 {
        public static final int driveMotorID = Motors.driveMotor2;
        public static final int angleMotorID = Motors.angleMotor2;
        public static final int canCoderID = 23;
        public static final double angleOffsetC = 254.6191;
        public static final double angleOffsetP = 0;
        public static double angleOffset = angleOffsetC;
        public static final SwerveModuleConfig config =
                new SwerveModuleConfig(
                        driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
        public static final int driveMotorID = Motors.driveMotor3;
        public static final int angleMotorID = Motors.angleMotor3;
        public static final int canCoderID = 33;
        public static final double angleOffsetC = 307.3535;
        public static final double angleOffsetP = 0;
        public static double angleOffset = angleOffsetC;
        public static final SwerveModuleConfig config =
                new SwerveModuleConfig(
                        driveMotorID, angleMotorID, canCoderID, angleOffset, angleOffsetP);;
    }

    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;

    public SwerveConfig() {
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit =
                new SupplyCurrentLimitConfiguration(
                        SwerveConfig.angleEnableCurrentLimit,
                        SwerveConfig.angleContinuousCurrentLimit,
                        SwerveConfig.anglePeakCurrentLimit,
                        SwerveConfig.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = SwerveConfig.angleKP;
        swerveAngleFXConfig.slot0.kI = SwerveConfig.angleKI;
        swerveAngleFXConfig.slot0.kD = SwerveConfig.angleKD;
        swerveAngleFXConfig.slot0.kF = SwerveConfig.angleKF;
        swerveAngleFXConfig.slot0.allowableClosedloopError = SwerveConfig.angleAllowableError;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
        swerveAngleFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit =
                new SupplyCurrentLimitConfiguration(
                        SwerveConfig.driveEnableCurrentLimit,
                        SwerveConfig.driveContinuousCurrentLimit,
                        SwerveConfig.drivePeakCurrentLimit,
                        SwerveConfig.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = SwerveConfig.driveKP;
        swerveDriveFXConfig.slot0.kI = SwerveConfig.driveKI;
        swerveDriveFXConfig.slot0.kD = SwerveConfig.driveKD;
        swerveDriveFXConfig.slot0.kF = SwerveConfig.driveKF;
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
        swerveDriveFXConfig.openloopRamp = SwerveConfig.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = SwerveConfig.closedLoopRamp;

        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = SwerveConfig.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy =
                SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }

    public static Translation2d[] moduleOffsets(double meters) {
        return moduleOffsets(new Translation2d(meters, meters));
    }

    public static Translation2d[] moduleOffsets(Translation2d frontLeft) {
        // ++ +- -+ --
        Translation2d fl = frontLeftLocation.plus(frontLeft);
        Translation2d fr =
                frontRightLocation.plus(new Translation2d(frontLeft.getX(), -frontLeft.getY()));
        Translation2d bl =
                frontRightLocation.plus(new Translation2d(-frontLeft.getX(), frontLeft.getY()));
        Translation2d br =
                frontRightLocation.plus(new Translation2d(-frontLeft.getX(), -frontLeft.getY()));
        Translation2d a[] = {fl, fr, bl, br};
        return a;
    }
}
