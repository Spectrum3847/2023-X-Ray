package frc.robot.leds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
    public LEDConfig config;
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    public final LEDScheduler scheduler;

    public enum LEDStripStatus {
        OFF,
        ON
    }

    public LEDStripStatus stripStatus;

    public LEDs() {
        config = new LEDConfig();

        ledStrip = new AddressableLED(config.ADDRESSABLE_LED);
        ledBuffer = new AddressableLEDBuffer(config.LED_COUNT);

        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);
        ledStrip.start();

        stripStatus = LEDStripStatus.ON;

        scheduler = new LEDScheduler(this);
    }

    @Override
    public void periodic() {}

    public void setHSV(int i, int hue, int saturation, int value) {
        ledBuffer.setHSV(i, hue, saturation, value);
    }

    public void setRGB(int i, int red, int green, int blue) {
        ledBuffer.setRGB(i, red, green, blue);
    }

    public int getBufferLength() {
        return ledBuffer.getLength();
    }

    public void sendData() {
        ledStrip.setData(ledBuffer);
    }

    public void stopLEDStrip() {
        ledStrip.stop();
        stripStatus = LEDStripStatus.OFF;
    }

    public void startLEDStrip() {
        ledStrip.start();
        stripStatus = LEDStripStatus.ON;
    }
}
