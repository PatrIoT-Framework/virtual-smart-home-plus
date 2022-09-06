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

import io.patriot_framework.virtual_smart_home.AppConfig;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 * Routing configuration for localhost:8080.
 */
@Component
public class BaseRoute extends RouteBuilder {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    @Value("${server.port}")
    private String port;
    @Value("${server.host}")
    private String host;
    @Value("${api.title}")
    private String apiTitle;
    private final String apiVersion = "0.0.1";
    private final String apiContextPath = "/api-doc";

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .host(host).port(port)
            .clientRequestValidation(true)
            .bindingMode(RestBindingMode.json)
            .apiContextPath(apiContextPath)
            .apiProperty("api.title", apiTitle).apiProperty("api.version", apiVersion);
    }
}
