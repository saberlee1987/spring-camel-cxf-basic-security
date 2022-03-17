package com.saber.spring_webflux_camel_rest_client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.support.jsse.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class AppConfig {
	@Value(value = "${service.keystore.jksFile}")
	private String jksFilePath;
	@Value(value = "${service.keystore.jksPassword}")
	private String jksPassword;

	@Value(value = "${server.netty.threads}")
	private Integer threads;

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
		
		mapper.findAndRegisterModules();
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
			public void afterApplicationStart(CamelContext camelContext) { }
		};
	}

	@Bean(value = "sslContextParameters")
	public SSLContextParameters sslContextParameters() throws Exception {

		SSLContextParameters sslContextParameters = new SSLContextParameters();

		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(jksFilePath), jksPassword.toCharArray());

		KeyManagersParameters keyManagersParameters = new KeyManagersParameters();

		KeyStoreParameters keyStoreParameters = new KeyStoreParameters();
		keyStoreParameters.setResource(jksFilePath);
		keyStoreParameters.setPassword(jksPassword);
		keyStoreParameters.setType("JKS");
		keyManagersParameters.setKeyStore(keyStoreParameters);
		keyManagersParameters.setKeyPassword(jksPassword);

		// create trust Store manager
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
													.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(keyStore);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

		TrustManagersParameters trustManagersParameters = new TrustManagersParameters();

		trustManagersParameters.setKeyStore(keyStoreParameters);
		trustManagersParameters.setTrustManager(trustManagers[0]);

		FilterParameters filter = new FilterParameters();
		filter.getInclude().add(".*");

		SSLContextClientParameters sslContextClientParameters = new SSLContextClientParameters();
		sslContextClientParameters.setCipherSuitesFilter(filter);


		sslContextParameters.setClientParameters(sslContextClientParameters);
		sslContextParameters.setKeyManagers(keyManagersParameters);
		sslContextParameters.setTrustManagers(trustManagersParameters);
		return sslContextParameters;
	}

	@Bean
	public CorsWebFilter corsWebFilter(){
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(List.of("*"));
		cors.setMaxAge(30000L);
		cors.addAllowedMethod("*");
		cors.addAllowedHeader("*");
		cors.addAllowedOriginPattern("*");
		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cors);

		return new CorsWebFilter(source);
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(){
		NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
		webServerFactory.addServerCustomizers(nettyServerCustomizer());
		return webServerFactory;
	}

	private NettyServerCustomizer nettyServerCustomizer(){
		return httpServer -> {
			EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads,Executors.newCachedThreadPool());
			httpServer = httpServer.accessLog(true);
			httpServer=httpServer.wiretap(true);
			NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
			eventLoopGroup.register(nioServerSocketChannel);
			return httpServer.runOn(eventLoopGroup);
		};
	}
}