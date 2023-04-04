package io.patriotframework.virtualsmarthomeplus.controllers.house;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
public class HouseControllerFunctionalTest {
    private final String housePath = "/api/v0.1/house";
    private final String houseName = "house1";
    private final JSONObject defaultHouse;
    private final JSONObject updatedHouse;

    public HouseControllerFunctionalTest() throws JSONException {
        RestAssured.baseURI = housePath;
        defaultHouse = new JSONObject()
                .put("houseName", houseName)
                .put("devices", new List[] {});

        updatedHouse = new JSONObject()
                .put("houseName", "newName")
                .put("devices", new List[] {});
    }
}
