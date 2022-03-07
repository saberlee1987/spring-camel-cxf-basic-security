package com.saber.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

//@Component
@Slf4j
public class LoggingResponseFilter implements GlobalFilter, Ordered {
	public static final String CORRELATION = "correlation";
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	
		ServerHttpRequest request = exchange.getRequest();
		
		String correlation = request.getHeaders().getFirst(CORRELATION);
		String startTime = request.getHeaders().getFirst("startTime");
		LocalDateTime endTime = LocalDateTime.now();
		assert startTime != null;
		long duration = 0;
		if (startTime!=null){
			LocalDateTime start = LocalDateTime.parse(startTime);
			duration = ChronoUnit.MILLIS.between(start,endTime);
			
		}
		exchange.getResponse().getHeaders().add(CORRELATION,correlation);
		
		
		ServerHttpResponse response = exchange.getResponse();
		
		String bodyObject =exchange.getAttribute("cachedResponseBodyObject");
		log.info("response body ========> {}",bodyObject);
		
		int statusCode = Objects.requireNonNull(response.getStatusCode()).value();
		String url = request.getPath().value();
		String uri = request.getURI().toString();
			JSONObject json = new JSONObject();
		json.put("statusCode",statusCode);
		json.put("uri",uri);
		json.put("url",url);
		json.put("correlation",correlation);
		json.put("startTime",startTime);
		json.put("duration",duration);
		json.put("endTime",endTime.toString());
		json.put("type","response");
		log.info(json.toString());
		
		
		return chain.filter(exchange);
	}
	

	
	@Override
	public int getOrder() {
		return FilterOrderType.POST.getOrder();
	}
}
