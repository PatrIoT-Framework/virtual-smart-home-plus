package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.DoorDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Door;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Handles the POST, GET, PUT and DELETE requests on Door route: {@link APIRoutes#DOOR_ROUTE}
 */
@RestController
public class DoorController extends FinalDeviceHandling {
    private static final String DOOR_ID_ROUTE = APIRoutes.DOOR_ROUTE + "{label}";

    DoorController(House house) {
        super(house);
    }

    /**
     * Returns the door
     *
     * @param label      label specified in route
     * @param apiVersion api version specified in route
     * @return door if present in the house
     */
    @GetMapping(DOOR_ID_ROUTE)
    public ResponseEntity<DeviceDTO> getDoor(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handleGet(label, Door.class), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Creates the door
     *
     * @param device     new door
     * @param apiVersion api version specified in route
     * @return door added to the house
     */
    @PostMapping(APIRoutes.DOOR_ROUTE)
    public ResponseEntity<DeviceDTO> postDoor(
            @Valid @RequestBody DoorDTO device,
            @NotNull @PathVariable String apiVersion
    ) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePost(device), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Updates or creates the door
     *
     * @param device     updated door
     * @param apiVersion api version specified in route
     * @param label label of the new device
     * @return door updated or added to the house
     */
    @PutMapping(DOOR_ID_ROUTE)
    public ResponseEntity<DeviceDTO> putDoor(
            @Valid @RequestBody DoorDTO device,
            @NotNull @PathVariable String label,
            @PathVariable String apiVersion
    ) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePut(label, device), HttpStatus.OK);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Deletes the door
     *
     * @param label      label of the door to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if door exists in the house and was deleted
     */
    @DeleteMapping(DOOR_ID_ROUTE)
    public ResponseEntity<HttpStatus> deleteDoor(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            handleDelete(label, Door.class);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
