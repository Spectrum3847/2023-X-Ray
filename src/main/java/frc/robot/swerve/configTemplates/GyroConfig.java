package frc.robot.swerve.configTemplates;

public class GyroConfig {
    public final GyroType type;
    public final int port;
    public final String canBus;

    public static enum GyroType {
        PIGEON1,
        PIGEON2,
        ADI
    }

    public GyroConfig(GyroType type, int port) {
        this.type = type;
        this.port = port;
        this.canBus = "rio";
    }

    public GyroConfig(GyroType type, int port, String canBus) {
        this.type = type;
        this.port = port;
        this.canBus = canBus;
    }
}
