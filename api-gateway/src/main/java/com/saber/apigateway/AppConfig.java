package com.saber.apigateway;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Configuration
public class AppConfig  {
    @Value(value = "${server.netty.threads}")
    private Integer threads;

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("*"));
        cors.setMaxAge(30000L);
        cors.setAllowedHeaders(List.of("GET , POST , PUT , DELETE , OPTIONS , PATCH , HEAD , TRACE "));
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

    @Bean
    public OpenApiCustomiser sortTagsAlphabetically() {
        return openApi -> openApi
                .setTags(openApi.getTags()
                .stream()
                .sorted(Comparator.comparing(tag -> StringUtils.stripAccents(tag.getName())))
                .collect(Collectors.toList()));
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
