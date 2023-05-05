package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mappers.DeviceMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.springframework.web.bind.annotation.*;


/**
 * Handles the POST, GET, PUT and DELETE requests on Door route: {@link APIRoutes#DOOR_ROUTE}
 */
@RestController
public class DoorController extends FinalDeviceHandling {
    private static final String DOOR_ID_ROUTE = APIRoutes.DOOR_ROUTE + "{label}";

    DoorController(House house, DeviceMapper deviceMapper) {
        super(house, deviceMapper);
    }

    /**
     * Returns the door
     * @param label label specified in route
     * @param apiVersion api version specified in route
     * @return door if present in the house
     */
    @GetMapping(DOOR_ID_ROUTE)
    public DeviceDTO getDoor(@PathVariable String label, @PathVariable String apiVersion) {
        return handleGet(label, Door.class, apiVersion);
    }

    /**
     * Creates the door
     * @param device new door
     * @param apiVersion api version specified in route
     * @return door added to the house
     */
    @PostMapping(APIRoutes.DOOR_ROUTE)
    public DeviceDTO postDoor(@RequestBody Door device, @PathVariable String apiVersion) {
        return handlePost(device, apiVersion);
    }

    /**
     * Updates or creates the door
     * @param device updated door
     * @param apiVersion api version specified in route
     * @return door updated or added to the house
     */
    @PutMapping(APIRoutes.DOOR_ROUTE)
    public DeviceDTO putDoor(@RequestBody Door device, @PathVariable String apiVersion) {
        return handlePut(device, apiVersion);
    }

    /**
     * Deletes the door
     * @param label label of the door to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if door exists in the house and was deleted
     */
    @DeleteMapping(DOOR_ID_ROUTE)
    public String deleteDoor(@PathVariable String label, @PathVariable String apiVersion) {
        return handleDelete(label, Door.class, apiVersion);
    }
}
