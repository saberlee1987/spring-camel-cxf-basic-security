package com.saber.spring_camel_rest_client.routes;

import com.saber.spring_camel_rest_client.dto.ErrorResponse;
import com.saber.spring_camel_rest_client.dto.PersonResponse;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class FindAllPersonRoutes extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/person")
				.get("/findAll")
				.description("find all person")
				.id(Routes.FIND_ALL_PERSON_ROUTE_ID)
				.produces(MediaType.APPLICATION_JSON)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.NOT_ACCEPTABLE.value()).message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.BAD_REQUEST.value()).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.FORBIDDEN.value()).message(HttpStatus.FORBIDDEN.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.UNAUTHORIZED.value()).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.GATEWAY_TIMEOUT.value()).message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.responseMessage().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).responseModel(ErrorResponse.class).endResponseMessage()
				.enableCORS(true)
				.bindingMode(RestBindingMode.json)
				.route()
				.routeId(Routes.FIND_ALL_PERSON_ROUTE)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.log("Request for find person find all to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findAll}} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.to("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findAll}}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for find all person with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonResponse.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
