package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.patriotframework.virtualsmarthomeplus.MockDevice;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public abstract class DeviceControllerFunctionalTestBase {
    protected Device minPostDevice;
    protected Device fullDefaultDevice;
    protected Device fullUpdatedDevice;
    protected String pathToDevices;
    protected String label;
    protected Class<?extends Device[]> aClass = Fireplace[].class;

    protected ObjectNode minPostDeviceJson;
    protected ObjectNode fullDefaultDeviceJson;
    protected ObjectNode fullUpdatedDeviceJson;
    ObjectMapper jsonMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    @Autowired House house;

    public DeviceControllerFunctionalTestBase() throws JSONException, JsonProcessingException {
        pathToDevices = getPathToDevices();

        fullDefaultDevice = getDefaultDevice();
        fullUpdatedDevice = getFullUpdatedDevice();

        minPostDeviceJson = (ObjectNode) jsonMapper.readTree(getMinPostDeviceJson());
        fullDefaultDeviceJson = jsonMapper.valueToTree(fullDefaultDevice);
        fullUpdatedDeviceJson = jsonMapper.valueToTree(fullUpdatedDevice);

        label = fullDefaultDevice.getLabel();
    }

    /**
     * Returns path to tested endpoint e.g. "/api/v0.1/house/device"
     */
    protected abstract String getPathToDevices();

    /**
     * Returns JSON string which represents device with only mandatory attributes
     *
     * @return JSON string
     * @throws JSONException if JSONException during creating JSON occurs
     */
    protected abstract String getMinPostDeviceJson() throws JSONException;

    /**
     * Returns device with default setting and with the same identity as device from {@link #getFullUpdatedDevice()}
     *
     * @return device of the class which is under test with default setting
     */
    protected abstract Device getDefaultDevice();

    /**
     * Returns device with the same identity as device from {@link #getDefaultDevice()}
     *
     * @return device of the class which is under test with some updated attribute
     */
    protected abstract Device getFullUpdatedDevice();

    @Test
    public void getReturnsObject() {
        house.addDevice(fullDefaultDevice);

        when().get(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullDefaultDeviceJson));

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void getNonExistentDeviceReturns404() {
        when().get(pathToDevices + "/nonExistent")
                .then().statusCode(404);
    }


    @Test
    public void validPost() {
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postAlreadyPresentDeviceReturns409() {
        house.addDevice(fullDefaultDevice);

        given().body(fullUpdatedDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(409);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postWithMissingMandatoryAttributeReturns400() {
        fullDefaultDeviceJson.remove("label");
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void postWithoutOptionalAttributesReturnsDefaultDevice() {
        given().body(minPostDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(200).body(equalTo(fullDefaultDevice));

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void postWithSuperfluousAttributeReturns400() {
        fullDefaultDeviceJson.put("superfluous", true);
        given().body(fullDefaultDevice.toString())
                .when().post(pathToDevices)
                .then().statusCode(400);

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void validDelete() {
        house.addDevice(fullDefaultDevice);

        given()
                .when().delete(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullDefaultDevice.toString()));

        assertThrows(NoSuchElementException.class, () -> {
            house.getDevice(label);
        });
    }

    @Test
    public void deleteDeviceWhichIsNotPresentReturns404() {
        given()
                .when().delete(pathToDevices + "/" + label)
                .then().statusCode(404);
    }

    @Test
    public void updateDefaultDevice() {
        fullUpdatedDeviceJson.get("label");
        house.addDevice(fullDefaultDevice);

        given().body(fullUpdatedDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(200).body(equalTo(fullUpdatedDevice));

        assertTrue(fullUpdatedDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putNonExistentDeviceReturns404() {

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/nonExistent")
                .then().statusCode(404);
    }

    @Test
    public void putWithMissingMandatoryAttributeReturns400() {
        fullDefaultDeviceJson.remove("label");
        house.addDevice(fullDefaultDevice);

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putWithSuperfluousAttributeReturns400() {
        house.addDevice(fullDefaultDevice);
        fullDefaultDeviceJson.put("Superfluous", true);

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void putAttributeWithWrongValueReturns400() {
        house.addDevice(fullDefaultDevice);
        fullDefaultDeviceJson.put("label", "newLabel");

        given().body(fullDefaultDevice.toString())
                .when().put(pathToDevices + "/" + label)
                .then().statusCode(400);

        assertTrue(fullDefaultDevice.hasSameAttributes(house.getDevice(label)));
    }

    @Test
    public void getAllDevicesOfGivenType() {
        Device secondFullDefaultDevice = fullDefaultDevice.createWithSameAttributes("label2");
        house.addDevice(fullDefaultDevice);
        house.addDevice(secondFullDefaultDevice);
        house.addDevice(new MockDevice("label3"));
        TreeSet<Device> tsGiven = new TreeSet<>();
        TreeSet<Device> tsExpected = new TreeSet<>();
        tsExpected.add(fullDefaultDevice);
        tsExpected.add(secondFullDefaultDevice);

        Device[] responseBody = given()
                .when().get(pathToDevices)
                .then().statusCode(200).extract().as(aClass);

        Collections.addAll(tsGiven, responseBody);
        assertEquals(tsExpected, tsGiven);
    }

    @Test
    public void getEmptyListOfDevicesOfGivenType() throws JsonProcessingException {
        given()
                .when().get(pathToDevices)
                .then().statusCode(200).body(equalTo(jsonMapper.writeValueAsString(jsonMapper.createArrayNode())));
    }
}
