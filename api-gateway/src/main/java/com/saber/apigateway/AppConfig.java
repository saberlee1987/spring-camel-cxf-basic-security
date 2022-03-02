package com.saber.apigateway;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.nio.channels.spi.SelectorProvider;
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
