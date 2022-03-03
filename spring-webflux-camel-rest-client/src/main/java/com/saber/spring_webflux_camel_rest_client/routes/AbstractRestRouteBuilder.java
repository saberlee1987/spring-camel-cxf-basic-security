package com.saber.spring_webflux_camel_rest_client.routes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

@Slf4j
public class AbstractRestRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {


        onException(ConnectException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , ConnectException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.TIMEOUT_EXCEPTION_HANDLER_ROUTE));


        onException(SocketException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , SocketException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.TIMEOUT_EXCEPTION_HANDLER_ROUTE));

        onException(SocketTimeoutException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , SocketTimeoutException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.TIMEOUT_EXCEPTION_HANDLER_ROUTE));


        onException(JsonMappingException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , JsonMappingException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.JSON_EXCEPTION_HANDLER_ROUTE));

        onException(JsonParseException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , JsonParseException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.JSON_EXCEPTION_HANDLER_ROUTE));

        onException(HttpOperationFailedException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , HttpOperationFailedException with error " + exceptionMessage())
                .to(String.format("direct:%s", Routes.HTTP_OPERATION_EXCEPTION_HANDLER_ROUTE));

    }
}