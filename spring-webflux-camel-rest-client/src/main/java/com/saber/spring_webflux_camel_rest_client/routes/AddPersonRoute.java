package com.saber.spring_webflux_camel_rest_client.routes;

import com.saber.spring_webflux_camel_rest_client.dto.person.AddPersonResponseDto;
import com.saber.spring_webflux_camel_rest_client.dto.person.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class AddPersonRoute extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();

		from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.ADD_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
				.log("Request for add person with body ==> ${in.body}  to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.addPerson}} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.ADD_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
				.marshal().json(JsonLibrary.Jackson,PersonDto.class)
				.setHeader(Exchange.HTTP_METHOD,constant("POST"))
				.to("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.addPerson}}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for add person  with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, AddPersonResponseDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
