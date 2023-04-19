package frc.robot.pilot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.SpectrumLib.gamepads.AxisButton;
import frc.SpectrumLib.gamepads.AxisButton.ThresholdType;
import frc.SpectrumLib.gamepads.Gamepad;
import frc.SpectrumLib.gamepads.XboxGamepad.XboxAxis;
import frc.robot.Robot;
import frc.robot.intakeLauncher.commands.IntakeCommands;
import frc.robot.leds.commands.OneColorLEDCommand;
import frc.robot.pilot.commands.PilotCommands;
import frc.robot.swerve.commands.AlignToAprilTag;
import frc.robot.trajectories.commands.DistanceDrive;

/** Used to add buttons to the pilot gamepad and configure the joysticks */
public class PilotGamepad extends Gamepad {

    Trigger rightX = AxisButton.create(gamepad, XboxAxis.RIGHT_X, 0.5, ThresholdType.DEADBAND);
    Trigger rightY = AxisButton.create(gamepad, XboxAxis.RIGHT_Y, 0.5, ThresholdType.DEADBAND);

    Trigger leftX =
            AxisButton.create(
                    gamepad, XboxAxis.LEFT_X, PilotConfig.throttleDeadband, ThresholdType.DEADBAND);
    Trigger leftY =
            AxisButton.create(
                    gamepad, XboxAxis.LEFT_Y, PilotConfig.throttleDeadband, ThresholdType.DEADBAND);

    Trigger leftTrigger =
            AxisButton.create(
                    gamepad,
                    XboxAxis.LEFT_TRIGGER,
                    PilotConfig.steeringDeadband,
                    ThresholdType.DEADBAND);
    Trigger rightTrigger =
            AxisButton.create(
                    gamepad,
                    XboxAxis.RIGHT_TRIGGER,
                    PilotConfig.steeringDeadband,
                    ThresholdType.DEADBAND);

    public PilotGamepad() {
        super("PILOT", PilotConfig.port);
        gamepad.leftStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.leftStick.configCurves(
                PilotConfig.throttleExp,
                PilotConfig.throttleScaler * Robot.swerve.config.tuning.maxVelocity);
        gamepad.leftStick.setXinvert(PilotConfig.xInvert);
        gamepad.leftStick.setYinvert(PilotConfig.yInvert);

        gamepad.rightStick.setDeadband(PilotConfig.throttleDeadband);
        gamepad.rightStick.configCurves(
                PilotConfig.steeringExp,
                PilotConfig.steeringScaler * Robot.swerve.config.tuning.maxAngularVelocity);
        gamepad.rightStick.setXinvert(PilotConfig.xInvert);
        gamepad.rightStick.setYinvert(PilotConfig.yInvert);

        gamepad.triggers.setTwistDeadband(PilotConfig.steeringDeadband);
        gamepad.triggers.configTwistCurve(
                PilotConfig.steeringExp,
                PilotConfig.steeringScaler * Robot.swerve.config.tuning.maxAngularVelocity);
        gamepad.triggers.setTwistInvert(PilotConfig.steeringInvert);
    }

