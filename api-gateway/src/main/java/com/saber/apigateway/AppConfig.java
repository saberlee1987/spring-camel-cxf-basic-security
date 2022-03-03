package com.saber.apigateway;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {
    @Value(value = "${server.netty.threads}")
    private Integer threads;

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setMaxAge(30000L);
        corsConfig.addAllowedMethod("GET,POST,PUT,DELETE,OPTIONS,PATCH,HEAD,TRACE");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
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
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(){
        NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();
        webServerFactory.addServerCustomizers(nettyServerCustomizer());
        return webServerFactory;
    }

    private NettyServerCustomizer nettyServerCustomizer(){
        return httpServer -> {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads,Executors.newCachedThreadPool());
            NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
            httpServer = httpServer.accessLog(true);
            httpServer=httpServer.wiretap(true);
            eventLoopGroup.register(nioServerSocketChannel);
            return httpServer.runOn(eventLoopGroup);
        };
    }
}
