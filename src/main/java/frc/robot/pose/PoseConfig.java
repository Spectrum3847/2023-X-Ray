package frc.robot.pose;

public class PoseConfig {

    // Increase these numbers to trust your model's state estimates less.
    public final double kPositionStdDevX = 0.05;
    public final double kPositionStdDevY = 0.05;
    public final double kPositionStdDevTheta = 5;
    public final double kPositionStdDevModule = 0.05;

    // Increase these numbers to trust sensor readings from encoders and gyros less.
    public final double kRateStdDevTheta = 0.01;
    public final double kRateStdDevModule = 0.01;

    // Increase these numbers to trust global measurements from vision less.
    public final double kVisionStdDevX = 0.5;
    public final double kVisionStdDevY = 0.5;
    public final double kVisionStdDevTheta = 30;
}
