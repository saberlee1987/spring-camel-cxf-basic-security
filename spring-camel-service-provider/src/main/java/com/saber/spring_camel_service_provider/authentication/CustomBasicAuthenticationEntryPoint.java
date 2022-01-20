package com.saber.spring_camel_service_provider.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.spring_camel_service_provider.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    private final ObjectMapper mapper;

    @Value(value = "${cxf.path}")
    private String soapServicePath;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith(soapServicePath)) {
            String errorResponse =
                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                            "<soap:Body>" +
                            "<error>" +
                            "<code>" +
                            "401" +
                            "</code>" +
                            "<message>" +
                            "401 UNAUTHORIZED" +
                            "</message>" +
                            "<originalMessage>" +
                            "username or password invalid" +
                            "</originalMessage>" +
                            "</error>"
                            + "</soap:Body>"
                            + "</soap:Envelope>";
            writer.println(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(7);
            errorResponse.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.UNAUTHORIZED.value(), authException.getLocalizedMessage()));
            try {
                writer.println(mapper.writeValueAsString(errorResponse));
            } catch (Exception ex) {
                writer.println(errorResponse);
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("saber");
        super.afterPropertiesSet();
    }
}
