package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representation of fireplace device.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Fireplace extends Device {

    private Boolean onFire = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    public static final String ON_FIRE = "on_fire";
    public static final String EXTINGUISHED = "extinguished";

    /**
     * Creates new fireplace with given label.
     *
     * @param label label creates identity of the fireplace and is compared in the equals method
     */
    @JsonCreator
    public Fireplace(String label) {
        super(label);
    }

    /**
     * Creates new fireplace with the same values of the attributes as given door except label.
     * Label of the new fireplace is given by parameter.
     *
     * @param origFireplace new fireplace copies values of given fireplace
     * @param newLabel   label creates identity of the fireplace and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Fireplace(Fireplace origFireplace, String newLabel) {
        super(origFireplace, newLabel);
        onFire = origFireplace.onFire;
    }

    /**
     * Fires up the fireplace.
     */
    public void fireUp() {
        if(!onFire) {
            onFire = true;
            LOGGER.debug(String.format("Fireplace %s fired up", getLabel()));
        }
    }

    /**
     * Extinguishes the fireplace.
     */
    public void extinguish() {
        if(onFire) {
            onFire = false;
            LOGGER.debug(String.format("Fireplace %s extinguished", getLabel()));
        }
    }

    /**
     * Returns the info about the fireplace.
     *
     * @return {@link #ON_FIRE} if the fireplace is lit, {@link #EXTINGUISHED} otherwise
     */
    @JsonIgnore
    public String getStatus() {
        return onFire ? ON_FIRE : EXTINGUISHED;
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
        if(fireplace == null) {
            throw new IllegalArgumentException("Fireplace cannot be null");
        }
        if(getClass() != fireplace.getClass()) {
            throw new IllegalArgumentException("device must be of class Fireplace");
        }

        Fireplace typedFireplace = (Fireplace)fireplace;

        if(isEnabled() != typedFireplace.isEnabled()) {
            return false;
        }
        return typedFireplace.onFire == onFire;
    }
}
