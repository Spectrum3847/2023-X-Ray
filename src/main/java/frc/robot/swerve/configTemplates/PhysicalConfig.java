package frc.robot.swerve.configTemplates;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * Physical Configuration of the swerve base. This includes the wheelbase and track width. This also
 * includes everything that is common to all modules.
 */
public class PhysicalConfig {
    public final AngleSensorType angleSensorType;
    public final double trackWidth;
    public final double wheelBase;
    public final double wheelDiameter;
    public final double driveGearRatio;
    public final double angleGearRatio;
    public final boolean driveMotorInvert;
    public final boolean angleMotorInvert;
    public final boolean angleSensorInvert;

    public final double wheelCircumference;
    public final Translation2d frontLeftLocation;
    public final Translation2d frontRightLocation;
    public final Translation2d backLeftLocation;
    public final Translation2d backRightLocation;

    public static final double swerveMetersPerPulse = 0.00002226;

    public static enum AngleSensorType {
        CANCoder,
        ThriftyEncoder
    }

    public PhysicalConfig(
            double trackWidth,
            double wheelBase,
            double wheelDiameter,
            double driveGearRatio,
            double angleGearRatio,
            boolean driveMotorInvert,
            boolean angleMotorInvert,
            boolean angleSensorInvert,
            AngleSensorType angleSensorType) {
        this.trackWidth = trackWidth;
        this.wheelBase = wheelBase;
        this.wheelDiameter = wheelDiameter;
        this.driveGearRatio = driveGearRatio;
        this.angleGearRatio = angleGearRatio;
        this.driveMotorInvert = driveMotorInvert;
        this.angleMotorInvert = angleMotorInvert;
        this.angleSensorInvert = angleSensorInvert;
        this.angleSensorType = angleSensorType;

        wheelCircumference = wheelDiameter * Math.PI;

        frontLeftLocation = new Translation2d(wheelBase / 2.0, trackWidth / 2.0);
        frontRightLocation = new Translation2d(wheelBase / 2.0, -trackWidth / 2.0);
        backLeftLocation = new Translation2d(-wheelBase / 2.0, trackWidth / 2.0);
        backRightLocation = new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0);
    }

    /**
     * Return offsets from each module from the center of the robot. The order is FL, FR, BL, BR.
     * This can let you get the outer corner of the frame at each corner, etc.
     *
     * @param meters
     * @return
     */
    public Translation2d[] moduleOffsets(double meters) {
        return moduleOffsets(new Translation2d(meters, meters));
    }

    public Translation2d[] moduleOffsets(Translation2d frontLeft) {
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
