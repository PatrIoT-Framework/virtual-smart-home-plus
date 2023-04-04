package io.patriotframework.virtualsmarthomeplus.controllers.fireplace;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.patriotframework.virtualsmarthomeplus.controllers.DeviceControllerFunctionalTestBase;

import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.json.JSONException;
import org.json.JSONObject;

public class FireplaceControllerFunctionalTest extends DeviceControllerFunctionalTestBase {

    public FireplaceControllerFunctionalTest() throws JSONException, JsonProcessingException {
        super();
    }

    @Override
    protected String getPathToDevices() {
        return "/api/v0.1/house/device";
    }

    @Override
    protected String getMinPostDeviceJson() throws JSONException {
        return new JSONObject()
                .put("label", "label1")
                .toString();
    }

    @Override
    protected Device getDefaultDevice() {
        return new Fireplace("label1");
    }

    @Override
    protected Device getFullUpdatedDevice() {
        Fireplace fireplace = new Fireplace("label1");
        fireplace.fireUp();
        return fireplace;
    }
}
