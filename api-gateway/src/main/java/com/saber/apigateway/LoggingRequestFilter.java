package com.saber.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingRequestFilter implements GlobalFilter, Ordered {
	public static final String CORRELATION = "correlation";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		String correlation = "";
		correlation = exchange.getAttribute(CORRELATION);
		if (correlation==null) {
			correlation = generateCorrelationId();
		}
		request = exchange.getRequest()
				.mutate()
				.header(CORRELATION, correlation)
				.build();
		return chain.filter(exchange.mutate().request(request).build());
	}


	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
	
	@Override
	public int getOrder() {
		return FilterOrderType.PRE.getOrder();
	}
}
