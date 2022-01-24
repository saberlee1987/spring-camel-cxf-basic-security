package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.saber.spring_camel_cxf_soap_provider.repositoreis.routes.Routes;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonSoapRoute extends RouteBuilder {
	
	@Autowired
	private CxfEndpoint cxfEndpoint;
	
	@Override
	public void configure() throws Exception {
		
		
		from(cxfEndpoint)
				.choice()
				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindAll"))
				.removeHeaders("*")
				.to(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY))
				.process(exchange -> {
					int statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
					FindAllPersonsResponse findAllPersonsResponse = new FindAllPersonsResponse();
					if (statusCode != HttpStatus.OK.value()) {
						ErrorSoapResponse errorSoapResponse = exchange.getIn().getBody(ErrorSoapResponse.class);
						findAllPersonsResponse.setError(errorSoapResponse);
						log.error("Error with statusCode {} , with errorSoapResponse {}",statusCode,errorSoapResponse);
					} else {
						findAllPersonsResponse = exchange.getIn().getBody(FindAllPersonsResponse.class);
					}
					log.info("Response for find All Person ===> {}",findAllPersonsResponse);
					exchange.getIn().setBody(findAllPersonsResponse);
				})
				.endChoice()
				.end()
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.log("Service called .............. ");
	}
}
