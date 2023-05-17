package io.patriotframework.virtualsmarthomeplus.house;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mappers.DeviceMapper;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class is responsible for management of {@code Device} used in the Virtual Smart Home.
 */
@Service
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class House {

    DeviceMapper deviceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    public House(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    /**
     * Puts new device to the house object.
     *
     * @param device actual instance of device
     * @throws IllegalArgumentException if device is null
     * @throws KeyAlreadyExistsException if device with given label is already present in the house
     */
    public void addDevice(Device device) throws IllegalArgumentException, KeyAlreadyExistsException {
        if(device == null) {
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
    public DeviceDTO getDevice(String label) throws IllegalArgumentException {
        if(label == null) {
            throw new IllegalArgumentException("Label can't be null.");
        }
        //return deviceMapper.deviceToDto(devices.get(label));
        return deviceMapper.map(devices.get(label));
    }

    /**
     * Updates device from the house.
     *
     * @param device device with updated values (device is specified by its label)
     * @throws IllegalArgumentException if device is null
     * @throws NoSuchElementException if no device with given label is not present in the house
     */
    public void updateDevice(Device device) throws IllegalArgumentException, NoSuchElementException {
        if(device == null)  {
            throw new IllegalArgumentException("Device can't be null");
        }
        final Device origDevice = devices.get(device.getLabel());
        if(origDevice== null) {
            throw new NoSuchElementException(
                    String.format("Device with label: %s is not present in the house", device.getLabel()));
        }
        devices.put(device.getLabel(), device);
        if(!LOGGER.isDebugEnabled()) {
            LOGGER.info(String.format("Device with label:%s updated.", device.getLabel()));
        }
        LOGGER.debug(String.format("Device:%s updated to: %s", origDevice, device));
    }

    /**
     * Removes given device from the house. (Removes device with the same label.)
     *
     * @param label label of the device to be removed
     * @throws IllegalArgumentException if label is null
     * @throws NoSuchElementException if house doesn't contain device with given label
     */
    public void removeDevice(String label) throws IllegalArgumentException, NoSuchElementException{
        if(label == null) {
            throw new IllegalArgumentException("label can't be null");
        }
        final Device origDevice = devices.remove(label);
        if(origDevice == null) {
            throw new NoSuchElementException(
                    String.format("Device with label: %s can't be removed from the house because it does not contain" +
                            " such device", label));
        } else {
            LOGGER.info(String.format("Device: %s removed from the house", label));
        }
    }

    /**
     * Provides devices of certain type which are stored in house.
     *
     * @param deviceType type of requested devices
     * @return map of labels and devices of requested type, Empty set if such device does not exist
     */
    public Map<String, DeviceDTO> getDevicesOfType(Class<? extends Device> deviceType) {
        Map<String, DeviceDTO> res = devices.entrySet().stream()
                .filter(x -> deviceType.isAssignableFrom(x.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> deviceMapper.map(e.getValue())));
        System.out.println(res);
        return res;
    }
}
