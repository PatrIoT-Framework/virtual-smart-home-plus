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

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Device endpoint which allows HTTP GET request only and returns information
 * about all devices.
 */
@Component
public class DeviceRoute extends HouseRoute {

    @Override
    public void configure() throws Exception {
        rest(getRoute())
            .get()
                .description("Returns devices in the house")
                .outType(house.getDevices().getClass())
                .responseMessage().code(200).message("List of devices present in the house returned")
                    .endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .process(exchange -> exchange.getMessage().setBody(house.getDevices()))
            .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/";
    }
}
