package io.patriotframework.virtualsmarthomeplus;

import org.springframework.stereotype.Service;


/**
 * This class serves as resource of routes to API endpoints.
 */
@Service
public class APIRoutes {

    /**
     * Beginning of the all api routes. All api endpoints share this prefix.
     */
    public static final String API_ROUTE = "/api/{apiVersion}/";

    /**
     * Route of the house.
     */
    public static final String HOUSE_ROUTE = API_ROUTE + "house/";

    /**
     * Route of the device.
     */
    public static final String DEVICE_ROUTE = HOUSE_ROUTE + "device/";

    /**
     * Route of the final device fireplace.
     */
    public static final String FIREPLACE_ROUTE = DEVICE_ROUTE + "fireplace/";

    /**
     * Route of the final device door.
     */
    public static final String DOOR_ROUTE = DEVICE_ROUTE + "door/";
    /**
     * Route of the final device thermometer.
     */
    public static final String THERMOMETER_ROUTE = DEVICE_ROUTE + "thermometer/";
    /**
     * Route of the final device RGBLight.
     */
    public static final String RGB_LIGHT_ROUTE = DEVICE_ROUTE + "RGBLight/";


}
