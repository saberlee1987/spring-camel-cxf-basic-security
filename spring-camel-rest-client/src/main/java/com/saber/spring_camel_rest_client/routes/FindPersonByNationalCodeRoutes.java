package com.saber.spring_camel_rest_client.routes;

import com.saber.spring_camel_rest_client.dto.ErrorResponse;
import com.saber.spring_camel_rest_client.dto.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class FindPersonByNationalCodeRoutes extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/person")
				.get("/find/{nationalCode}")
				.description("find by nationalCode")
				.id(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_ID)
				.produces(MediaType.APPLICATION_JSON)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonDto.class).endResponseMessage()
				.responseMessage().code(HttpStatus.NOT_ACCEPTABLE.value()).message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.BAD_REQUEST.value()).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.FORBIDDEN.value()).message(HttpStatus.FORBIDDEN.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.UNAUTHORIZED.value()).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.GATEWAY_TIMEOUT.value()).message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.param().name("nationalCode").type(RestParamType.path).required(true).dataType("string").example("0079028748").endParam()
				.enableCORS(true)
				.bindingMode(RestBindingMode.json)
				.route()
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.log("Request for find person by nationalCode ${in.header.nationalCode} to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.toD("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for find person by nationalCode ${in.header.nationalCode} with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
