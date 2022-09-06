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

import io.patriot_framework.virtual_smart_home.house.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * House endpoint which allows HTTP GET request only and returns information
 * about the whole {@code House} object.
 */
@Component
public class HouseRoute extends BaseRoute {

    @Autowired House house;

    @Override
    public void configure() throws Exception {
        rest(getRoute())
            .get()
                .description("Returns basic house info")
                .outType(House.class)
                .responseMessage().code(200).message("House object with basic info returned").endResponseMessage()
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .process(exchange -> exchange.getMessage().setBody(house))
            .endRest();
    }

    protected String getRoute() {
        return "/house/";
    }
}
