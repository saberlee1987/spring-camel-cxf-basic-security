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
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonResponse.class).endResponseMessage()
				.route()
				.routeId(Routes.FIND_ALL_PERSON_ROUTE)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.log("Request for correlation : ${in.header.correlation} , find person find all to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findAll}} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.to("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findAll}}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for correlation : ${in.header.correlation} , find all person with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonResponse.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
