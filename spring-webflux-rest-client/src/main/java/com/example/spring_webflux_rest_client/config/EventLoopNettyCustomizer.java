package com.example.spring_webflux_rest_client.config;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executors;
@Component
public class EventLoopNettyCustomizer implements NettyServerCustomizer {
    @Value(value = "${server.netty.threads}")
    private Integer threads;
    @Override
    public HttpServer apply(HttpServer httpServer) {
        SelectorProvider selectorProvider = SelectorProvider.provider();
        SelectStrategyFactory selectStrategyFactory =DefaultSelectStrategyFactory.INSTANCE;
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(threads, Executors.newCachedThreadPool(),selectorProvider,selectStrategyFactory);
        NioServerSocketChannel nioServerSocketChannel = new NioServerSocketChannel();
        eventLoopGroup.register(nioServerSocketChannel);
        return httpServer.runOn(eventLoopGroup);
    }
}
