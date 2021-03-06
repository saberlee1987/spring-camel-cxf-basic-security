package com.saber.spring_webflux_camel_soap_client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.concurrent.Executors;

@Configuration
@ImportResource(value ={"classpath*:camel.xml"})
public class AppConfig {

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
	public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(){
		NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
		webServerFactory.addServerCustomizers(nettyServerCustomizer());
		return webServerFactory;
	}
	
	private NettyServerCustomizer nettyServerCustomizer(){
		return httpServer -> {
			EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads, Executors.newCachedThreadPool());
			NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
			httpServer =httpServer.accessLog(true);
			httpServer =httpServer.wiretap(true);
			eventLoopGroup.register(nioServerSocketChannel);

			return httpServer.runOn(eventLoopGroup);
		};
	}
}