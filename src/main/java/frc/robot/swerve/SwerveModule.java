// Based on Code from Team364 - BaseFalconSwerve
// https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.SpectrumLib.swerve.CTREModuleState;
import frc.SpectrumLib.util.Conversions;
import frc.robot.swerve.angleSensors.AngleSensorIO;
import frc.robot.swerve.angleSensors.CanCoder;
import frc.robot.swerve.angleSensors.ThriftyEncoder;
import frc.robot.swerve.configTemplates.ModuleConfig;
import frc.robot.swerve.configTemplates.SwerveConfig;

public class SwerveModule extends SubsystemBase {
    public int moduleNumber;
    public WPI_TalonFX mAngleMotor;
    public WPI_TalonFX mDriveMotor;
    private SwerveConfig config;
    private ModuleConfig moduleConfig;
    private double angleOffset;
    private AngleSensorIO angleEncoder;
    private Rotation2d lastAngle;
    private SimpleMotorFeedforward feedforward;

    private SwerveModuleState mSwerveModState = new SwerveModuleState();
    private SwerveModulePosition mSwerveModPosition = new SwerveModulePosition();
    private Rotation2d mAbsoluteAngle = new Rotation2d();

    public SwerveModule(int moduleNumber, SwerveConfig config) {
        this.moduleNumber = moduleNumber;
        this.config = config;
        moduleConfig = config.modules[moduleNumber];
        angleOffset = moduleConfig.angleOffset;
        feedforward =
                new SimpleMotorFeedforward(
                        config.tuning.driveKS, config.tuning.driveKV, config.tuning.driveKA);

        /* Angle Encoder Config */
        switch (config.physical.angleSensorType) {
            case CANCoder:
                angleEncoder = new CanCoder(moduleConfig.absAngleSensorID, moduleConfig.canBus);
                break;
            case ThriftyEncoder:
                angleEncoder = new ThriftyEncoder(moduleConfig.absAngleSensorID);
                break;
        }

        // Must do this before you configure angle motor correctly
        mAbsoluteAngle = checkAbsoluteAngle();

        /* Angle Motor Config */
        mAngleMotor = new WPI_TalonFX(moduleConfig.angleMotorID, moduleConfig.canBus);
        configAngleMotor();

        /* Drive Motor Config */
        mDriveMotor = new WPI_TalonFX(moduleConfig.driveMotorID, moduleConfig.canBus);
        configDriveMotor();

        mSwerveModState = getCANState();
        mSwerveModPosition = getCANPosition();
        lastAngle = checkFalconAngle();
    }

    @Override
    public void periodic() {
        mSwerveModState = getCANState();
        mSwerveModPosition = getCANPosition();
        mAbsoluteAngle = checkAbsoluteAngle();
    }

    /**
     * Set motors to desired speed using state
     *
     * @param desiredState
     * @param isOpenLoop - if true, use percent output for motors, if false, use velocity *
     */
    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        // Custom optimize command, since default WPILib optimize assumes continuous controller
        // which CTRE is not
        desiredState = CTREModuleState.optimize(desiredState, getState().angle);
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    public void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / config.tuning.maxVelocity;
            mDriveMotor.set(ControlMode.PercentOutput, percentOutput);
        } else {
            double velocity =
                    Conversions.MPSToFalcon(
                            desiredState.speedMetersPerSecond,
                            config.physical.wheelCircumference,
                            config.physical.driveGearRatio);
            mDriveMotor.set(
                    ControlMode.Velocity,
                    velocity,
                    DemandType.ArbitraryFeedForward,
                    feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    public void setAngle(SwerveModuleState desiredState) {
        // Prevent rotating module if speed is less then 1%
        // Prevents Jittering.
        Rotation2d angle = desiredState.angle;

        if ((Math.abs(desiredState.speedMetersPerSecond) < (config.tuning.maxVelocity * 0.01))) {
            angle = lastAngle;
        }

        mAngleMotor.set(
                ControlMode.Position,
                Conversions.degreesToFalcon(angle.getDegrees(), config.physical.angleGearRatio));
        lastAngle = angle;
    }

    public double makePositiveDegrees(double anAngle) {
        double degrees = anAngle;
        degrees = degrees % 360;
        if (degrees < 0.0) {
            degrees = degrees + 360;
        }
        return degrees;
    }

    public void resetToAbsolute() {
        double absolutePosition =
                Conversions.degreesToFalcon(
                        makePositiveDegrees(getAbsoluteAngle().getDegrees()) - angleOffset,
                        config.physical.angleGearRatio);
        mAngleMotor.setSelectedSensorPosition(absolutePosition);

        // Testing this
        lastAngle =
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                absolutePosition, config.physical.angleGearRatio));
    }

    public void setLastAngletoCurrentAngle() {
        double currentFalconAngle = mAngleMotor.getSelectedSensorPosition();
        lastAngle =
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                currentFalconAngle, config.physical.angleGearRatio));
    }

    private void configAngleMotor() {
        mAngleMotor.configFactoryDefault();
        mAngleMotor.configAllSettings(moduleConfig.swerveAngleFXConfig);
        mAngleMotor.setInverted(config.physical.angleMotorInvert);
        mAngleMotor.setNeutralMode(config.tuning.angleNeutralMode);
        resetToAbsolute();
    }

    private void configDriveMotor() {
        mDriveMotor.configFactoryDefault();
        mDriveMotor.configAllSettings(moduleConfig.swerveDriveFXConfig);
        mDriveMotor.setInverted(config.physical.driveMotorInvert);
        mDriveMotor.setNeutralMode(config.tuning.driveNeutralMode);
        mDriveMotor.setSelectedSensorPosition(0);
    }

    private Rotation2d checkAbsoluteAngle() {
        return angleEncoder.get();
    }

    public Rotation2d getAbsoluteAngle() {
        return mAbsoluteAngle;
    }

    public Rotation2d getTargetAngle() {
        return lastAngle;
    }

    public Rotation2d checkFalconAngle() {
        return Rotation2d.fromDegrees(
                Conversions.falconToDegrees(
                        mAngleMotor.getSelectedSensorPosition(), config.physical.angleGearRatio));
    }

    private SwerveModuleState getCANState() {
        double velocity =
                Conversions.falconToMPS(
                        mDriveMotor.getSelectedSensorVelocity(),
                        config.physical.wheelCircumference,
                        config.physical.driveGearRatio);
        Rotation2d angle =
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                mAngleMotor.getSelectedSensorPosition(),
                                config.physical.angleGearRatio));
        return new SwerveModuleState(velocity, angle);
    }

    public SwerveModuleState getState() {
        return mSwerveModState;
    }

    private SwerveModulePosition getCANPosition() {
        double position =
                Conversions.FalconToMeters(
                        mDriveMotor.getSelectedSensorPosition(),
                        config.physical.wheelCircumference,
                        config.physical.driveGearRatio);
        return new SwerveModulePosition(position, mSwerveModState.angle);
    }

    public SwerveModulePosition getPosition() {
        return mSwerveModPosition;
    }

    public void setBrakeMode(Boolean enabled) {
        if (enabled) {
            mDriveMotor.setNeutralMode(NeutralMode.Brake);
            mAngleMotor.setNeutralMode(NeutralMode.Brake);
        } else {
            mDriveMotor.setNeutralMode(NeutralMode.Coast);
            mAngleMotor.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void stop() {
        mDriveMotor.stopMotor();
        mAngleMotor.stopMotor();
    }

    public void setVoltage(double voltage) {
        mDriveMotor.setVoltage(voltage);
    }
}
