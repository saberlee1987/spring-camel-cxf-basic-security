package com.saber.spring_camel_rest_client.routes;

import org.apache.camel.Exchange;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends AbstractRestRouteBuilder{
	
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/hello")
				.get("/sayHello")
				.id("sayHello")
				.description("sayHello")
				.produces(MediaType.TEXT_PLAIN_VALUE)
				.enableCORS(true)
				.route()
				.routeId("sayHello")
				.setBody(constant("Hello World @@@@@@@"))
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200));
	}
}
