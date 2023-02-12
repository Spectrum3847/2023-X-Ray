package frc.robot.swerve.configTemplates;

public class GyroConfig {
    public final GyroType type;
    public final int port;

    public static enum GyroType {
        PIGEON1,
        PIGEON2,
        ADI
    }

    public GyroConfig(GyroType type, int port) {
        this.type = type;
        this.port = port;
    }
}
