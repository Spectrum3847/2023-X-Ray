package frc.robot.pilot;

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
import frc.robot.swerve.commands.LockSwerve;
import frc.robot.swerve.commands.SwerveCommands;

/** Used to add buttons to the pilot gamepad and configure the joysticks */
public class PilotGamepad extends Gamepad {
    // Trigger canUseAutoPilot =
    //         new Trigger(() -> Robot.vision.canUseAutoPilot && !Robot.pose.isOnChargeStation());

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
        // canUseAutoPilot =
        //         new Trigger(() -> Robot.vision.canUseAutoPilot &&
        // !Robot.pose.isOnChargeStation());

        fpvButton().and(noBumpers()).whileTrue(PilotCommands.fpvPilotSwerve()); // X and Not A
        slowModeButton().and(noBumpers()).whileTrue(PilotCommands.slowMode()); // A and not X
        slowFpvButton().and(noBumpers()).whileTrue(PilotCommands.slowModeFPV()); // A and X

        // gamepad.yButton.and(noBumpers()).whileTrue(); Y IS FREE
        // gamepad.yButton.and(noBumpers()).whileTrue(OperatorCommands.coneTop());

        // gamepad.bButton.and(noBumpers()).whileTrue(OperatorCommands.homeAndSlowIntake());
        gamepad.bButton.and(noBumpers()).whileTrue(PilotCommands.reorientToGrid(0));

        gamepad.xButton.whileTrue(PilotCommands.reorientToGrid(Math.PI));

        // gamepad.xButton.whileTrue(new BehindBalanceCommand());

        // gamepad.yButton.whileTrue(PilotCommands.reorientToGrid(0));

        /* Will not run if canUseAutoPilot condition is not met */
        // leftGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid1Left());
        // leftGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid1Middle());
        // leftGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid1Right());
        // middleGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid2Left());
        // middleGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid2Middle());
        // middleGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid2Right());
        // rightGrid().and(gamepad.xButton).whileTrue(PositionPaths.grid3Left());
        // rightGrid().and(gamepad.aButton).whileTrue(PositionPaths.grid3Middle());
        // rightGrid().and(gamepad.bButton).whileTrue(PositionPaths.grid3Right());

        // hybrid aiming
        // rightGrid().and(gamepad.rightTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(0));
        // rightGrid().and(bothTriggers()).whileTrue(VisionCommands.aimToHybridSpot(1));
        // rightGrid().and(gamepad.leftTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(2));
        // middleGrid().and(gamepad.rightTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(3));
        // middleGrid().and(bothTriggers()).whileTrue(VisionCommands.aimToHybridSpot(4));
        // middleGrid().and(gamepad.leftTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(5));
        // leftGrid().and(gamepad.rightTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(6));
        // leftGrid().and(bothTriggers()).whileTrue(VisionCommands.aimToHybridSpot(7));
        // leftGrid().and(gamepad.leftTriggerButton).whileTrue(VisionCommands.aimToHybridSpot(8));

        // Stick steer when the right stick is moved passed 0.5 and bumpers aren't pressed
        // driveTrigger();
        stickSteerTriggers();
        triggerSteering();

        gamepad.Dpad.Up.and(noBumpers()).whileTrue(IntakeCommands.launch());
        gamepad.Dpad.Down.and(noBumpers()).whileTrue(IntakeCommands.eject());
        gamepad.Dpad.Left.and(noBumpers()).whileTrue(new LockSwerve());
        // Right is free

        // Reorient the robot to the current heading, reset swerve ot absolute sensors, and rumble
        // controller
        gamepad.Dpad.Up.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(0));
        gamepad.Dpad.Left.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(90));
        gamepad.Dpad.Down.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(180));
        gamepad.Dpad.Right.and(leftBumperOnly()).whileTrue(PilotCommands.reorient(270));

        // Start and Select Buttons
        gamepad.startButton.whileTrue(
                SwerveCommands
                        .resetSteeringToAbsolute()); // Reset steering if a falcon is being weird
        // gamepad.selectButton.whileTrue():
    }

    public void setupDisabledButtons() {
        gamepad.aButton.whileTrue(new OneColorLEDCommand(Color.kWhite, "White", 5));
    }

    public void setupTestButtons() {}

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

    private Trigger slowModeButton() {
        return gamepad.aButton.and(gamepad.xButton.negate());
    }

    private Trigger fpvButton() {
        return gamepad.yButton.and(gamepad.aButton.negate());
    }

    private Trigger slowFpvButton() {
        return gamepad.yButton.and(gamepad.aButton);
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

    private void driveTrigger() {
        leftX.or(leftY)
                .and(noBumpers())
                .and(noTriggers())
                .whileTrue(PilotCommands.pilotHeadingLock());
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
