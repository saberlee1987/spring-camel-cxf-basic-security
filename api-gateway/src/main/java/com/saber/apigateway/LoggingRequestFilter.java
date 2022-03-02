package com.saber.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Component
@Slf4j
public class LoggingRequestFilter implements GlobalFilter, Ordered {
	public static final String CORRELATION = "correlation";
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		String correlation = "";
		if (hasCorrelationId(headers)) {
			correlation = headers.getFirst(CORRELATION);
		} else {
			correlation = generateCorrelationId();
		}
		LocalDateTime startTime = LocalDateTime.now();
		request = exchange.getRequest()
				.mutate()
				.header(CORRELATION, correlation)
				.header("startTime", String.valueOf(startTime))
				.build();
		
		MultiValueMap<String, String> queryParams = request.getQueryParams();
		HttpHeaders requestHeaders = request.getHeaders();
		
		Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
		String url = request.getPath().value();
		JSONObject json = new JSONObject();
		json.put("correlation", correlation);
		json.put("uri", request.getURI());
		json.put("url", url);
		json.put("queryParams", queryParams);
		json.put("headers", requestHeaders);
		json.put("path", request.getPath());
		json.put("serviceName", route.getId() == null ? "unknown service" : route.getId());
		json.put("ip", request.getRemoteAddress());
		json.put("startTime", startTime);
		json.put("type", "request");
	
		
		log.info(json.toString());
		return chain.filter(exchange.mutate().request(request).build());
	}
	
	private boolean hasCorrelationId(HttpHeaders header) {
		return header.containsKey(CORRELATION);
	}
	
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
	
	@Override
	public int getOrder() {
		return FilterOrderType.PRE.getOrder();
	}
}
