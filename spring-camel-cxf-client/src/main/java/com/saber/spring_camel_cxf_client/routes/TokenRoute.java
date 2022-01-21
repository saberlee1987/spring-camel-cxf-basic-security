package com.saber.spring_camel_cxf_client.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TokenRoute extends AbstractRestRouteBuilder {

    @Value(value = "${service.person-api.username}")
    private String username;
    @Value(value = "${service.person-api.password}")
    private String password;

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
                .routeId(Routes.ADD_TOKEN_ROUTE)
                .routeGroup(Routes.PERSON_CLIENT_ROUTE_GROUP)
                .process(exchange -> {
                 String authorization = String.format("Basic %s", Base64.getEncoder().encodeToString( String.format("%s:%s",username,password).getBytes(StandardCharsets.UTF_8)    ));
                 exchange.getIn().setHeader("Authorization",authorization);
                })
                .log("Add token to header");
    }
}
