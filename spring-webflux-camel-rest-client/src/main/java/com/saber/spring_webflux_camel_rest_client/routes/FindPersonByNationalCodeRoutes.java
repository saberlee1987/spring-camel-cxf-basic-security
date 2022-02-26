package com.saber.spring_webflux_camel_rest_client.routes;


import com.saber.spring_webflux_camel_rest_client.dto.person.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;


@Component
public class FindPersonByNationalCodeRoutes extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();

		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.log("Request for find person by nationalCode ${in.header.nationalCode} to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_METHOD,constant("GET"))
				.toD("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for find person by nationalCode ${in.header.nationalCode} with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
