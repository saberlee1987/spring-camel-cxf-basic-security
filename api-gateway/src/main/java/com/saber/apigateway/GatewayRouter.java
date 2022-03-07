package com.saber.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Configuration
@Slf4j
public class GatewayRouter {
	
	public static final String CORRELATION = "correlation";
	
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route("person-soap-client", route ->
						route.path("/services/person-soap-client/**")
								.filters(this::getFilterSpec)
								.uri("lb://person-soap-client"))
				
				.route("spring-camel-rest-client", route ->
						route.path("/services/camel-rest-client/**")
								.filters(this::getFilterSpec)
								.uri("lb://spring-camel-rest-client"))
				
				.route("webflux-camel-rest-client", route ->
						route.path("/services/webflux-camel-rest-client/**")
								.filters(this::getFilterSpec)
								.uri("lb://webflux-camel-rest-client"))
				
				.route("spring-webflux-rest-client", route ->
						route.path("/services/webflux-rest-client/**")
								.filters(this::getFilterSpec)
								.uri("lb://spring-webflux-rest-client"))
				
				.route("webflux-camel-soap-client", route ->
						route.path("/services/webflux-camel-soap-client/**")
								.filters(this::getFilterSpec)
								.uri("lb://webflux-camel-soap-client"))
				
				.build();
	}
	
	private GatewayFilterSpec getFilterSpec(GatewayFilterSpec f) {
		return f.modifyRequestBody(String.class, String.class,
				(webExchange, originalBody) -> {
					if (originalBody != null) {
						webExchange.getAttributes().put("cachedRequestBodyObject", originalBody);
						logRequest(webExchange);
						return Mono.just(originalBody);
					} else {
						logRequest(webExchange);
						return Mono.empty();
					}
				})
				.modifyResponseBody(String.class, String.class,
						(webExchange, originalBody) -> {
							if (originalBody != null) {
								webExchange.getAttributes().put("cachedResponseBodyObject", originalBody);
								logResponse(webExchange);
								return Mono.just(originalBody);
							} else {
								logResponse(webExchange);
								return Mono.empty();
							}
						});
	}
	
	
	private void logRequest(ServerWebExchange exchange) {
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
		
		String bodyObject = exchange.getAttribute("cachedRequestBodyObject");
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
		if (bodyObject != null)
			json.put("body", bodyObject);
		
		
		log.info(json.toString());
	}
	
	private void logResponse(ServerWebExchange exchange) {
		
		ServerHttpRequest request = exchange.getRequest();
		
		String correlation = request.getHeaders().getFirst(CORRELATION);
		String startTime = request.getHeaders().getFirst("startTime");
		LocalDateTime endTime = LocalDateTime.now();
		assert startTime != null;
		long duration = 0;
		LocalDateTime start = LocalDateTime.parse(startTime);
		duration = ChronoUnit.MILLIS.between(start, endTime);
		exchange.getResponse().getHeaders().add(CORRELATION, correlation);
	
		ServerHttpResponse response = exchange.getResponse();
		
		Collection<List<String>> headers = response.getHeaders().values();
		
		String bodyObject = exchange.getAttribute("cachedResponseBodyObject");
		int statusCode = Objects.requireNonNull(response.getStatusCode()).value();
		String url = request.getPath().value();
		String uri = request.getURI().toString();
		JSONObject json = new JSONObject();
		json.put("statusCode", statusCode);
		json.put("uri", uri);
		json.put("url", url);
		json.put("headers", headers);
		json.put("correlation", correlation);
		json.put("startTime", startTime);
		json.put("duration", duration);
		json.put("endTime", endTime.toString());
		json.put("type", "response");
		if (bodyObject != null)
			json.put("body", bodyObject);
		
		log.info(json.toString());
	}
	
	private boolean hasCorrelationId(HttpHeaders header) {
		return header.containsKey(CORRELATION);
	}
	
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
}
