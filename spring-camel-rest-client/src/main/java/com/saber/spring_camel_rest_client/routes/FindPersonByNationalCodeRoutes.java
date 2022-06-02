package com.saber.spring_camel_rest_client.routes;

import com.saber.spring_camel_rest_client.dto.PersonDto;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindPersonByNationalCodeRoutes extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		rest("/person")
				.get("/find/{nationalCode}")
				.description("find by nationalCode")
				.id(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_ID)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonDto.class).endResponseMessage()
				.param().name("nationalCode").type(RestParamType.path).required(true).dataType("string").example("0079028748").endParam()
				.route()
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.validate(header("nationalCode").isNotNull())
				.validate(header("nationalCode").regex("\\d{10}"))
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY));
		
		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.log("Request for correlation : ${in.header.correlation} , find person by nationalCode ${in.header.nationalCode} to url {{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode} ")
				.to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
				.toD("{{service.person.url}}:{{service.person.port}}{{service.person.baseUrl}}{{service.person.findByNationalCode}}/${in.header.nationalCode}?bridgeEndpoint=true&sslContextParameters=#sslContextParameters")
				.convertBodyTo(String.class)
				.log("Response for correlation : ${in.header.correlation} , find person by nationalCode ${in.header.nationalCode} with body ===> ${in.body}")
				.unmarshal().json(JsonLibrary.Jackson, PersonDto.class)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	}
}
