package com.saber.spring_webflux_camel_rest_client.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TokenRoute extends AbstractRestRouteBuilder {
	
	@Value("${service.authorization.username}")
	private String username;
	@Value("${service.authorization.password}")
	private String password;
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		from(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.routeId(Routes.ADD_TOKEN_ROUTE)
				.routeGroup(Routes.ADD_TOKEN_ROUTE_GROUP)
				.setHeader(Headers.Authorization,
						constant(String.format("Basic %s",
								Base64.getEncoder().encodeToString(
										String.format("%s:%s", username, password).getBytes(StandardCharsets.UTF_8)
								))));
	}
}
