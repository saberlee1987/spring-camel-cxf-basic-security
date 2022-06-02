package com.saber.spring_camel_rest_client.routes;

import com.saber.spring_camel_rest_client.dto.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UpdatePersonRoute extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/person")
				.put("/update/{nationalCode}")
				.description("update person")
				.id(Routes.UPDATE_PERSON_ROUTE_ID)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonDto.class).example("example1","{\"firstname\": \"saber\",\"lastname\": \"azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}").endResponseMessage()
				.type(PersonDto.class)
				.param().name("nationalCode").type(RestParamType.path).dataType("string").required(true).example("0079028748").endParam()
				.route()
				.routeId(Routes.UPDATE_PERSON_ROUTE)
				.routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
				.to("bean-validator://update-person")
				.validate(header("nationalCode").isNotNull())
				.validate(header("nationalCode").regex("\\d{10}"))
				.to(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
				.log("Request for correlation : ${in.header.correlation} , update person by nationalCode ${in.header.nationalCode} with body ==> ${in.body}  to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.updatePerson}}/${in.header.nationalCode} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
				.marshal().json(JsonLibrary.Jackson,PersonDto.class)
				.toD("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.updatePerson}}/${in.header.nationalCode}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for correlation : ${in.header.correlation} , update person by nationalCode ${in.header.nationalCode}  with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
