package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class FinalDeviceHandling {

    /**
     * string that will be returned after successful deletion
     */
    public static final String DELETED_RESPONSE = "OK";
    private final House house;

    FinalDeviceHandling(House house) {
        this.house = house;
    }

    /**
     * Serving method for get requests on the final device.
     *
     * @param label       label of the requested device
     * @param deviceClass class of the requested device
     * @param apiVersion  used api version
     * @return device of specified class with given label if present in the house
     * @throws ResponseStatusException 404 if device is not present in the house or invalid API version is demanded
     */
    public DeviceDTO handleGet(String label, Class<? extends DeviceDTO> deviceClass, String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            final DeviceDTO retrievedDevice = house.getDevicesOfType(deviceClass).get(label);

            if (retrievedDevice == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "device with given label not found" // 404
                );
            }
            return retrievedDevice;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Serving method for post requests on the final device.
     *
     * @param device     device to add to the house
     * @param apiVersion used api version
     * @return device of specified class with given label if present in the house
     * @throws ResponseStatusException 409 if device already exists in the house, 404 if invalid API version is demanded
     */
    public DeviceDTO handlePost(DeviceDTO device, String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            final DeviceDTO checkForConflict = house
                    .getDevicesOfType(device.getClass())
                    .get(device.getLabel());
            if (checkForConflict != null) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "device with given label already exists" // 409
                );
            }

            try {
                RGBLight rgbLight = new RGBLight(device.getLabel());
                house.addDevice(rgbLight);
                house.updateDevice(device.getLabel(), device);
            } catch (MappingException exc) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label cannot be null", exc);
            }

            return house.getDevice(device.getLabel());
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Serving method for put requests on the final device.
     *
     * @param device     device to update or add to the house
     * @param apiVersion used api version
     * @return updated device of specified class with given label if present in the house
     * @throws ResponseStatusException 404 if invalid API version is demanded
     */
    public DeviceDTO handlePut(String label, DeviceDTO device, String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            DeviceDTO deviceToUpdate = house
                    .getDevicesOfType(device.getClass())
                    .get(label);
            if (deviceToUpdate == null) {
                RGBLight rgbLight = new RGBLight(device.getLabel());
                house.addDevice(rgbLight);
            }
            house.updateDevice(label, device);

            return house.getDevice(device.getLabel());
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion)// 404
        );
    }

    /**
     * Serving method for delete requests on the final device.
     *
     * @param label          label of the device to delete
     * @param deviceDtoClass class of the device requested to delete
     * @param apiVersion     used api version
     * @return {@link FinalDeviceHandling#DELETED_RESPONSE} if device of specified class with given label was deleted
     * from the house
     * @throws ResponseStatusException 404 if invalid API version is demanded
     */
    public String handleDelete(String label, Class<? extends DeviceDTO> deviceDtoClass, String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {

            final DeviceDTO retrievedDevice = house.getDevicesOfType(deviceDtoClass).get(label);

            if (retrievedDevice == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "device with given label not found" // 404
                );
            }
            house.removeDevice(label);
            return DELETED_RESPONSE;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
