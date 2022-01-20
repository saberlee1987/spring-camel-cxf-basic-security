package com.saber.spring_camel_service_provider.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
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
    @Value(value = "${springdoc.oAuthFlow.authorizationUrl}")
    private String authorizationUrl;
    @Value(value = "${springdoc.oAuthFlow.tokenUrl}")
    private String tokenUrl;

    @Bean
    public OpenAPI springShopOpenAPI() {
        String securitySchema = "basicScheme";
        String bearerSecuritySchema = "keycloak-authorize";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchema))
                .addSecurityItem(new SecurityRequirement().addList(bearerSecuritySchema))
                .components(new Components()
                        .addSecuritySchemes(securitySchema, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("basic"))
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
                .info(new Info().title(swaggerTitle)
                        .description(swaggerDescription)
                        .version(swaggerVersion)
                );
    }
}
