package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class PoseTelemetry {
    Pose pose;
    Field2d field = new Field2d();

    public PoseTelemetry(Pose pose) {
        this.pose = pose;

        createField();
    }

    private void createField() {
        SmartDashboard.putData("Field", field);
    }

    public void updatePoseOnField(String name, Pose2d pose) {
        field.getObject(name).setPose(pose);
        Robot.log.logger.recordOutput(name, pose);
    }

    /**
     * get the current field2d object
     *
     * @return field2d object
     */
    public Field2d getField() {
        return field;
    }

    public void testMode() {}
}
