package io.patriotframework.virtualsmarthomeplus.Actuators;

import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.device.passive.actuators.AbstractActuator;

import java.util.ArrayList;
import java.util.List;

/**
 * Actuator for RGB light
 */
public class RGBActuator extends AbstractActuator {

    /**
     * intensity of red color in RGB
     */
    private int red;
    /**
     * intensity of green color in RGB
     */
    private int green;
    /**
     * intensity of blue color in RGB
     */
    private int blue;

    /**
     * creates new RGBActuator
     */
    public RGBActuator() {
        super();
    }

    /**
     * creates new RGBActuator
     *
     * @param label label of the new RGBActuator
     */
    public RGBActuator(String label) {
        super(label);
    }

    /**
     * creates new RGBActuator
     */
    public void setRGB(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    /**
     * sets intensity of red color in RGB
     *
     */
    public void setRed(int red) {
        if (red < 0) {
            this.red = 0;
            return;
        }
        if (red > 255) {
            this.red = 255;
            return;
        }
        this.red = red;
    }

    /**
     * sets intensity of green color in RGB
     *
     */
    public void setGreen(int green) {
        if (green < 0) {
            this.green = 0;
            return;
        }
        if (green > 255) {
            this.green = 255;
            return;
        }
        this.green = green;
    }

    /**
     * sets intensity of blue color in RGB
     *
     */
    public void setBlue(int blue) {
        if (blue < 0) {
            this.blue = 0;
            return;
        }
        if (blue > 255) {
            this.blue = 255;
            return;
        }
        this.blue = blue;
    }

    /**
     * requests data from RGBActuator
     *
     * @return status of RGBActuator and intensity of red, green and blue color
     */
    @Override
    public List<Data> requestData(Object... params) {
        List<Data> data = new ArrayList<>(super.requestData());
        System.out.println("RGBActuator requestData");
        System.out.println(data);
        if (data == null) {
            return null;
        }

        data.add(new Data(Integer.class, red));
        data.add(new Data(Integer.class, green));
        data.add(new Data(Integer.class, blue));
        return data;
    }

}
