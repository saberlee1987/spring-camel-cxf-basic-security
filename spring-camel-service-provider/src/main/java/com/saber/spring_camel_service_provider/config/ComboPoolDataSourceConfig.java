package com.saber.spring_camel_service_provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.c3p0")
public class ComboPoolDataSourceConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int initialPoolSize;
    private int acquireIncrement;
    private int acquireRetryDelay;
    private int checkTimeout;
    private int maxConnectionAge;
    private int maxIdleTimeout;
    private int maxStatementPerConnection;
    private int idleConnectionTestPeriod;
    private boolean testConnectionOnCheckout;
    private boolean testConnectionOnCheckin;
    private String preferredTestQuery;
    private int numHelperThreads;
}
