package com.example.spring_webflux_rest_client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class AppConfig {
	@Value(value = "${service.keystore.jksFile}")
	private String jksFilePath;
	@Value(value = "${service.keystore.jksPassword}")
	private String jksPassword;
	@Value(value = "${service.authorization.username}")
	private String username;
	@Value(value = "${service.authorization.password}")
	private String password;
	
	@Value(value = "${service.person.url}")
	private String personUrl;
	@Value(value = "${service.person.port}")
	private String personPort;
	@Value(value = "${service.person.timeout}")
	private Long timeout;
	@Value(value = "${service.person.maxConnection}")
	private Integer maxConnection;
	
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
	public WebClient webClient() {
		
		HttpClient httpClient = HttpClient
				.create(ConnectionProvider.create("person connection",maxConnection))
				.responseTimeout(Duration.ofMillis(timeout))
				.secure(t -> {
					try {
						t.sslContext(sslContext());
					} catch (Exception e) {
						log.error("Error for {}",e.getLocalizedMessage());
					}
				});
		
		return WebClient.builder()
				.defaultHeader("authorization", basicAuthorization())
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.baseUrl(String.format("%s:%s",personUrl,personPort))
				.build();
	}
	
	private String basicAuthorization() {
		return String.format("Basic %s", Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes(StandardCharsets.UTF_8)));
	}
	
	private SslContext sslContext() throws Exception {
		KeyStore personKeyStore = KeyStore.getInstance("JKS");
		personKeyStore.load(new FileInputStream(jksFilePath), jksPassword.toCharArray());
		
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(personKeyStore, jksPassword.toCharArray());
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(personKeyStore);
		
		return SslContextBuilder.forClient()
				.keyManager(keyManagerFactory)
				.trustManager(trustManagerFactory)
				.startTls(true)
				.build();
	}

	@Bean
	public CorsWebFilter corsWebFilter(){
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*"));
		corsConfig.setMaxAge(8000L);
		corsConfig.addAllowedMethod("POST, GET, OPTIONS, PUT, DELETE");
		corsConfig.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}
	@Bean
	public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(){
		NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
		webServerFactory.addServerCustomizers(nettyServerCustomizer());
		return webServerFactory;
	}
	
	private NettyServerCustomizer nettyServerCustomizer(){
		return httpServer -> {
			SelectorProvider selectorProvider = SelectorProvider.provider();
			SelectStrategyFactory selectStrategyFactory = DefaultSelectStrategyFactory.INSTANCE;
			EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads, Executors.newCachedThreadPool(),selectorProvider,selectStrategyFactory);
			NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
			eventLoopGroup.register(nioServerSocketChannel);
			return httpServer.runOn(eventLoopGroup);
		};
	}
}