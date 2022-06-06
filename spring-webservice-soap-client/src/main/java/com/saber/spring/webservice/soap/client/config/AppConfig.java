package com.saber.spring.webservice.soap.client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Value(value = "${service.person-api.jksFile}")
    private String jksFile;
    @Value(value = "${service.person-api.jksPassword}")
    private String jksPassword;
    @Value(value = "${service.person-api.username}")
    private String username;
    @Value(value = "${service.person-api.password}")
    private String password;
    @Value(value = "${service.person-api.serviceUrl}")
    private String personSoapServiceUrl;

    @Value(value = "${service.person-api.readTimeout}")
    private int readTimeout;
    @Value(value = "${service.person-api.connectTimeout}")
    private int connectTimeout;
    @Value(value = "${service.person-api.connectTimeout}")
    private int connectionTimeToLive;
    @Value(value = "${service.person-api.connectionPerRoute}")
    private int connectionPerRoute;
    @Value(value = "${service.person-api.maxTotalConnection}")
    private int maxTotalConnection;
    @Autowired
    private SoapLoggingInterceptor soapLoggingInterceptor;

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return mapper;
    }

    @Bean
    public CamelContextConfiguration camelContextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
                camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("*"));
        cors.setAllowedMethods(List.of("*"));
        cors.setAllowedHeaders(List.of("*"));
        cors.addAllowedOriginPattern("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("com.saber.spring.webservice.soap.client.dto.wsdl");
        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() throws Exception {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(jaxb2Marshaller());
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        List<ClientInterceptor> clientInterceptors = new ArrayList<>();
        clientInterceptors.add(securityInterceptor());
        clientInterceptors.add(soapLoggingInterceptor);
        ClientInterceptor[] clientInterceptorsArray= new ClientInterceptor[clientInterceptors.size()];
        clientInterceptors.toArray(clientInterceptorsArray);
        webServiceTemplate.setInterceptors(clientInterceptorsArray);
        webServiceTemplate.setDefaultUri(personSoapServiceUrl);
        return webServiceTemplate;
    }

    public HttpComponentsMessageSender httpComponentsMessageSender() throws Exception {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setReadTimeout(readTimeout);
        httpComponentsMessageSender.setConnectionTimeout(connectTimeout);
        httpComponentsMessageSender.setHttpClient(httpClient());
        return httpComponentsMessageSender;
    }

    public HttpClient httpClient() throws Exception {
        return HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory())
                .setMaxConnTotal(maxTotalConnection)
                .setMaxConnPerRoute(connectionPerRoute)
                .setConnectionTimeToLive(connectionTimeToLive, TimeUnit.MILLISECONDS)
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()).build();
    }

    public SSLConnectionSocketFactory sslConnectionSocketFactory() throws Exception {
        // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
        // is thrown: java.security.cert.CertificateException: No name matching localhost found
        return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
    }

    public SSLContext sslContext() throws Exception {
        return SSLContextBuilder.create()
                .loadTrustMaterial(new File(jksFile), jksPassword.toCharArray()).build();
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();

        // Adds "Timestamp" and "UsernameToken" sections in SOAP header
        security.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);

        // Set values for "UsernameToken" sections in SOAP header
        security.setSecurementPasswordType(WSConstants.PW_TEXT);
        security.setSecurementUsername(username);
        security.setSecurementPassword(password);
        return security;
    }
}