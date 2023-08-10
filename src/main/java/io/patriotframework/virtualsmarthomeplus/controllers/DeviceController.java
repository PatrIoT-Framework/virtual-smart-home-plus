package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Handles the GET, requests on Device route: {@link APIRoutes#DEVICE_ROUTE}
 */
@RestController
public class DeviceController {
    private static final String DEVICE_ID_ROUTE = APIRoutes.DEVICE_ROUTE + "{label}";
    private final House house;
    private final DTOMapper dtoMapper;
    @Autowired
    public DeviceController(House house) {
        this.house = house;
        dtoMapper = new DTOMapper(new ModelMapper());
    }

    /**
     * Returns the list of all devices
     *
     * @param apiVersion api version specified in the route
     * @return list of all devices in the house
     */
    @GetMapping(APIRoutes.DEVICE_ROUTE)
    public ResponseEntity<ArrayList<DeviceDTO>> getDevices(@PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(new ArrayList<>((house.getDevicesOfType(Device.class)
                    .stream()
                    .map(dtoMapper::map)
                    .collect(Collectors.toList()))), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Returns the device specified by it`s label
     *
     * @param label      label of the requested device specified in route
     * @param apiVersion api version specified in route
     * @return device of any type with given label from the house,
     */
    @GetMapping(DEVICE_ID_ROUTE)
    public DeviceDTO getDevice(@PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            final Device retrievedDevice = house.getDevice(label);
            if (retrievedDevice == null) {
                throw new NoSuchElementException("Device not found");
            }
            return dtoMapper.map(retrievedDevice);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
