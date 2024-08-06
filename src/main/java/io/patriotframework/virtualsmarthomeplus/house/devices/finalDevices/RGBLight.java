package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriot_framework.generator.device.passive.actuators.stateMachine.StateMachine;
import io.patriotframework.virtualsmarthomeplus.Actuators.RGBActuator;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Actuator;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RGBLight extends Actuator {

    public static final String OFF = "Off";
    public static final String ON = "On";
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);

    /**
     * Creates new RGBLight with given label and sets intensity of RGB light to zero.
     *
     * @param label label creates identity of the RGBLight and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    @JsonCreator
    public RGBLight(String label) {
        super(label);
        device = new RGBActuator(label);
        setRGB(0, 0, 0);
        ((RGBActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from("Off")
                        .to("On", "TurnOn")
                        .from("On")
                        .to("Off", "TurnOff")
                        .build()
        );
    }

    /**
     * Creates new RGBLight with given label.
     * Color of the new RGBLight is given by parameters.
     *
     * @param label label of the new RGBLight
     * @param red   intensity of red in new RGB light
     * @param green intensity of green in new RGB light
     * @param blue  intensity of blue in new RGB light
     * @throws IllegalArgumentException if given label is null or blank
     */
    public RGBLight(String label, int red, int green, int blue) {
        super(label);
        device = new RGBActuator(label);
        ((RGBActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from(OFF)
                        .to(ON, "TurnOn")
                        .from(ON)
                        .to(OFF, "TurnOff")
                        .build()
        );
        setRGB(red, green, blue);
    }

    /**
     * Creates new RGBLight with the same values of the attributes as given RGBLight except label.
     * Label of the new RGBLight is given by parameter.
     *
     * @param origRGBLight new RGBlight copies values of given thermometer
     * @param newLabel     label creates identity of the thermometer and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public RGBLight(RGBLight origRGBLight, String newLabel) {
        super(origRGBLight, newLabel);
        device = new RGBActuator(newLabel);
        ((RGBActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from("Off")
                        .to("On", "TurnOn")
                        .from("On")
                        .to("Off", "TurnOff")
                        .build()
        );
        setRGB(0, 0, 0);
    }

    /**
     * Creates new RGBLight with the same values of the attributes as given RGBLight except label.
     * Label and color of the new RGBLight is given by parameters.
     *
     * @param origRGBLight new RGBLight copies values of given RGBLight
     * @param newLabel     name of new RGB light
     * @param red          intensity of red in new RGB light
     * @param green        intensity of green in new RGB light
     * @param blue         intensity of blue in new RGB light
     * @throws IllegalArgumentException if given label is null or blank
     */
    public RGBLight(RGBLight origRGBLight, String newLabel, int red, int green, int blue) {
        super(origRGBLight, newLabel);
        setRGB(red, green, blue);
        device = new RGBActuator(newLabel);
        device.setEnabled(origRGBLight.isEnabled());
        ((RGBActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from("Off")
                        .to("On", "TurnOn")
                        .from("On")
                        .to("Off", "TurnOff")
                        .build()
        );
    }

    /**
     * Switches on the RGB light.
     */
    public void switchOn() {
        ((RGBActuator) device).controlSignal("TurnOn");
    }

    /**
     * Switches off the RGB light.
     */
    public void switchOff() {
        ((RGBActuator) device).controlSignal("TurnOff");
    }

    /**
     * Set new color of RGB light.
     *
     * @param red   new intensity of red RGB light
     * @param green new intensity of green RGB light
     * @param blue  new intensity of blue RGB light
     */
    public void setRGB(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    /**
     * Gets color of RGB light
     *
     * @return color of RGB light as Color
     */
    public Color getRGB() {
        return new Color(getRed(), getGreen(), getBlue());
    }

    /**
     * Gets intensity of red color
     *
     * @return intensity of red color as int
     */
    public int getRed() {
        return device.requestData().get(1).get(Integer.class);
    }

    /**
     * Sets intensity of red color
     *
     * @param red new value of red
     */
    public void setRed(int red) {
        ((RGBActuator) device).setRed(red);
        LOGGER.debug(String.format("Red value changed to %d", red));
    }

    /**
     * Gets intensity of green color
     *
     * @return intensity of green color as int
     */
    public int getGreen() {
        return device.requestData().get(2).get(Integer.class);
    }

    /**
     * Sets intensity of green color
     *
     * @param green new value of green
     */
    public void setGreen(int green) {
        ((RGBActuator) device).setGreen(green);
        LOGGER.debug(String.format("Green value changed to %d", green));
    }

    /**
     * Gets intensity of blue color
     *
     * @return intensity of blue color as int
     */
    public int getBlue() {
        return device.requestData().get(3).get(Integer.class);
    }

    /**
     * Sets intensity of blue color
     *
     * @param blue new value of blue
     */
    public void setBlue(int blue) {
        ((RGBActuator) device).setBlue(blue);

        LOGGER.debug(String.format("Blue value changed to %d", blue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RGBLight createWithSameAttributes(String newLabel) {
        return new RGBLight(this, newLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSameAttributes(Device rgbLight) throws IllegalArgumentException {
        if (rgbLight == null) {
            throw new IllegalArgumentException("RGBLight cannot be null");
        }
        if (this.getClass() != rgbLight.getClass()) {
            throw new IllegalArgumentException("device must be of class RGBLight");
        }

        final RGBLight typedRGB = (RGBLight) rgbLight;


        return typedRGB.getRGB().equals(this.getRGB());
    }

    /**
     * Updates the rgbLight object with the values from provided DTO.
     *
     * @param deviceDTO rgbLight DTO containing the updated values or null if value was not updated
     */
    @Override
    public void update(DeviceDTO deviceDTO) {
        final RGBLightDTO rgbLightDTO = (RGBLightDTO) deviceDTO;
        if (rgbLightDTO.getRed() != null) {
            setRed(rgbLightDTO.getRed());
        }
        if (rgbLightDTO.getGreen() != null) {
            setGreen(rgbLightDTO.getGreen());
        }
        if (rgbLightDTO.getBlue() != null) {
            setBlue(rgbLightDTO.getBlue());
        }
        super.update(rgbLightDTO);
    }
}
