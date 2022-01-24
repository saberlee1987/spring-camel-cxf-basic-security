package com.saber.spring_camel_cxf_soap_provider.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.beans.PropertyVetoException;

@Configuration
@RequiredArgsConstructor
public class DataBaseConfig {
	
	
	private final ComboPoolDataSourceConfig comboConfig;
	

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
	@Bean(value = "sql")
	public SqlComponent sql() throws PropertyVetoException {
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource());
		sql.setUsePlaceholder(true);
		return sql;
	}
	
}