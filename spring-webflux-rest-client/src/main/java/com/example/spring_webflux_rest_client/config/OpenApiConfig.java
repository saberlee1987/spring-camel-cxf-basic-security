package com.example.spring_webflux_rest_client.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
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
    
    @Value(value = "${springdoc.oAuthFlow.authorizationUrl}")
    private String authorizationUrl;
    @Value(value = "${springdoc.oAuthFlow.tokenUrl}")
    private String tokenUrl;
    
    @Bean
    public OpenAPI springShopOpenAPI() {
        String bearerSecuritySchema = "keycloak-authorize";
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
                .addSecurityItem(new SecurityRequirement().addList(bearerSecuritySchema))
                .components(new Components()
                        .addSecuritySchemes(bearerSecuritySchema, new SecurityScheme()
                                .name(bearerSecuritySchema)
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows().authorizationCode(
                                        new OAuthFlow().authorizationUrl(authorizationUrl).tokenUrl(tokenUrl)
                                                .scopes(new Scopes()
                                                        .addString("openid", "openid")
                                                        .addString("read", "read")
                                                        .addString("write", "write")
                                                )
                                )))
                
                )
                .servers(serverList)
                .info(new Info().title(swaggerTitle)
                        .description(swaggerDescription)
                        .version(swaggerVersion));
    }
}
