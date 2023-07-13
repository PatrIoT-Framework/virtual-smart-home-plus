package io.patriotframework.virtualsmarthomeplus.controllers;

import io.patriotframework.virtualsmarthomeplus.APIRoutes;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class ThermometerController extends FinalDeviceHandling{
    public static final String THERMOMETER_ID_ROUTE = APIRoutes.THERMOMETER_ROUTE + "{label}";

    public static final String THERMOMETER_GET_TEMPERATURE  = THERMOMETER_ID_ROUTE + "/getTemperature";

    public static final String THERMOMETER_GET_UNIT  = THERMOMETER_ID_ROUTE + "/getUnit";

    public static final String THERMOMETER_SET_UNIT  = THERMOMETER_ID_ROUTE + "/setUnit";

    @Autowired
    ThermometerController(House house) {
        super(house);
    }

    /**
     * Returns the thermometer
     * @param label label specified in route
     * @param apiVersion api version specified in route
     * @return thermometer if present in the house
     */
    @GetMapping(THERMOMETER_ID_ROUTE)
    public DeviceDTO getThermometer(@PathVariable String label, @PathVariable String apiVersion) {
        return handleGet(label, Thermometer.class);
    }

    /**
     * Creates the thermometer
     * @param device new thermometer
     * @param apiVersion api version specified in route
     * @return thermometer added to the house
     */
    @PostMapping(APIRoutes.THERMOMETER_ROUTE)
    public DeviceDTO postThermometer(@RequestBody ThermometerDTO device,@PathVariable String label, @PathVariable String apiVersion) {
        return handlePost(device);
    }

    /**
     * Updates or creates the thermometer
     * @param thermometer updated thermometer
     * @param apiVersion api version specified in route
     * @return thermometer updated or added to the house
     */
    @PutMapping(THERMOMETER_ID_ROUTE)
    public DeviceDTO putThermometer(@RequestBody ThermometerDTO thermometer, @NotNull @PathVariable String label, @PathVariable String apiVersion) {
        return handlePut(label,thermometer);
    }

    /**
     * Deletes the thermometer
     * @param label label of the thermometer to be deleted
     * @param apiVersion api version specified in route
     * @return "OK" if thermometer exists in the house and was deleted
     */
    @DeleteMapping(THERMOMETER_ID_ROUTE)
    public ResponseEntity<HttpStatus> deleteThermometer(@NotNull @PathVariable String label, @PathVariable String apiVersion) {
        handleDelete(label, Thermometer.class);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
