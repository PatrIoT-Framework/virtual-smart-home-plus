/*
 * Copyright 2021 Patriot project
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class DeviceRouteBase {

    // === GET ===
    void getRequestContentTypeJson(String endpoint) {
        given()
                .when().get(endpoint)
                .then().contentType(ContentType.JSON);
    }

    void getRequestNotFound(String endpoint) {
        given()
                .when().get(endpoint + "/notFound")
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }

    void getRequestNonExistentEndpoint(String endpoint) {
        given()
                .when().get(endpoint + "/nonExistent")
                .then().body(Matchers.equalTo(""));
    }

    void getRequestWithInvalidParam(String endpoint) {
        given()
                .param("param", "param")
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    void getRequestStatusCode200(String endpoint) {
        given()
                .when().get(endpoint)
                .then().statusCode(Response.SC_OK);
    }

    void emptyGetRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .param("label", body.get("label"))
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    void getUniqueDevice(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .pathParam("label", body.get("label"))
                .when().get(endpoint + "/{label}")
                .then().body(Matchers.equalTo(body.toString()));
    }

    // === POST ===
    void postRequestWithoutBody(String endpoint) {
        given()
                .when().post(endpoint)
                .then().statusCode(Response.SC_UNSUPPORTED_MEDIA_TYPE); // 415
    }

    void postRequestWithInvalidJsonBody(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void postRequestWithNonJsonBody(String endpoint) {
        given()
                .contentType(ContentType.TEXT)
                .body("body")
                .when().post(endpoint)
                .then().statusCode(Response.SC_UNSUPPORTED_MEDIA_TYPE); // 415
    }

    void postRequestConflict(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_CONFLICT); // 409
    }

    void postRequestStatusCode201(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_CREATED);
    }

    void postRequestBodyWithoutLabel(String endpoint, JSONObject body) {
        body.remove("label");
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST);
    }

    void postRequestBodyWithRedundantAttribute(String endpoint, JSONObject body) throws JSONException {
        body.put("redundant", 42);
        body.remove("label");
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().statusCode(Response.SC_BAD_REQUEST);
    }

    void simplePostRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when().post(endpoint)
                .then().body(Matchers.equalTo(body.toString()));

        given()
                .when().get(endpoint + "/" + body.getString("label"))
                .then().body(Matchers.equalTo(body.toString()));
    }

    // === PUT ===
    void putRequestWithoutBody(String endpoint) {
        given()
                .pathParam("label", "label")
                .when().put(endpoint + "/{label}")
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void putRequestWithInvalidJsonBody(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .pathParam("label", "label")
                .when().put(endpoint + "/{label}")
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void putRequestStatusCode200(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .pathParam("label", body.get("label"))
                .when().put(endpoint + "/{label}")
                .then().statusCode(Response.SC_OK);
    }

    void putRequestPathAndBodyLabelDifferStatusCode200(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        JSONObject newBody = new JSONObject(body.toString());
        newBody.put("label", "newUniqueLabel");

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .pathParam("label", body.get("label"))
                .when().put(endpoint + "/{label}")
                .then().statusCode(Response.SC_OK);
    }

    void putRequestPathAndBodyLabelDiffer(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        JSONObject wrongLabelBody = new JSONObject(body.toString());
        wrongLabelBody.put("label", "newUniqueLabel");

        given()
                .contentType(ContentType.JSON)
                .body(wrongLabelBody.toString())
                .pathParam("label", body.get("label"))
                .when().put(endpoint + "/{label}")
                .then().body(Matchers.equalTo(body.toString()));
    }

    void putRequestDifferentDeviceType(String endpoint, JSONObject body) {
        //todo: This test will be possible after another device POST endpoint will be implemented.
        // put device with same label but different class
    }

    void putRequestNonExistingDevice(String endpoint, JSONObject body) {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .pathParam("label", "nonExistingLabel")
                .when().put(endpoint + "/{label}")
                .then().statusCode(Response.SC_NOT_FOUND);
    }

    // === PATCH ===
    void patchRequestWithoutBody(String endpoint) {
        given()
                .pathParam("label", "label")
                .when().patch(endpoint + "/{label}")
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    void patchRequestStatusCode200(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .pathParam("label", body.get("label"))
                .when().patch(endpoint + "/{label}")
                .then().statusCode(Response.SC_OK);
    }

    // === DELETE ===
    void deleteRequestWithoutQueryParam(String endpoint) {
        given()
                .when().delete(endpoint)
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }


    void deleteRequestStatusCode200(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .pathParam("label", body.get("label"))
                .when().delete(endpoint + "/{label}")
                .then().statusCode(Response.SC_OK);
    }

    void deleteRequestNonExistingDevice(String endpoint) {
        given()
                .contentType(ContentType.JSON)
                .pathParam("label", "nonExistingLabel")
                .when().delete(endpoint + "/{label}")
                .then().statusCode(Response.SC_NOT_FOUND);
    }

    void simpleDeleteRequest(String endpoint, JSONObject body) throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(endpoint);

        given()
                .pathParam("label", body.get("label"))
                .delete(endpoint + "/{label}");

        given()
                .param("label", body.get("label"))
                .when().get(endpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }
}
