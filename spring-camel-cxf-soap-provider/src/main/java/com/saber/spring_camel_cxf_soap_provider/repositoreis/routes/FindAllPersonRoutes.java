package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.exceptions.TimeoutException;
import com.saber.spring_camel_cxf_soap_provider.soap.services.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindAllPersonRoutes extends AbstractRestRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.log("Request for find all person")
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT));
		
		from(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT))
				.routeId(Routes.FIND_ALL_PERSON_ROUTE_GATEWAY_OUT)
				.routeGroup(Routes.FIND_ALL_PERSON_ROUTE_GROUP)
				.circuitBreaker()
					.to("sql:select * from persons?outputClass=" + PersonSoapDto.class.getName())
					.log("Response for find All persons ===> ${in.body}")
					.process(exchange -> {
						String response = exchange.getIn().getBody(String.class);
						response = String.format("{\"response\":%s}", response);
						exchange.getIn().setBody(response);
					})
					.log("Response for find All persons ===> ${in.body}")
					.unmarshal().json(JsonLibrary.Jackson, FindAllPersonsResponse.class)
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.onFallbackViaNetwork()
					.log("Error can not connect to DataBase ")
					.throwException(new TimeoutException("Can not connect to dataBase"))
				.endCircuitBreaker()
				.end();
	}
}
