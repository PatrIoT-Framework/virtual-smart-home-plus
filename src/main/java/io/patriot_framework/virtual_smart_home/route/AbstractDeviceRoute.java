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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import io.patriot_framework.virtual_smart_home.house.device.DifferentDeviceException;
import io.patriot_framework.virtual_smart_home.house.device.IllegalDeviceArgumentException;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestDefinition;
import static org.apache.camel.model.rest.RestParamType.path;
import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.Level;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * REST API base class for device routes.
 */
public abstract class AbstractDeviceRoute extends HouseRoute {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Level LOGLEVEL = Level.INFO;
    private final String routeDeviceIdentifier = "label";
    private String endpoint;
    private Class<? extends Device> deviceType;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setupRoute(String endpoint, Class<? extends Device> deviceType) {
        this.endpoint = endpoint;
        this.deviceType = deviceType;
    }

    /**
     * REST API configuration.
     * Any issue related to JSON parsing returns status code 400 (Bad Request).
     */
    @Override
    public void configure() {
        final RestDefinition rd = rest(getRoute());
        buildGet(rd);
        buildPost(rd);
        buildPut(rd);
        buildPatch(rd);
        buildDelete(rd);

        onException(UnrecognizedPropertyException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        onException(JsonParseException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        onException(IllegalDeviceArgumentException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        handleGet();
        handlePost();
        handlePut();
        handleDelete();
    }
    /**
     * HTTP GET request setting.
     *
     * Set the get method and adds openAPI info.
     * @param rd rest definition with the desired route
     */
    private void buildGet(RestDefinition rd) {
        rd.get("{" + routeDeviceIdentifier + "}")
            .description(String.format("Returns basic info about %s with given label", endpoint))
            .responseMessage().code(200).message(String.format("Basic info about %s returned", endpoint))
                .endResponseMessage()
            .responseMessage().code(404).message(String.format("%s with given label isn't present in the house",
                    endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
            .outType(deviceType)
            .param().name("label").type(path).description(String.format("The label of the requested %s", endpoint))
                .endParam()
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:read" + endpoint);

        rd.get()
            .description(String.format("Returns array of all %ss",endpoint))
            .outType(deviceType.arrayType())
            .responseMessage().code(200).message(String.format("Basic info about all %ss returned", endpoint))
                .endResponseMessage()
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:read" + endpoint + "s");
    }

    /**
     * HTTP POST request setting.
     *
     * Set the post method and adds openAPI info.
     * @param rd rest definition with the desired route
     */
    private void buildPost(RestDefinition rd) {
        rd.post()
            .description(String.format("Add new %s to the house", endpoint))
            .responseMessage().code(201).message(String.format("%s added", endpoint)).endResponseMessage()
            .responseMessage().code(400).message("Invalid body").endResponseMessage()
            .responseMessage().code(409).message(String.format("Device %s with given label already exists ", endpoint))
                .endResponseMessage()
            .consumes(MediaType.APPLICATION_JSON_VALUE)
            .type(deviceType)
            .param().name("body").description("Object, that needs to be added to the house."
                    + " Label must be unique among all the devices in the house.").endParam()
            .outType(deviceType)
            .to("direct:create" + endpoint);
    }

    /**
     * HTTP PUT request setting.
     *
     * Set the put method and adds openAPI info.
     * @param rd rest definition with the desired route
     */
    private void buildPut(RestDefinition rd) {
        buildAsPut(rd.put("{" + routeDeviceIdentifier + "}"), String.format("Update an existing %s", endpoint));
    }

    /**
     * HTTP PATCH request setting.
     *
     * Set the patch method and adds openAPI info.
     * Setting of the patch is the same as of the put.
     * @param rd rest definition with the desired route
     */
    private void buildPatch(RestDefinition rd) {
        buildAsPut(rd.patch("{" + routeDeviceIdentifier + "}"),
                String.format("Update an existing %s (same as put)",endpoint));
    }

    /**
     * HTTP DELETE request setting.
     *
     * Set the delete method and adds openAPI info.
     * @param rd rest definition with the desired route
     */
    private void buildDelete(RestDefinition rd) {
        rd.delete("{" + routeDeviceIdentifier + "}")
            .description(String.format("Deletes a %s with given label", endpoint))
            .responseMessage().code(200).message(String.format("%s deleted", endpoint.substring(0,1).toUpperCase()
                    + endpoint.substring(1))).endResponseMessage()
            .responseMessage().code(400).message("Header label is missing").endResponseMessage()
            .responseMessage().code(404).message(String.format("%s with given label isn't present in the house",
                    endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
            .param().name("label").type(path).description(String.format("%s label to be deleted",
                    endpoint.substring(0,1).toUpperCase() + endpoint.substring(1))).endParam()
            .to("direct:delete" + endpoint);
    }

    /**
     * HTTP PUT request setting.
     *
     * Set the get method and adds openAPI info.
     * This setting can be used for multiple methods if needed
     * @param rd rest definition with the desired route
     * @param description brief openAPI description of the method
     */
    private void buildAsPut(RestDefinition rd, String description) {
        rd
            .description(description)
            .responseMessage().code(200).message(String.format("%s updated", endpoint.substring(0, 1).toUpperCase()
                    + endpoint.substring(1))).endResponseMessage()
            .responseMessage().code(400).message("Invalid body").endResponseMessage()
            .responseMessage().code(404).message(String.format("%s with given label isn't present in the house",
                    endpoint.substring(0, 1).toUpperCase() + endpoint.substring(1))).endResponseMessage()
            .param().name("label").type(path).description(String.format("The label of the updated %s", endpoint))
                .endParam()
            .param().name("body").description("Object, that needs to be updated in the house. "
                    + "Label cannot be changed. If label will differ from path value it will be ignored.").endParam()
            .type(deviceType)
            .outType(deviceType)
            .consumes(MediaType.APPLICATION_JSON_VALUE)
            .to("direct:update" + endpoint);
    }

    /**
     * HTTP GET request handling.
     *
     * Successful request returns JSON for a specific device or JSON array
     * of all devices of certain type. Otherwise, a 404 status code (Not Found)
     * is returned.
     */
    private void handleGet() {
        from("direct:read" + endpoint)
                .routeId("read-" + endpoint + "-route")
                .process(exchange -> {
                    final String label = exchange.getMessage().getHeader(routeDeviceIdentifier).toString();
                    final Device retrievedDevice = house.getDevicesOfType(deviceType).get(label);
                    exchange.getMessage().setBody(retrievedDevice);
                    if (retrievedDevice == null) {
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND); // 404
                        LOGGER.log(LOGLEVEL, String.format("Cannot find device of %s with label %s, returns code 400",
                                deviceType, label));
                    }
                })
                .endRest();

        from("direct:read" + endpoint + "s")
                .routeId("read-" + endpoint + "s-route")
                .process(exchange -> exchange.getMessage().setBody(house.getDevicesOfType(deviceType).values()))
                .endRest();
    }

    /**
     * HTTP POST request handling.
     *
     * Successful request creates an object of a certain type and returns
     * status code 201 (Created). If there is a conflict, status code 409
     * (Conflict) is returned. Otherwise, the request is considered invalid
     * and status code 400 (Bad Request) is returned.
     */
    private void handlePost() {
        from("direct:create" + endpoint)
                .routeId("create-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            final Device deviceToAdd = exchange.getMessage().getBody(deviceType);
                            final Device checkForConflict = house
                                    .getDevicesOfType(deviceType)
                                    .get(deviceToAdd.getLabel());
                            if (checkForConflict != null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CONFLICT);
                                // 409
                                LOGGER.log(LOGLEVEL, String.format("Cannot create device of %s label %s" +
                                        " is already used, return code 409", deviceType, deviceToAdd.getLabel()));
                                return;
                            }
                            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CREATED); // 201
                            house.addDevice(deviceToAdd.getLabel(), deviceToAdd);
                            exchange.getMessage().setBody(house.getDevice(deviceToAdd.getLabel()));
                            exchange.getMessage().setHeader("label", deviceToAdd.getLabel());
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 409"))
                                .log("Created route path for \"${header.label}\" device")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    /**
     * HTTP PUT/PATCH request handling.
     *
     * Successful request updates an object of a certain type. If an object is
     * not found, status code 404 (Not Found) is returned. Otherwise,
     * the request is considered invalid and status code 400 (Bad Request) is
     * returned.
     */
    private void handlePut() {
        from("direct:update" + endpoint)
                .routeId("update-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            final String label = exchange.getMessage().getHeader(routeDeviceIdentifier).toString();
                            Device updatedDevice = exchange.getMessage().getBody(deviceType);

                            try {
                                updatedDevice = updatedDevice.createSimilar(label);
                                house.updateDevice(label, updatedDevice);
                            } catch (IllegalDeviceArgumentException e) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_BAD_REQUEST);
                                // 400
                                exchange.getMessage().setBody(e.getMessage());
                                LOGGER.log(LOGLEVEL, String.format("Cannot update device of %s because label is null," +
                                        " return code 400", deviceType));
                                return;
                            } catch (NoSuchElementException | DifferentDeviceException e) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                exchange.getMessage().setBody(e.getMessage());
                                LOGGER.log(LOGLEVEL, String.format("Cannot update device of %s because device with" +
                                        " label %s is not present in the house or instance of different class," +
                                        " return code 404", deviceType, label));
                                return;
                            }

                            exchange.getMessage().setBody(house.getDevice(label));
                            exchange.getMessage().setHeader("label", label);
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Updated fireplace \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    /**
     * HTTP DELETE request handling.
     *
     * Successful request deletes an object of a certain type. If an object is
     * not found, status code 404 (Not Found) is returned. Otherwise,
     * the request is considered invalid and status code 400 (Bad Request) is
     * returned.
     */
    private void handleDelete() {
        from("direct:delete" + endpoint)
                .routeId("delete-" + endpoint + "-route")
                .choice()
                    .when(header("label").isNotNull())
                        .process(exchange -> {
                            final String label = exchange.getMessage().getHeader(routeDeviceIdentifier).toString();
                            try {
                                house.removeDevice(label);
                            } catch (NoSuchElementException e) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                exchange.getMessage().setBody(e.getMessage());
                                LOGGER.log(LOGLEVEL, String.format("Device with label %s is not present in the house," +
                                        " return code 404", label));
                            }
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Removed device \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/" + endpoint;
    }
}
