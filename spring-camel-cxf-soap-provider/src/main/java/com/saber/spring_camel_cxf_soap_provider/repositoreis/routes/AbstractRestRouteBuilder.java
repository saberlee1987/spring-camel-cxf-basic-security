package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.exceptions.TimeoutException;
import com.saber.spring_camel_cxf_soap_provider.soap.services.ErrorSoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;

@Slf4j
public class AbstractRestRouteBuilder extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		
		
		onException(TimeoutException.class)
				.maximumRedeliveries(0)
				.handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(504))
				.process(exchange -> {
					TimeoutException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,TimeoutException.class);
					ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
					errorSoapResponse.setCode(HttpStatus.GATEWAY_TIMEOUT.value());
					errorSoapResponse.setMessage(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase());
					errorSoapResponse.setOriginalMessage(exception.getLocalizedMessage());
					exchange.getIn().setBody(errorSoapResponse);
				});
	}
}
