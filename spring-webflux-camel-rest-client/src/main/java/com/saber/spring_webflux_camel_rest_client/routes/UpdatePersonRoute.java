package com.saber.spring_webflux_camel_rest_client.routes;


import com.saber.spring_webflux_camel_rest_client.dto.person.PersonDto;
import com.saber.spring_webflux_camel_rest_client.dto.person.UpdatePersonResponseDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class UpdatePersonRoute extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();

		from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
				.log("Request for correlation : ${in.header.correlation} , update person by nationalCode ${in.header.nationalCode} with body ==> ${in.body}  to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.updatePerson}}/${in.header.nationalCode} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
				.marshal().json(JsonLibrary.Jackson, PersonDto.class)
				.setHeader(Exchange.HTTP_METHOD,constant("PUT"))
				.toD("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.updatePerson}}/${in.header.nationalCode}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for correlation : ${in.header.correlation} , update person by nationalCode ${in.header.nationalCode}  with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, UpdatePersonResponseDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
