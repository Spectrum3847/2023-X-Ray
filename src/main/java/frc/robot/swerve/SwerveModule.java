// Based on Code from Team364 - BaseFalconSwerve
// https://github.com/Team364/BaseFalconSwerve/tree/338c0278cb63714a617f1601a6b9648c64ee78d1

package frc.robot.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.SpectrumLib.swerve.CTREModuleState;
import frc.SpectrumLib.swerve.SwerveModuleConfig;
import frc.SpectrumLib.util.Conversions;
import frc.robot.Robot;
import frc.robot.RobotConfig.RobotType;

public class SwerveModule {
    public int moduleNumber;
    private double angleOffset;
    public WPI_TalonFX mAngleMotor;
    public WPI_TalonFX mDriveMotor;
    private WPI_CANCoder angleEncoder;
    private Rotation2d lastAngle;
    private SwerveConfig swerveConfig;

    SimpleMotorFeedforward feedforward =
            new SimpleMotorFeedforward(
                    SwerveConfig.driveKS, SwerveConfig.driveKV, SwerveConfig.driveKA);

    public SwerveModule(
            int moduleNumber, SwerveConfig swerveConfig, SwerveModuleConfig moduleConfig) {
        this.moduleNumber = moduleNumber;
        this.swerveConfig = swerveConfig;
        if (Robot.config.getRobotType() == RobotType.PRACTICE) {
            angleOffset = moduleConfig.angleOffset;
        } else {
            angleOffset = moduleConfig.angleOffset;
        }

        /* Angle Encoder Config */
        angleEncoder = new WPI_CANCoder(moduleConfig.cancoderID);
        configAngleEncoder();

        /* Angle Motor Config */
        mAngleMotor = new WPI_TalonFX(moduleConfig.angleMotorID);
        configAngleMotor();

        /* Drive Motor Config */
        mDriveMotor = new WPI_TalonFX(moduleConfig.driveMotorID);
        configDriveMotor();

        lastAngle = getState().angle;
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

        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / SwerveConfig.maxVelocity;
            mDriveMotor.set(ControlMode.PercentOutput, percentOutput);
        } else {
            double velocity =
                    Conversions.MPSToFalcon(
                            desiredState.speedMetersPerSecond,
                            SwerveConfig.wheelCircumference,
                            SwerveConfig.driveGearRatio);
            mDriveMotor.set(
                    ControlMode.Velocity,
                    velocity,
                    DemandType.ArbitraryFeedForward,
                    feedforward.calculate(desiredState.speedMetersPerSecond));
        }

        // Prevent rotating module if speed is less then 1%
        // Prevents Jittering.
        Rotation2d angle = desiredState.angle;

        if ((Math.abs(desiredState.speedMetersPerSecond) < (SwerveConfig.maxVelocity * 0.01))) {
            angle = lastAngle;
        }

        mAngleMotor.set(
                ControlMode.Position,
                Conversions.degreesToFalcon(angle.getDegrees(), SwerveConfig.angleGearRatio));
        lastAngle = angle;
    }

    public void resetToAbsolute() {
        double offset = angleOffset;
        double absolutePosition =
                Conversions.degreesToFalcon(
                        getCanCoderAngle().getDegrees() - offset, SwerveConfig.angleGearRatio);
        mAngleMotor.setSelectedSensorPosition(absolutePosition);
    }

    private void configAngleEncoder() {
        angleEncoder.configFactoryDefault();
        angleEncoder.configAllSettings(swerveConfig.swerveCanCoderConfig);
        angleEncoder.setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 249);
        angleEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 20);
    }

    private void configAngleMotor() {
        mAngleMotor.configFactoryDefault();
        mAngleMotor.configAllSettings(swerveConfig.swerveAngleFXConfig);
        mAngleMotor.setInverted(SwerveConfig.angleMotorInvert);
        mAngleMotor.setNeutralMode(SwerveConfig.angleNeutralMode);
        resetToAbsolute();
    }

    private void configDriveMotor() {
        mDriveMotor.configFactoryDefault();
        mDriveMotor.configAllSettings(swerveConfig.swerveDriveFXConfig);
        mDriveMotor.setInverted(SwerveConfig.driveMotorInvert);
        mDriveMotor.setNeutralMode(SwerveConfig.driveNeutralMode);
        mDriveMotor.setSelectedSensorPosition(0);
    }

    public Rotation2d getCanCoderAngle() {
        Rotation2d position = Rotation2d.fromDegrees(angleEncoder.getAbsolutePosition());
        return position;
    }

    public Rotation2d getTargetAngle() {
        return lastAngle;
    }

    public SwerveModuleState getState() {
        double velocity =
                Conversions.falconToMPS(
                        mDriveMotor.getSelectedSensorVelocity(),
                        SwerveConfig.wheelCircumference,
                        SwerveConfig.driveGearRatio);
        Rotation2d angle =
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                mAngleMotor.getSelectedSensorPosition(),
                                SwerveConfig.angleGearRatio));
        return new SwerveModuleState(velocity, angle);
    }

    public SwerveModulePosition getPosition() {
        double position =
                Conversions.FalconToMeters(
                        mDriveMotor.getSelectedSensorPosition(),
                        SwerveConfig.wheelCircumference,
                        SwerveConfig.driveGearRatio);
        Rotation2d angle =
                Rotation2d.fromDegrees(
                        Conversions.falconToDegrees(
                                mAngleMotor.getSelectedSensorPosition(),
                                SwerveConfig.angleGearRatio));
        return new SwerveModulePosition(position, angle);
    }
}
