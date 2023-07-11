package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import org.springframework.web.bind.annotation.*;


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
    public DeviceDTO postLed(@RequestBody RGBLightDTO device, @PathVariable String apiVersion) {
        return handlePost(device, apiVersion);
    }

    /**
     * Returns the RGBLight
     *
     * @param label      label specified in route
     * @param apiVersion api version specified in route
     * @return RGBlight if present in the house
     */
    @GetMapping(RGB_LIGHT_ID_ROUTE)
    public DeviceDTO getLed(@PathVariable String label, @PathVariable String apiVersion) {
        return handleGet(label, RGBLightDTO.class, apiVersion);
    }

    /**
     * Updates or creates the RGBLight
     *
     * @param device     updated RGBLight
     * @param apiVersion api version specified in route
     * @return RGBLight updated or added to the house
     */
    @PutMapping(RGB_LIGHT_ID_ROUTE)
    public DeviceDTO putLed(@RequestBody RGBLightDTO device, @PathVariable String label, @PathVariable String apiVersion) {
        return handlePut(label, device, apiVersion);
    }


    /**
     * Deletes the RGBLight
     *
     * @param label      label of the RGBLight to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if RGBLight exists in the house and was deleted
     */
    @DeleteMapping(RGB_LIGHT_ID_ROUTE)
    public String deleteRGBLight(@PathVariable String label, @PathVariable String apiVersion) {
        return handleDelete(label, RGBLightDTO.class, apiVersion);
    }
}
