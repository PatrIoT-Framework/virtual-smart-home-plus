package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.APIVersions;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
public class RGBLightController extends FinalDeviceHandling {
    private static final String RGB_LIGHT_ID_ROUTE = APIRoutes.RGB_LIGHT_ROUTE + "{label}";

    RGBLightController(House house) {
        super(house);
    }

    /**
     * Creates the RGBLight
     *
     * @param device     new RGBLight
     * @param apiVersion api version specified in route
     * @return RGBLight added to the house
     */
    @PostMapping(APIRoutes.RGB_LIGHT_ROUTE)
    public ResponseEntity<DeviceDTO> postLed(@Valid @RequestBody RGBLightDTO device, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePost(device), HttpStatus.OK);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Returns the RGBLight
     *
     * @param label      label specified in route
     * @param apiVersion api version specified in route
     * @return RGBlight if present in the house
     */
    @GetMapping(RGB_LIGHT_ID_ROUTE)
    public ResponseEntity<DeviceDTO> getLed(@PathVariable @NotNull String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handleGet(label, RGBLight.class), HttpStatus.OK);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }

    /**
     * Updates or creates the RGBLight
     *
     * @param device     updated RGBLight
     * @param apiVersion api version specified in route
     * @return RGBLight updated or added to the house
     */
    @PutMapping(RGB_LIGHT_ID_ROUTE)
    public ResponseEntity<DeviceDTO> putLed(@Valid @RequestBody RGBLightDTO device, @PathVariable @NotNull String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            return new ResponseEntity<>(handlePut(label, device), HttpStatus.OK);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }


    /**
     * Deletes the RGBLight
     *
     * @param label      label of the RGBLight to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if RGBLight exists in the house and was deleted
     */
    @DeleteMapping(RGB_LIGHT_ID_ROUTE)
    public ResponseEntity<HttpStatus> deleteRGBLight(@PathVariable @NotNull String label, @PathVariable String apiVersion) {
        if (apiVersion.equals(APIVersions.V0_1)) {
            handleDelete(label, RGBLight.class);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Unknown api version: %s", apiVersion) // 404
        );
    }
}
