package io.patriotframework.virtualsmarthomeplus.factory;

import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;

/**
 * Class for creation of Devices
 */
public class DeviceFactory {
    public DeviceFactory() {
    }

    /**
     * Creates an instance of device using DeviceDTO.
     *
     * @param device DTO of device to be created
     * @return devices of certain type
     */
    public Device createDevice( DeviceDTO device) throws IllegalArgumentException {
        final String deviceType = device.getClass().getSimpleName();
        return switch (deviceType) {
            case "RGBLightDTO" -> new RGBLight(device.getLabel());
            case "ThermometerDTO" -> new Thermometer(device.getLabel());
            case "FireplaceDTO" -> new Fireplace(device.getLabel());
            case "DoorDTO" -> new Door(device.getLabel());
            default -> throw new IllegalArgumentException();
        };
    }
}
