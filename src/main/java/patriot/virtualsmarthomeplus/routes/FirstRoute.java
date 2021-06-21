package patriot.virtualsmarthomeplus.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class FirstRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet")
                .host("localhost").port(8080)
                .bindingMode(RestBindingMode.auto);

        rest("/data")
                .get("/").produces("text/plain")
                .route().routeId("first-route")
                .transform(simple("Success"))
                .endRest();
    }
}
