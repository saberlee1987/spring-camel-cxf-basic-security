package com.saber.spring_camel_cxf_client.routes;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.dto.ServiceErrorResponseEnum;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResponseRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		
		from(String.format("direct:%s",Routes.TIME_OUT_EXCEPTION_ROUTE))
				.routeId(Routes.TIME_OUT_EXCEPTION_ROUTE)
				.routeGroup(Routes.EXCEPTION_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(504))
				.process(exchange -> {
					Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
					String exceptionMessage = exception.getLocalizedMessage();
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.TIMEOUT_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.TIMEOUT_EXCEPTION.getMessage());
					errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.GATEWAY_TIMEOUT.value(),exceptionMessage));
					exchange.getIn().setBody(errorResponse);
				})
		.log(LoggingLevel.ERROR,"Error for correlation : ${in.header.correlation} , timeoutException with body ====> ${in.body}");

		from(String.format("direct:%s",Routes.SOAP_FAULT_EXCEPTION_ROUTE))
				.routeId(Routes.SOAP_FAULT_EXCEPTION_ROUTE)
				.routeGroup(Routes.EXCEPTION_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(500))
				.process(exchange -> {
					Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
					String exceptionMessage = exception.getLocalizedMessage();
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getMessage());
					errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(),exceptionMessage));
					exchange.getIn().setBody(errorResponse);
				})
				.log(LoggingLevel.ERROR,"Error for correlation : ${in.header.correlation} , soapFault with body ====> ${in.body}");


		from(String.format("direct:%s",Routes.SOAP_HTTP_EXCEPTION_ROUTE))
				.routeId(Routes.SOAP_HTTP_EXCEPTION_ROUTE)
				.routeGroup(Routes.EXCEPTION_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(500))
				.process(exchange -> {
					Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
					String exceptionMessage = exception.getLocalizedMessage();
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getMessage());
					errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(),exceptionMessage));
					exchange.getIn().setBody(errorResponse);
				})
				.log(LoggingLevel.ERROR,"Error for correlation : ${in.header.correlation} , soap http with body ====> ${in.body}");
	}
}
