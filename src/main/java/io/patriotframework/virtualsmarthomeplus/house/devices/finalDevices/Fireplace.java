package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriot_framework.generator.device.impl.basicActuators.BasicActuator;
import io.patriot_framework.generator.device.passive.actuators.stateMachine.StateMachine;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Actuator;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representation of fireplace device.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Fireplace extends Actuator {

    public static final String ON_FIRE = "On_fire";
    public static final String EXTINGUISHED = "Extinguished";
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);

    /**
     * Creates new fireplace with given label.
     *
     * @param label label creates identity of the fireplace and is compared in the equals method
     */
    @JsonCreator
    public Fireplace(String label) {
        super(label);
        device = new BasicActuator(label);
        ((BasicActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from(ON_FIRE)
                        .to(EXTINGUISHED, "Extinguish")
                        .from(EXTINGUISHED)
                        .to(ON_FIRE, "FireUp")
                        .build()
        );
    }

    /**
     * Creates new fireplace with the same values of the attributes as given door except label.
     * Label of the new fireplace is given by parameter.
     *
     * @param origFireplace new fireplace copies values of given fireplace
     * @param newLabel      label creates identity of the fireplace and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Fireplace(Fireplace origFireplace, String newLabel) {
        super(origFireplace, newLabel);
        device = new BasicActuator(newLabel);
        ((BasicActuator) device).setStateMachine(
                new StateMachine.Builder()
                        .from(ON_FIRE)
                        .to(EXTINGUISHED, "Extinguish")
                        .from(EXTINGUISHED)
                        .to(ON_FIRE, "FireUp")
                        .build()
        );
        device.setEnabled(origFireplace.isEnabled());

    }

    /**
     * Fires up the fireplace.
     */
    public void fireUp() {
        if (getStatus().equals(EXTINGUISHED)) {
            ((BasicActuator) device).controlSignal("FireUp");
            LOGGER.debug(String.format("Fireplace %s fired up", getLabel()));
        }
    }

    /**
     * Extinguishes the fireplace.
     */
    public void extinguish() {
        if (getStatus().equals(ON_FIRE)) {
            ((BasicActuator) device).controlSignal("Extinguish");
            LOGGER.debug(String.format("Fireplace %s extinguished", getLabel()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fireplace createWithSameAttributes(String newLabel) {
        return new Fireplace(this, newLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSameAttributes(Device fireplace) throws IllegalArgumentException {
        if (fireplace == null) {
            throw new IllegalArgumentException("Fireplace cannot be null");
        }
        if (getClass() != fireplace.getClass()) {
            throw new IllegalArgumentException("device must be of class Fireplace");
        }

        final Fireplace typedFireplace = (Fireplace) fireplace;

        return isEnabled() == typedFireplace.isEnabled();
    }

    /**
     * Updates the fireplace object with the values from provided DTO.
     *
     * @param deviceDTO fireplace DTO containing the updated values or null if value was not updated
     */
    public void update(DeviceDTO deviceDTO) {
        final FireplaceDTO fireplaceDTO = (FireplaceDTO) deviceDTO;
        if (fireplaceDTO.getStatus() != null) {
            if (fireplaceDTO.getStatus().equals(EXTINGUISHED)) {
                this.extinguish();
            } else if (fireplaceDTO.getStatus().equals(ON_FIRE)) {
                this.fireUp();
            }
        }
        super.update(fireplaceDTO);
    }
}
