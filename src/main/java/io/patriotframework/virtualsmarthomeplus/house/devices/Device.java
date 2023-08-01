package io.patriotframework.virtualsmarthomeplus.house.devices;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * From this class will be derived Actuators and Sensors. From them will be then derived final devices.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Device implements Comparable<Device> {
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private String label;
    private Boolean enabled = false;

    /**
     * Creates new device with given label.
     *
     * @param label label creates identity of the device and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    @JsonCreator
    public Device(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Parameter 'label' for the new device can't be null");
        }
        if (label.isBlank()) {
            throw new IllegalArgumentException("Parameter 'label' for the new device can't blank");
        }
        this.label = label;
        LOGGER.debug(String.format("Device %s created", label));
    }

    /**
     * Creates new device with the same values of the attributes as given device except label.
     * Label of the new device is given by parameter.
     *
     * @param origDevice new device copies values of given device
     * @param newLabel   label creates identity of the device and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Device(Device origDevice, String newLabel) {
        if (origDevice == null) {
            throw new IllegalArgumentException("Parameter 'origDevice' can't be null");
        }
        if (newLabel == null) {
            throw new IllegalArgumentException("Parameter 'label' can't be null");
        }
        if (newLabel.isBlank()) {
            throw new IllegalArgumentException("Parameter 'label' can't blank");
        }
        this.label = newLabel;
        enabled = origDevice.enabled;
        LOGGER.debug(String.format("Device %s created", label));
    }

    /**
     * Label creates identity of the device and is compared in the {@link #equals(Object) equals} method.
     *
     * @return label of the device
     */
    public String getLabel() {
        return label;
    }

    /**
     * Indicates whether the device will react to commands.
     *
     * @return if true, device will react to commands
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Configures whether the device will react to commands.
     *
     * @param enabled if true, device will react to commands
     */
    public void setEnabled(Boolean enabled) {
        if (enabled == null) return;
        if (enabled != this.enabled) {
            LOGGER.debug(String
                    .format("Device %s changed it's property enabled from: %s to: %s", label, this.enabled, enabled));
        }
        this.enabled = enabled;
    }

    /**
     * Method responsible for creation new devices with the same values of attributes except the label
     *
     * @param newLabel label of the new device
     * @return returns created device
     */
    public abstract Device createWithSameAttributes(String newLabel);

    /**
     * Method responsible for check whether two Devices has the same values of attributes
     *
     * @param device device to which this object will be compared
     * @throws IllegalArgumentException if parameter device is null
     * @return return true if objects have same attributes and false otherwise
     */
    public abstract boolean hasSameAttributes(Device device) throws IllegalArgumentException;

    /**
     * Compares device with given device based on the lexicographic arrangement of their names
     *
     * @param compared the object to be compared with.
     * @return 0 if the devices label is equal to the other compared devices label.
     * < 0 if the label is lexicographically less than the other label
     * > 0 if the label is lexicographically greater than the other string
     * @throws IllegalArgumentException if the compared device is null
     */
    @Override
    public int compareTo(Device compared) {
        if (compared == null) {
            throw new IllegalArgumentException("Compared device must not be null");
        }
        return label.compareTo(compared.getLabel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Device device = (Device) o;
        return getLabel().equals(device.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel());
    }

    /**
     * Updates the Device object with the values from provided DTO.
     *
     * @param deviceDTO device DTO containing the updated values or null if value was not updated
     */
    public void update(DeviceDTO deviceDTO) {
        if (deviceDTO.getEnabled() != null) {
            this.setEnabled(deviceDTO.getEnabled());
        }
        if (deviceDTO.getLabel() != null) {
            this.label = deviceDTO.getLabel();
        }
    }
}
