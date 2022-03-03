package com.saber.spring_webflux_camel_soap_client.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value(value = "${service.swagger.version}")
    private String swaggerVersion;
    @Value(value = "${service.swagger.title}")
    private String swaggerTitle;
    @Value(value = "${service.swagger.description}")
    private String swaggerDescription;
    
    @Value(value = "${service.swagger.host}")
    private String swaggerHost;
    @Value(value = "${server.port}")
    private int serverPort;
    @Value(value = "${service.swagger.api-gateway.host}")
    private String apiGatewayHost;
    @Value(value = "${service.swagger.api-gateway.port}")
    private int apiGatewayPort;

    @Bean
    public OpenAPI springShopOpenAPI() {
        List<Server> serverList = new ArrayList<>();
    
        Server serviceServer = new Server();
        serviceServer.setUrl(String.format("http://%s:%d", swaggerHost, serverPort));
        serviceServer.setDescription(swaggerDescription);
    
    
        Server apiGatewayServer = new Server();
        apiGatewayServer.setUrl(String.format("http://%s:%d", apiGatewayHost, apiGatewayPort));
        apiGatewayServer.setDescription("api-gateway");
    
        serverList.add(serviceServer);
        serverList.add(apiGatewayServer);
        return new OpenAPI()
                .components(new Components())
                .servers(serverList)
                .info(new Info().title(swaggerTitle)
                        .description(swaggerDescription)
                        .version(swaggerVersion));
    }
}
