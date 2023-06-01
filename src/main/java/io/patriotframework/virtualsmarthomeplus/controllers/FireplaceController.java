package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Handles the POST, GET, PUT and DELETE requests on Fireplace route: {@link APIRoutes#FIREPLACE_ROUTE}
 */
@RestController
public class FireplaceController extends FinalDeviceHandling {
    private static final String FIREPLACE_ID_ROUTE = APIRoutes.FIREPLACE_ROUTE + "{label}";


    FireplaceController(House house, DTOMapper AbstractDeviceDTO) {
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
        return handleGet(label, FireplaceDTO.class, apiVersion);
    }

    /**
     * Creates the fireplace
     * @param device new fireplace
     * @param apiVersion api version specified in route
     * @return fireplace added to the house
     */
    @PostMapping(APIRoutes.FIREPLACE_ROUTE)
    public DeviceDTO postFireplace(@Valid @RequestBody FireplaceDTO device, @PathVariable String apiVersion) {
        return handlePost(device, apiVersion);
    }

    /**
     * Updates or creates the fireplace
     * @param device updated fireplace
     * @param apiVersion api version specified in route
     * @return fireplace updated or added to the house
     */
    @PutMapping(APIRoutes.FIREPLACE_ROUTE)
    public DeviceDTO putFireplace(@Valid @RequestBody FireplaceDTO device, @PathVariable String apiVersion) {
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
        return handleDelete(label, FireplaceDTO.class, apiVersion);
    }
}
