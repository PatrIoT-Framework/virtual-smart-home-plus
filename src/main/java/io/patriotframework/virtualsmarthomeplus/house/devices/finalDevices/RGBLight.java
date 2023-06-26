package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Actuator;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class RGBLight extends Actuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private int red;
    private int green;
    private int blue;

    /**
     * Creates new RGBLight with given label and sets intensity of RGB light to zero.
     *
     * @param label label creates identity of the RGBLight and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    @JsonCreator
    public RGBLight(String label) {
        super(label);
        setRGB(0, 0, 0);
    }

    /**
     * Creates new RGBLight with given label.
     * Color of the new RGBLight is given by parameters.
     *
     * @param red   intensity of red in new RGB light
     * @param green intensity of green in new RGB light
     * @param blue  intensity of blue in new RGB light
     * @throws IllegalArgumentException if given label is null or blank
     */
    public RGBLight(String label, int red, int green, int blue) {
        super(label);
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
        return new Color(this.red, this.green, this.blue);
    }

    /**
     * Sets intensity of red color
     *
     * @param red new value of red
     */
    public void setRed(int red) {
        if (red < 0) {
            LOGGER.debug(String.format("Red value %d out of bound", red));
            this.red = 0;
            LOGGER.debug(String.format("Red value changed to %d", this.red));
            return;
        }
        if (red > 100) {
            LOGGER.debug(String.format("Red value %d out of bound", red));
            this.red = 100;
            LOGGER.debug(String.format("Red value changed to %d", this.red));
            return;
        }
        this.red = red;
        LOGGER.debug(String.format("Red value changed to %d", red));
    }

    /**
     * Gets intensity of red color
     *
     * @return intensity of red color as int
     */
    public int getRed() {
        return this.red;
    }

    /**
     * Sets intensity of green color
     *
     * @param green new value of green
     */
    public void setGreen(int green) {
        if (green < 0) {
            LOGGER.debug(String.format("Green value %d out of bound", green));
            this.green = 0;
            LOGGER.debug(String.format("Green value changed to %d", this.green));
            return;
        }
        if (green > 100) {
            LOGGER.debug(String.format("Green value %d out of bound", green));
            this.green = 100;
            LOGGER.debug(String.format("Green value changed to %d", this.green));
            return;
        }
        this.green = green;
        LOGGER.debug(String.format("Green value changed to %d", green));
    }

    /**
     * Gets intensity of green color
     *
     * @return intensity of green color as int
     */
    public int getGreen() {
        return this.green;
    }

    /**
     * Sets intensity of blue color
     *
     * @param blue new value of blue
     */
    public void setBlue(int blue) {
        if (blue < 0) {
            LOGGER.debug(String.format("Blue value %d out of bound", blue));
            this.blue = 0;
            LOGGER.debug(String.format("Blue value changed to %d", this.blue));
            return;
        }
        if (blue > 100) {
            LOGGER.debug(String.format("Blue value %d out of bound", blue));
            this.blue = 100;
            LOGGER.debug(String.format("Blue value changed to %d", this.blue));
            return;
        }
        this.blue = blue;
        LOGGER.debug(String.format("Blue value changed to %d", blue));
    }

    /**
     * Gets intensity of blue color
     *
     * @return intensity of blue color as int
     */
    public int getBlue() {
        return this.blue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RGBLight createWithSameAttributes(String newLabel) {
        return new RGBLight(this,newLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSameAttributes(Device rgbLight) throws IllegalArgumentException {
        if(rgbLight == null) {
            throw new IllegalArgumentException("Fireplace cannot be null");
        }
        if(this.getClass() != rgbLight.getClass()) {
            throw new IllegalArgumentException("device must be of class Fireplace");
        }

        RGBLight typedRGB = (RGBLight) rgbLight;

        if(this.isEnabled() != typedRGB.isEnabled()) {
            return false;
        }

        return typedRGB.getRGB().equals(this.getRGB());
    }
}
