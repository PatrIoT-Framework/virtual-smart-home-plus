package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.Mappers.DeviceMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.springframework.web.bind.annotation.*;

/**
 * Handles the POST, GET, PUT and DELETE requests on Fireplace route: {@link APIRoutes#FIREPLACE_ROUTE}
 */
@RestController
public class FireplaceController extends FinalDeviceHandling {
    private static final String FIREPLACE_ID_ROUTE = APIRoutes.FIREPLACE_ROUTE + "{label}";


    FireplaceController(House house, DeviceMapper AbstractDeviceDTO) {
        super(house, AbstractDeviceDTO);
    }

    /**
     * Returns the fireplace
     * @param label label specified in route
     * @param apiVersion api version specified in route
     * @return Fireplace if present in the house
     */
    @GetMapping(FIREPLACE_ID_ROUTE)
    public DeviceDTO getFireplace(@PathVariable String label, @PathVariable String apiVersion) {
        return handleGet(label, Fireplace.class, apiVersion);
    }

    /**
     * Creates the fireplace
     * @param device new fireplace
     * @param apiVersion api version specified in route
     * @return fireplace added to the house
     */
    @PostMapping(APIRoutes.FIREPLACE_ROUTE)
    public DeviceDTO postFireplace(@RequestBody Fireplace device, @PathVariable String apiVersion) {
        return handlePost(device, apiVersion);
    }

    /**
     * Updates or creates the fireplace
     * @param device updated fireplace
     * @param apiVersion api version specified in route
     * @return fireplace updated or added to the house
     */
    @PutMapping(APIRoutes.FIREPLACE_ROUTE)
    public DeviceDTO putFireplace(@RequestBody Fireplace device, @PathVariable String apiVersion) {
        return handlePut(device, apiVersion);
    }

    /**
     * Deletes the fireplace
     * @param label label of the fireplace to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if fireplace exists in the house and was deleted
     */
    @DeleteMapping(FIREPLACE_ID_ROUTE)
    public String deleteFireplace(@PathVariable String label, @PathVariable String apiVersion) {
        return handleDelete(label, Fireplace.class, apiVersion);
    }
}
