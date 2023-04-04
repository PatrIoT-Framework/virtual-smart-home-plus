package io.patriotframework.virtualsmarthomeplus.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest
public abstract class DeviceControllerGeneralTestBase {

    protected String pathToDevices;

    public DeviceControllerGeneralTestBase() {
        pathToDevices = getPathToDevices();
    }

    /**
     * Returns path to tested endpoint e.g. "/api/v0.1/house/device"
     */
    protected abstract String getPathToDevices();

    @Test void noContentType() {
        given().noContentType()
                .get(pathToDevices).then()
                .contentType(JSON);
    }

    @Test
    public void acceptHeaderJson() {
        given().accept("application/json")
                .get().then()
                .statusCode(200)
                .contentType(JSON);
    }

    @Test
    public void basicGetHouseWithQueryParams() {
        given().queryParam("param", "param")
                .get(pathToDevices).then()
                .statusCode(200);
    }
}
