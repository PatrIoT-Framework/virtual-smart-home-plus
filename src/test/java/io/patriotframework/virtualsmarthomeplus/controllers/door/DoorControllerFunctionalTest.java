package io.patriotframework.virtualsmarthomeplus.controllers.door;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.patriotframework.virtualsmarthomeplus.controllers.fireplace.FireplaceControllerFunctionalTest;
import org.json.JSONException;
import org.json.JSONObject;

public class DoorControllerFunctionalTest  {
    FireplaceControllerFunctionalTest a = new FireplaceControllerFunctionalTest();
    String doorURN = "/api/v0.1/house/device/door";

    JSONObject minPostFireplace = new JSONObject()
            .put("label", "label1");

    JSONObject fullDefaultFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", false);

    JSONObject fullUpdatedFireplace = new JSONObject()
            .put("label", "label1")
            .put("enabled", true);

    public DoorControllerFunctionalTest() throws JSONException, JsonProcessingException {
    }
}
