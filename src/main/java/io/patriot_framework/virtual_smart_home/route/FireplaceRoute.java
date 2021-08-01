package io.patriot_framework.virtual_smart_home.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class FireplaceRoute extends BaseRoute {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet")
                .host("localhost").port(8080)
                .bindingMode(RestBindingMode.auto);

        rest("/fireplace")
                .get("/")
                .produces("application/json")
                .route().routeId("fireplace-route")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getMessage().setBody("{\"label\": \"fireplace\"}");
                    }
                })
                .endRest();
    }
}
