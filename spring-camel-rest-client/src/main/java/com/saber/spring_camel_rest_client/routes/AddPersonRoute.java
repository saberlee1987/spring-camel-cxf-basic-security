package com.saber.spring_camel_rest_client.routes;

import com.saber.spring_camel_rest_client.dto.AddPersonResponseDto;
import com.saber.spring_camel_rest_client.dto.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AddPersonRoute extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/person")
				.post("/add")
				.description("add person")
				.id(Routes.ADD_PERSON_ROUTE_ID)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(AddPersonResponseDto.class).example("example1","{\"firstname\": \"saber\",\"lastname\": \"azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}").endResponseMessage()
				.type(PersonDto.class)
				.route()
				.routeId(Routes.ADD_PERSON_ROUTE)
				.routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
				.to("bean-validator://add-person")
				.to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.ADD_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
				.log("Request for correlation : ${in.header.correlation} , add person with body ==> ${in.body}  to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.addPerson}} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.ADD_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
				.marshal().json(JsonLibrary.Jackson,PersonDto.class)
				.to("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.addPerson}}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for correlation : ${in.header.correlation} , add person  with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, AddPersonResponseDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
