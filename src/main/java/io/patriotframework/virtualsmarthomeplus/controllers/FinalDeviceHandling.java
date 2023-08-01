package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;


@Component
public class FinalDeviceHandling {

    /**
     * string that will be returned after successful deletion
     */
    public static final String DELETED_RESPONSE = "OK";
    private final House house;
    private final DTOMapper dtoMapper;

    FinalDeviceHandling(House house) {
        this.house = house;
        this.dtoMapper = new DTOMapper(new ModelMapper());
    }

    /**
     * Serving method for get requests on the final device.
     *
     * @param label       label of the requested device
     * @param deviceClass class of the requested device
     * @return device DTO of specified class with given label if present in the house
     * @throws ResponseStatusException 404 if device is not present in the house or invalid API version is demanded
     */
    public DeviceDTO handleGet(String label, Class<? extends Device> deviceClass)
            throws NoSuchElementException, IllegalArgumentException {
        final Device retrievedDevice = house.getDeviceOfType(deviceClass, label);

        if (retrievedDevice == null) {
            throw new NoSuchElementException();
        }
        return dtoMapper.map(retrievedDevice);
    }

    /**
     * Serving method for post requests on the final device.
     *
     * @param device device to add to the house
     * @return device of specified class with given label if present in the house
     * @throws ResponseStatusException 409 if device already exists in the house, 404 if invalid API version is demanded
     */
    public DeviceDTO handlePost(DeviceDTO device) throws IllegalArgumentException {
        final Device checkForConflict = house
                .getDeviceOfType(dtoMapper.mapDtoClassType(device.getClass()), device.getLabel());
        if (checkForConflict != null) {
            throw new KeyAlreadyExistsException();
        }

        final RGBLight rgbLight = new RGBLight(device.getLabel());
        house.addDevice(rgbLight);
        rgbLight.update(device);

        return dtoMapper.map(house.getDevice(device.getLabel()));
    }

    /**
     * Serving method for put requests on the final device.
     *
     * @param device device to update or add to the house
     * @param label  label of the device to be updated
     * @return updated device DTO of specified class with given label if present in the house
     * @throws IllegalArgumentException if label is null
     * @throws NoSuchElementException   if device we want to update is not in the house
     */
    public DeviceDTO handlePut(String label, DeviceDTO device)
            throws IllegalArgumentException, NoSuchElementException {
        if (device == null) throw new IllegalArgumentException();
        Device deviceToUpdate = house
                .getDeviceOfType(dtoMapper.mapDtoClassType(device.getClass()), label);
        if (deviceToUpdate == null) {
            final RGBLight rgbLight = new RGBLight(device.getLabel());
            deviceToUpdate = rgbLight;
            house.addDevice(rgbLight);
        }
        deviceToUpdate.update(device);

        if (!label.equals(deviceToUpdate.getLabel())) {
            house.removeDevice(label);
            house.addDevice(deviceToUpdate);
        }

        return dtoMapper.map(deviceToUpdate);
    }

    /**
     * Serving method for delete requests on the final device.
     *
     * @param label       label of the device to delete
     * @param deviceClass class of the device requested to delete
     * @throws NoSuchElementException if required device is not in house
     */
    public void handleDelete(
            String label,
            Class<? extends Device> deviceClass
    ) throws NoSuchElementException, IllegalArgumentException {
        final Device retrievedDevice = house.getDeviceOfType(deviceClass, label);

        if (retrievedDevice == null) {
            throw new NoSuchElementException();
        }
        house.removeDevice(label);
    }
}
