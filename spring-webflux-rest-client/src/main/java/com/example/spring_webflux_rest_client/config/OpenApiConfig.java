package com.example.spring_webflux_rest_client.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value(value = "${service.swagger.version}")
    private String swaggerVersion;
    @Value(value = "${service.swagger.title}")
    private String swaggerTitle;
    @Value(value = "${service.swagger.description}")
    private String swaggerDescription;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(swaggerTitle)
                        .description(swaggerDescription)
                        .version(swaggerVersion));
    }
}
