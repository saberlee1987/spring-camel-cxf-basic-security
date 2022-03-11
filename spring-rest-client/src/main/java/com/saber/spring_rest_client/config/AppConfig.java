package com.saber.spring_rest_client.config;

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
import org.apache.http.conn.DnsResolver;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

@Configuration
public class AppConfig {

    @Value(value = "${service.keystore.jksFile}")
    private String jksFilePath;
    @Value(value = "${service.keystore.jksPassword}")
    private String jksPassword;

    @Value(value = "${spring.restTemplate.connectionPerRoute}")
    private Integer connectionPerRoute;
    @Value(value = "${spring.restTemplate.maxTotalConnection}")
    private Integer maxTotalConnection;
    @Value(value = "${spring.restTemplate.readTimeout}")
    private Integer readTimeout;
    @Value(value = "${spring.restTemplate.connectTimeout}")
    private Integer connectTimeout;

//    @Value(value = "${spring.restTemplate.connectionTimeToLive}")
//    private Integer connectionTimeToLive;

//    @Value(value = "${service.authorization.username}")
//    private String authorizationUsername;
//    @Value(value = "${service.authorization.password}")
//    private String authorizationPassword;

    @Value(value = "${spring.restTemplate.dnsResolver}")
    private String dnsResolver ;
    @Value(value = "${spring.restTemplate.dnsHost}")
    private String dnsHost;

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
            public void afterApplicationStart(CamelContext camelContext) {  }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

        SSLContext sslContext = sslContext();
         HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnPerRoute(connectionPerRoute)
                .setMaxConnTotal(maxTotalConnection)
//                 .setConnectionTimeToLive()
               // .setDnsResolver(dnsResolver())
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory httpComponent =
                            new HttpComponentsClientHttpRequestFactory();

        httpComponent.setConnectTimeout(connectTimeout);
        httpComponent.setReadTimeout(readTimeout);
        httpComponent.setConnectionRequestTimeout(readTimeout);

        httpComponent.setHttpClient(httpClient);

        RestTemplate restTemplate = builder
                //.basicAuthentication(authorizationUsername,authorizationPassword)
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                  .build();

        //restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpComponent));
        restTemplate.setRequestFactory(httpComponent);
        // set interceptor to RestTemplate
       // List<ClientHttpRequestInterceptor> requestInterceptors = new ArrayList<>();
       // requestInterceptors.add(new LoggingRequestInterceptor());
        //restTemplate.setInterceptors(requestInterceptors);
        return restTemplate;
    }

    private SSLContext sslContext() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        SecureRandom secureRandom = new SecureRandom();

//        X509TrustManager tm = new X509TrustManager() {
//
//            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//            }
//
//            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//            }
//
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };

        //         load keyStore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(jksFilePath),jksPassword.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore,jksPassword.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

//        sslContext.init(null,new TrustManager[]{tm},secureRandom);
        sslContext.init(keyManagers,trustManagers,secureRandom);
        SSLContext.setDefault(sslContext);
        return sslContext;
    }


    private DnsResolver dnsResolver(){
        return new SystemDefaultDnsResolver(){
            @Override
            public InetAddress[] resolve(String host) throws UnknownHostException {
                if (host.equals(dnsResolver)){
                    return new InetAddress[]{InetAddress.getByAddress(dnsHost.getBytes(StandardCharsets.UTF_8))};
                }
                return super.resolve(host);
            }
        };
    }
}
