package io.patriotframework.virtualsmarthomeplus.house.devices;

import io.patriotframework.virtualsmarthomeplus.house.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Actuator extends Device {

    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    public static final String TURNED_ON = "turned_on";
    public static final String TURNED_OF = "turned_of";
    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    public Actuator(Device origDevice, String newLabel) {
        super(origDevice, newLabel);
    }

    public void switchOn() {
        if (!enabled) {
            this.enabled = true;
            LOGGER.debug(String.format("Actuator %s is switched on", getLabel()));
        }
    }

    public void switchOf() {
        if (enabled) {
            this.enabled = false;
            LOGGER.debug(String.format("Actuator %s is switched on", getLabel()));
        }
    }

    public boolean isOn() {
        return this.enabled;
    }
}
