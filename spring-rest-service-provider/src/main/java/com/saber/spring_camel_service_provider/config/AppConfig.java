package com.saber.spring_camel_service_provider.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.beans.PropertyVetoException;
import java.util.List;

@Configuration
public class AppConfig {
    private final ComboPoolDataSourceConfig comboConfig;
    public AppConfig(ComboPoolDataSourceConfig comboConfig) {
        this.comboConfig = comboConfig;
    }

    @Bean(value = "personDataSource")
    @Primary
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(comboConfig.getDriverClassName());
        dataSource.setJdbcUrl(comboConfig.getUrl());
        dataSource.setUser(comboConfig.getUsername());
        dataSource.setPassword(comboConfig.getPassword());

        dataSource.setPreferredTestQuery(comboConfig.getPreferredTestQuery());
        dataSource.setMinPoolSize(comboConfig.getMinPoolSize());
        dataSource.setMaxPoolSize(comboConfig.getMaxPoolSize());
        dataSource.setAcquireIncrement(comboConfig.getAcquireIncrement());
        dataSource.setCheckoutTimeout(comboConfig.getCheckTimeout());
        dataSource.setInitialPoolSize(comboConfig.getInitialPoolSize());
        dataSource.setAcquireRetryDelay(comboConfig.getAcquireRetryDelay());
        dataSource.setMaxConnectionAge(comboConfig.getMaxConnectionAge());
        dataSource.setMaxIdleTime(comboConfig.getMaxIdleTimeout());
        dataSource.setMaxStatementsPerConnection(comboConfig.getMaxStatementPerConnection());
        dataSource.setIdleConnectionTestPeriod(comboConfig.getIdleConnectionTestPeriod());
        dataSource.setTestConnectionOnCheckout(comboConfig.isTestConnectionOnCheckout());
        dataSource.setTestConnectionOnCheckin(comboConfig.isTestConnectionOnCheckin());
        dataSource.setNumHelperThreads(comboConfig.getNumHelperThreads());
        return dataSource;
    }

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
    public Gson gson(){
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addAllowedOriginPattern("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}