    public void setupTeleopButtons() {
        /* Drive */
        stickSteerTriggers();
        triggerSteering();
        // driveTrigger();

        /* Aiming */
        gamepad.xButton.and(noBumpers()).whileTrue(PilotCommands.aimPilotDrive(Math.PI));
        gamepad.bButton.whileTrue(IntakeCommands.launch());

        /* Dpad */
        gamepad.Dpad.Up.and(noBumpers().or(rightBumperOnly())).whileTrue(IntakeCommands.launch());
        gamepad.Dpad.Down.and(noBumpers().or(rightBumperOnly())).whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.and(noBumpers()).whileTrue(new DistanceDrive(Units.inchesToMeters(5)));
        gamepad.Dpad.Right.and(noBumpers()).whileTrue(new DistanceDrive(Units.inchesToMeters(-5)));

        /* Aligning */
        rightBumperOnly()
                .whileTrue(new AlignToAprilTag(() -> Robot.pilotGamepad.getDriveFwdPositive(), 0));
        // rightBumperOnly().whileTrue(new DriveToCubeNode(0));
        rightBumperOnly()
                .and(rightTrigger)
                .whileTrue(
                        new AlignToAprilTag(
                                () -> Robot.pilotGamepad.getDriveFwdPositive(),
                                PilotConfig.alignmentOffset));
        rightBumperOnly()
                .and(leftTrigger)
                .whileTrue(
                        new AlignToAprilTag(
                                () -> Robot.pilotGamepad.getDriveFwdPositive(),
                                -PilotConfig.alignmentOffset));

        /* Reorient */
        gamepad.Dpad.Up.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(0));
        gamepad.Dpad.Left.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(90));
        gamepad.Dpad.Down.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(180));
        gamepad.Dpad.Right.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(270));

        /* Start and Select */
        gamepad.startButton.whileTrue(PilotCommands.resetSteering());
        gamepad.selectButton.whileTrue(PilotCommands.lockSwerve());
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kWhite, "White", 5));
        gamepad.startButton.whileTrue(PilotCommands.resetSteering());
    }

    public void setupTestButtons() {}

    public double getPilotScalar() {
        return Robot.pilotGamepad.slowModeButton().getAsBoolean()
                ? PilotConfig.slowModeScaler
                : 1.0;
    }

    private Trigger noBumpers() {
        return gamepad.rightBumper.negate().and(gamepad.leftBumper.negate());
    }

    private Trigger noTriggers() {
        return leftTrigger.negate().and(rightTrigger.negate());
    }

    private Trigger leftBumperOnly() {
        return gamepad.leftBumper.and(gamepad.rightBumper.negate());
    }

    private Trigger rightBumperOnly() {
        return gamepad.rightBumper.and(gamepad.leftBumper.negate());
    }

    private Trigger bothBumpers() {
        return gamepad.rightBumper.and(gamepad.leftBumper);
    }

    private Trigger bothTriggers() {
        return gamepad.rightTriggerButton.and(gamepad.leftTriggerButton);
    }

    private Trigger leftGrid() {
        return leftBumperOnly();
    }

    private Trigger rightGrid() {
        return rightBumperOnly();
    }

    private Trigger middleGrid() {
        return bothBumpers();
    }

    public Trigger slowModeButton() {
        return gamepad.aButton.and(noBumpers());
    }

    public Trigger fpvButton() {
        return gamepad.yButton.and(noBumpers());
    }

    public double getDriveFwdPositive() {
        double fwdPositive = gamepad.leftStick.getY();
        return fwdPositive;
    }

    public double getDriveLeftPositive() {
        double leftPositive = gamepad.leftStick.getX();
        return leftPositive;
    }

    // Positive is counter-clockwise, left Trigger is positive
    public double getDriveCCWPositive() {
        double ccwPositive = gamepad.triggers.getTwist();
        return ccwPositive;
    }

    // Return the angle created by the left stick in radians, 0 is up, pi/2 is left
    public Double getDriveAngle() {
        return gamepad.leftStick.getDirectionRadians(getDriveFwdPositive(), getDriveLeftPositive());
    }

    // Return the angle created by the right stick in radians, 0 is up, pi/2 is left
    public double getRightStickAngle() {
        return gamepad.rightStick.getDirectionRadians(
                gamepad.rightStick.getY(), gamepad.rightStick.getX());
    }

    public double getRightStickCardinals() {
        double stickAngle = getRightStickAngle();
        if (stickAngle > -Math.PI / 4 && stickAngle <= Math.PI / 4) {
            return 0;
        } else if (stickAngle > Math.PI / 4 && stickAngle <= 3 * Math.PI / 4) {
            return Math.PI / 2;
        } else if (stickAngle > 3 * Math.PI / 4 || stickAngle <= -3 * Math.PI / 4) {
            return Math.PI;
        } else {
            return -Math.PI / 2;
        }
    }

    private void triggerSteering() {
        leftTrigger.or(rightTrigger).and(noBumpers()).whileTrue(PilotCommands.pilotSwerve());
    }

    private void stickSteerTriggers() {
        // Right Stick points the robot in that direction, when bumpers aren't pressed
        (rightX.or(rightY)).and(noBumpers()).whileTrue(PilotCommands.stickSteer());
    }

    public double getLeftYRaw() {
        return gamepad.getRawAxis(1) * -1;
    }

    public double getLeftXRaw() {
        return gamepad.getRawAxis(0) * -1;
    }

    public void rumble(double intensity) {
        this.gamepad.setRumble(intensity, intensity);
    }
}
