package com.saber.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomBearerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> {
            HttpStatus status = this.getStatus(ex);
            Map<String, String> parameters = this.createParameters(ex);
            String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set("WWW-Authenticate", wwwAuthenticate);
            response.setStatusCode(status);
            return response.setComplete();
        });
    }
    private HttpStatus getStatus(AuthenticationException authException) {
        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException)authException).getError();
            if (error instanceof BearerTokenError) {
                return ((BearerTokenError)error).getHttpStatus();
            }
        }
        return HttpStatus.UNAUTHORIZED;
    }

    private Map<String, String> createParameters(AuthenticationException authException) {
        Map<String, String> parameters = new LinkedHashMap<>();

        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException)authException).getError();
            parameters.put("error", error.getErrorCode());
            if (StringUtils.hasText(error.getDescription())) {
                parameters.put("error_description", error.getDescription());
            }

            if (StringUtils.hasText(error.getUri())) {
                parameters.put("error_uri", error.getUri());
            }

            if (error instanceof BearerTokenError) {
                BearerTokenError bearerTokenError = (BearerTokenError)error;
                if (StringUtils.hasText(bearerTokenError.getScope())) {
                    parameters.put("scope", bearerTokenError.getScope());
                }
            }
        }

        return parameters;
    }

    private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
        StringBuilder wwwAuthenticate = new StringBuilder();
        wwwAuthenticate.append("Bearer");
        if (!parameters.isEmpty()) {
            wwwAuthenticate.append(" ");
            int i = 0;

            for(Iterator<Map.Entry<String, String>> var3 = parameters.entrySet().iterator(); var3.hasNext(); ++i) {
                Map.Entry<String, String> entry = var3.next();
                wwwAuthenticate.append((String)entry.getKey()).append("=\"").append((String)entry.getValue()).append("\"");
                if (i != parameters.size() - 1) {
                    wwwAuthenticate.append(", ");
                }
            }
        }

        return wwwAuthenticate.toString();
    }
}
