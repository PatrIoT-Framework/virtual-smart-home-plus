package io.patriot_framework.virtual_smart_home.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class BaseRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .host("localhost").port(8080)
                .bindingMode(RestBindingMode.auto);
    }
}
