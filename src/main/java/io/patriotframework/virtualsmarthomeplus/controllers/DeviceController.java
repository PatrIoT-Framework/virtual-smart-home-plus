package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;



@RestController
public class DeviceController {
    private final House house;
    private static final String DEVICE_ID_ROUTE = APIRoutes.DEVICE_ROUTE + "{label}";

    @Autowired
    public DeviceController(House house) {
        this.house = house;
    }

    @GetMapping(APIRoutes.DEVICE_ROUTE)
    public ArrayList<Device> getDevices(@PathVariable String apiVersion) {
        if(apiVersion.equals(APIVersions.V0_1)) {
            return new ArrayList<>((house.getDevicesOfType(Device.class).values()));
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    @GetMapping(DEVICE_ID_ROUTE)
    public DeviceDTO getDevice(@PathVariable String label, @PathVariable String apiVersion) {
        if(apiVersion.equals(APIVersions.V0_1)) {
            DeviceDTO retrievedDevice = house.getDevice(label);
            if(retrievedDevice == null) {
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
}
