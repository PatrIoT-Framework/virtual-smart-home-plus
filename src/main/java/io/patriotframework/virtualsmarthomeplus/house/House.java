package io.patriotframework.virtualsmarthomeplus.house;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class is responsible for management of {@code Device} used in the Virtual Smart Home.
 */
@Service
public class House {

    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    public House() {
    }

    /**
     * Puts new device to the house object.
     *
     * @param device actual instance of device
     * @throws IllegalArgumentException  if device is null
     * @throws KeyAlreadyExistsException if device with given label is already present in the house
     */
    public void addDevice(Device device) throws IllegalArgumentException, KeyAlreadyExistsException {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        final Device origDevice = devices.putIfAbsent(device.getLabel(), device);
        if (origDevice != null) {
            throw new KeyAlreadyExistsException(
                    String.format("Device can't be added to the house twice. (label: %s)", origDevice.getLabel()));
        }
        LOGGER.info(String.format("Device %s with label %s added to the house", device, device.getLabel()));
    }

    /**
     * Returns a device occupied in the house.
     *
     * @param label label of the demanded device
     * @return instance of device with given label, null if device is not present in the house
     * @throws IllegalArgumentException if label is null
     */
    public Device getDevice(String label) throws IllegalArgumentException {
        if (label == null) {
            throw new IllegalArgumentException("Label can't be null.");
        }
        return devices.get(label);
    }


    /**
     * Removes given device from the house. (Removes device with the same label.)
     *
     * @param label label of the device to be removed
     * @throws IllegalArgumentException if label is null
     * @throws NoSuchElementException   if house doesn't contain device with given label
     */
    public void removeDevice(String label) throws IllegalArgumentException, NoSuchElementException {
        if (label == null) {
            throw new IllegalArgumentException("label can't be null");
        }
        final Device origDevice = devices.remove(label);
        if (origDevice == null) {
            throw new NoSuchElementException(
                    String.format("Device with label: %s can't be removed from the house because it does not contain" +
                            " such device", label));
        } else {
            LOGGER.info(String.format("Device: %s removed from the house", label));
        }
    }

    /**
     * Provide device of certain type which is stored in house.
     *
     * @param deviceType type of requested device
     * @param label label of requested device
     * @return device of requested type
     */
    public Device getDeviceOfType(Class<? extends Device> deviceType, String label) throws IllegalArgumentException {

        final Device foundDevice = getDevice(label);
        if (foundDevice != null) {
            if (deviceType.isAssignableFrom(foundDevice.getClass())) {
                return foundDevice;
            }
            return null;
        }
        return null;
    }

    /**
     * Provide devices of certain type which are stored in house.
     *
     * @param deviceClass type of requested devices
     * @return devices of requested type
     */
    public List<Device> getDevicesOfType(Class<? extends Device> deviceClass) {
        return devices.values().stream()
                .filter(device -> deviceClass.isAssignableFrom(device.getClass()))
                .collect(Collectors.toList());
    }
}
