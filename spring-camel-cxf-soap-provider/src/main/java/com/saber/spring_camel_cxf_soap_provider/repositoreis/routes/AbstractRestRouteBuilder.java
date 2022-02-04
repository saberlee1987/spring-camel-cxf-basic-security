package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.ErrorSoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;

@Slf4j
public class AbstractRestRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(String.format("direct:%s", Routes.RESOURCE_NOT_FOUND_EXCEPTION_ROUTE))
                .routeId(Routes.RESOURCE_NOT_FOUND_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(406))
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader("nationalCode", String.class);
                    ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
                    errorSoapResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
                    errorSoapResponse.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
                    errorSoapResponse.setOriginalMessage(String.format("person with nationalCode %s does not exist", nationalCode));
                    exchange.getIn().setBody(errorSoapResponse);
                });

        from(String.format("direct:%s", Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE))
                .routeId(Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(406))
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader("nationalCode", String.class);
                    ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
                    errorSoapResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
                    errorSoapResponse.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
                    errorSoapResponse.setOriginalMessage(String.format("person with nationalCode %s already exist", nationalCode));
                    exchange.getIn().setBody(errorSoapResponse);
                });

        from(String.format("direct:%s", Routes.BEAN_VALIDATION_ROUTE))
                .routeId(Routes.BEAN_VALIDATION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(406))
                .process(exchange -> {
                    ErrorSoapResponse errorSoapResponse = exchange.getIn().getBody(ErrorSoapResponse.class);
                    exchange.getIn().setBody(errorSoapResponse);
                })
                .log("Error for beanvalidation ===> ${in.body}");

    }
}
