package com.saber.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewaySecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange((exchanges) -> exchanges
                        .pathMatchers("/actuators/**",
                                "/services/person-soap-client-docs/v2/api-docs"
                                , "/services/camel-rest-client/v3/api-docs"
                                , "/services/webflux-camel-rest-client-docs/v3/api-docs"
                                , "/services/webflux-rest-client-docs/v3/api-docs"
                                , "/services/webflux-camel-soap-client-docs/v3/api-docs"
                                , "/services/spring-rest-client-docs/v3/api-docs"
                                , "/services/spring-rest-client-docs/v3/api-docs"
                                , "/webjars/**",
                                "/v3/api-docs/**"
                                , "/swagger-ui**",
                                "/swagger-ui/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> {
                    oAuth2ResourceServerSpec.jwt(jwt ->
                            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
                })
        ;

        return http.build();
    }

    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
    }
}
