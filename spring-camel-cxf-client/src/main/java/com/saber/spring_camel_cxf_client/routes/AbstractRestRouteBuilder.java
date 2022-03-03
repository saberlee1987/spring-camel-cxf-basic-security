package com.saber.spring_camel_cxf_client.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.binding.soap.SoapFault;

import javax.xml.ws.http.HTTPException;
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
				.log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , ConnectException with error body ==> " + exceptionMessage())
				.to(String.format("direct:%s", Routes.TIME_OUT_EXCEPTION_ROUTE));
		
		onException(SocketException.class)
				.handled(true)
				.maximumRedeliveries(0)
				.log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , ConnectException with error body ==> " + exceptionMessage())
				.to(String.format("direct:%s", Routes.TIME_OUT_EXCEPTION_ROUTE));
		
		onException(SocketTimeoutException.class)
				.handled(true)
				.maximumRedeliveries(0)
				.log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , ConnectException with error body ==> " + exceptionMessage())
				.to(String.format("direct:%s", Routes.TIME_OUT_EXCEPTION_ROUTE));


		onException(SoapFault.class)
				.maximumRedeliveries(0)
				.handled(true)
				.log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , SoapFault with error body ==> " + exceptionMessage())
				.to(String.format("direct:%s", Routes.SOAP_FAULT_EXCEPTION_ROUTE));


		onException(HTTPException.class)
				.maximumRedeliveries(0)
				.handled(true)
				.log(LoggingLevel.ERROR, "Error for correlation : ${in.header.correlation} , HTTPException with error body ==> " + exceptionMessage())
				.to(String.format("direct:%s", Routes.SOAP_HTTP_EXCEPTION_ROUTE));
	}
}
