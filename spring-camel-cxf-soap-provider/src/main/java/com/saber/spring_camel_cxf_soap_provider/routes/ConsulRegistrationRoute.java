package com.saber.spring_camel_cxf_soap_provider.routes;

import com.orbitz.consul.model.agent.ImmutableRegistration;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.consul.ConsulConstants;
import org.apache.camel.component.consul.endpoint.ConsulAgentActions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsulRegistrationRoute extends RouteBuilder {

    @Value(value = "${camel.cloud.consul.service-host}")
    private String consulHost;
    @Value(value = "${camel.cloud.consul.id}")
    private String consulId;
    @Value(value = "${server.port}")
    private int serverPort;

    @Override
    public void configure() throws Exception {

        from("timer://consul-registration?fixedRate=true&period={{camel.cloud.consul.registrationScheduled}}")
                .autoStartup(true)
                .startupOrder(1)
                .threads().threadName(Routes.CONSUL_REGISTRATION_ROUTE)
                .routeId(Routes.CONSUL_REGISTRATION_ROUTE)
                .routeGroup(Routes.CONSUL_REGISTRATION_ROUTE_GROUP)
                .setBody().constant(ImmutableRegistration.builder()
                        .name(consulId).id(consulId).address(consulHost).port(serverPort).build())
                .setHeader(ConsulConstants.CONSUL_ACTION, constant(ConsulAgentActions.REGISTER))
                .doTry()
                    .to("consul:agent?url={{camel.cloud.consul.url}}")
                    .log("{{camel.cloud.consul.id}} Register to consul to url {{camel.cloud.consul.url}}")
                .endDoTry()
                .doCatch(Exception.class)
                     .log(LoggingLevel.ERROR,"Sorry can not register service to consul with error ===> "+exceptionMessage())
                .doFinally()
                    .log("doFinally block ............")
                .end();
    }
}
