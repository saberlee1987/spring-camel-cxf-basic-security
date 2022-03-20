//package com.saber.apigateway;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//
//@Configuration
//@EnableWebSecurity
//public class ApiGatewaySecurityConfig  extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.cors().disable()
//                .csrf().disable()
//                .authorizeRequests().mvcMatchers("/actuators/**","/swagger-ui**","/swagger-ui/**").permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and().oauth2ResourceServer().jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
//    }
//
//    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
//        return jwtConverter;
//    }
//}
