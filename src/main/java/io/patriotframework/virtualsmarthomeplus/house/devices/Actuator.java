package io.patriotframework.virtualsmarthomeplus.house.devices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriotframework.virtualsmarthomeplus.DTOs.ActuatorDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Actuator extends Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private boolean switchedOn = false;

    /**
     * Creates new Actuator.
     *
     * @param label of the new Actuator
     * @throws IllegalArgumentException if given label is null or blank
     */
    @JsonCreator
    public Actuator(String label) {
        super(label);
    }

    /**
     * Creates new Actuator with the same values of the attributes as given Actuator except label.
     * Label of the new Actuator is given by parameter.
     *
     * @param newLabel label creates identity of the thermometer and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Actuator(Device origDevice, String newLabel) {
        super(origDevice, newLabel);
    }

    /**
     * Switches actuator ON
     */
    public void switchOn() {
        if (!switchedOn) {
            this.switchedOn = true;
            LOGGER.debug(String.format("Actuator %s is switched on", getLabel()));
        }
    }

    /**
     * Switches actuator OF
     */
    public void switchOf() {
        if (switchedOn) {
            this.switchedOn = false;
            LOGGER.debug(String.format("Actuator %s is switched on", getLabel()));
        }
    }

    /**
     * Gets status of actuator
     *
     * @return intensity of blue color as int
     */
    public boolean getSwitchedOn() {
        return this.switchedOn;
    }

    /**
     * Updates the Actuator object with the values from provided DTO.
     *
     * @param deviceDTO actuator DTO containing the updated values or null if value was not updated
     */
    @Override
    public void update(DeviceDTO deviceDTO) {
        ActuatorDTO actuatorDTO = (ActuatorDTO) deviceDTO;
        if (actuatorDTO.getSwitchedOn() != null) {
            if (actuatorDTO.getSwitchedOn()) {
                this.switchOn();
            } else {
                this.switchOf();
            }
        }
        super.update(deviceDTO);
    }
}
