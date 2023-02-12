// Created by Spectrum3847

// Based on Code from Team364 - BaseFalconSwerve
// https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.swerve;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveConfig config;
    protected Gyro gyro;
    public Odometry odometry;
    public SwerveTelemetry telemetry;
    public SwerveModule[] mSwerveMods;
    private SwerveModuleState[] mSwerveModStates;

    public Swerve() {
        setName("Swerve");
        config = new SwerveConfig();
        gyro = new Gyro();

        mSwerveMods =
                new SwerveModule[] {
                    new SwerveModule(0, config, SwerveConfig.Mod0.config),
                    new SwerveModule(1, config, SwerveConfig.Mod1.config),
                    new SwerveModule(2, config, SwerveConfig.Mod2.config),
                    new SwerveModule(3, config, SwerveConfig.Mod3.config)
                };
        resetSteeringToAbsolute();
        odometry = new Odometry(this);
        telemetry = new SwerveTelemetry(this);
    }

    @Override
    public void periodic() {
        odometry.update();
        mSwerveModStates = getStatesCAN(); // Get the states once a loop
        telemetry.logModuleStates("SwerveModuleStates/Measured", mSwerveModStates);
        telemetry.logModuleAbsolutePositions();
    }

    public void drive(
            double fwdPositive,
            double leftPositive,
            double rotationRadiansPS,
            boolean fieldRelative,
            boolean isOpenLoop) {
        drive(
                fwdPositive,
                leftPositive,
                rotationRadiansPS,
                fieldRelative,
                isOpenLoop,
                new Translation2d());
    }

    /**
     * Used to drive the swerve robot, should be called from commands that require swerve.
     *
     * @param fwdPositive Velocity of the robot fwd/rev, Forward Positive meters per second
     * @param leftPositive Velocity of the robot left/right, Left Positive meters per secound
     * @param omegaRadiansPerSecond Rotation Radians per second
     * @param fieldRelative If the robot should drive in field relative
     * @param isOpenLoop If the robot should drive in open loop
     * @param centerOfRotationMeters The center of rotation in meters
     */
    public void drive(
            double fwdPositive,
            double leftPositive,
            double omegaRadiansPerSecond,
            boolean fieldRelative,
            boolean isOpenLoop,
            Translation2d centerOfRotationMeters) {

        ChassisSpeeds speeds;
        if (fieldRelative) {
            speeds =
                    ChassisSpeeds.fromFieldRelativeSpeeds(
                            fwdPositive, leftPositive, omegaRadiansPerSecond, getHeading());
        } else {
            speeds = new ChassisSpeeds(fwdPositive, leftPositive, omegaRadiansPerSecond);
        }

        SwerveModuleState[] swerveModuleStates =
                SwerveConfig.swerveKinematics.toSwerveModuleStates(speeds, centerOfRotationMeters);

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, SwerveConfig.maxVelocity);

        telemetry.logModuleStates("SwerveModuleStates/Desired", swerveModuleStates);
        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    /** Reset AngleMotors to Absolute This is used to reset the angle motors to absolute position */
    public void resetSteeringToAbsolute() {
        for (SwerveModule mod : mSwerveMods) {
            mod.resetToAbsolute();
        }
    }

    public Rotation2d getHeading() {
        return odometry.getHeading();
    }

    public Pose2d getPoseMeters() {
        return odometry.getPoseMeters();
    }

    /**
     * Used by SwerveFollowCommand in Auto, assumes closed loop control
     *
     * @param desiredStates Meters per second and radians per second
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConfig.maxVelocity);

        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    public void brakeMode(boolean enabled) {
        for (SwerveModule mod : mSwerveMods) {
            if (enabled) {
                mod.mDriveMotor.setNeutralMode(NeutralMode.Brake);
            } else {
                mod.mDriveMotor.setNeutralMode(NeutralMode.Coast);
            }
        }
    }

    public void stop() {
        for (SwerveModule mod : mSwerveMods) {
            mod.mDriveMotor.stopMotor();
            mod.mAngleMotor.stopMotor();
        }
    }

    private SwerveModuleState[] getStatesCAN() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (SwerveModule mod : mSwerveMods) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModuleState[] getStates() {
        return mSwerveModStates;
    }

    public SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (SwerveModule mod : mSwerveMods) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        mSwerveMods[0].mDriveMotor.setVoltage(leftVolts);
        mSwerveMods[2].mDriveMotor.setVoltage(leftVolts);
        mSwerveMods[1].mDriveMotor.setVoltage(rightVolts);
        mSwerveMods[3].mDriveMotor.setVoltage(rightVolts);
    }
}
