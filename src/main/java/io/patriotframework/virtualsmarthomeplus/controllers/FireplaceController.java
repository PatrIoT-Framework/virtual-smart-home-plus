package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Handles the POST, GET, PUT and DELETE requests on Fireplace route: {@link APIRoutes#FIREPLACE_ROUTE}
 */
@RestController
public class FireplaceController extends FinalDeviceHandling {
    private static final String FIREPLACE_ID_ROUTE = APIRoutes.FIREPLACE_ROUTE + "{label}";


    FireplaceController(House house) {
        super(house);
    }

    /**
     * Returns the fireplace
     *
     * @param label      label specified in route
     * @param apiVersion api version specified in route
     * @return Fireplace if present in the house
     */
    @GetMapping(FIREPLACE_ID_ROUTE)
    public DeviceDTO getFireplace(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return handleGet(label, Fireplace.class);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Creates the fireplace
     *
     * @param device     new fireplace DTO
     * @param apiVersion api version specified in route
     * @return fireplace added to the house
     */
    @PostMapping(APIRoutes.FIREPLACE_ROUTE)
    public DeviceDTO postFireplace(@Valid @RequestBody FireplaceDTO device, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {

            return handlePost(device);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Updates or creates the fireplace
     *
     * @param device     updated fireplace DTO
     * @param apiVersion api version specified in route
     * @return fireplace updated or added to the house
     */
    @PutMapping(FIREPLACE_ID_ROUTE)
    public DeviceDTO putFireplace(@Valid @RequestBody FireplaceDTO device, @NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return handlePut(label, device);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Deletes the fireplace
     *
     * @param label      label of the fireplace to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if fireplace exists in the house and was deleted
     */
    @DeleteMapping(FIREPLACE_ID_ROUTE)
    public ResponseEntity<HttpStatus> deleteFireplace(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            handleDelete(label, Fireplace.class);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
