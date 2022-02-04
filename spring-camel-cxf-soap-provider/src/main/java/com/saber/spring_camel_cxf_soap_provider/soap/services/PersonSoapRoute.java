package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.saber.spring_camel_cxf_soap_provider.repositoreis.routes.Headers;
import com.saber.spring_camel_cxf_soap_provider.repositoreis.routes.Routes;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
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
					FindAllPersonsResponse findAllPersonsResponse = new FindAllPersonsResponse();
					findAllPersonsResponse = exchange.getIn().getBody(FindAllPersonsResponse.class);
					log.info("Response for find All Person ===> {}",findAllPersonsResponse);
					exchange.getIn().setBody(findAllPersonsResponse);
				})
				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindByNationalCode"))
				.removeHeaders("*")
				.process(exchange -> {
					MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
					String nationalCode = (String) messageContentsList.get(0);
					exchange.getIn().setHeader(Headers.nationalCode,nationalCode);
				})
				.to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
				.process(exchange -> {
					int statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
					PersonSoapResponse personSoapResponse = new PersonSoapResponse();
					if (statusCode != HttpStatus.OK.value()) {
						ErrorSoapResponse errorSoapResponse = exchange.getIn().getBody(ErrorSoapResponse.class);
						personSoapResponse.setError(errorSoapResponse);
						log.error("Error with statusCode {} , with errorSoapResponse {}",statusCode,errorSoapResponse);
					} else {
						personSoapResponse = exchange.getIn().getBody(PersonSoapResponse.class);
					}
					log.info("Response for find-person-by-national-code  ===> {}",personSoapResponse);
					exchange.getIn().setBody(personSoapResponse);
				})

				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("AddPerson"))
				.removeHeaders("*")
				.process(exchange -> {
					MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
					PersonSoapDto personSoapDto = (PersonSoapDto) messageContentsList.get(0);
					exchange.getIn().setBody(personSoapDto);
				})
				.to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
				.process(exchange -> {
					int statusCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
					AddPersonResponse addPersonResponse = new AddPersonResponse();
					if (statusCode != HttpStatus.OK.value()) {
						ErrorSoapResponse errorSoapResponse = exchange.getIn().getBody(ErrorSoapResponse.class);
						addPersonResponse.setError(errorSoapResponse);
						log.error("Error with statusCode {} , with errorSoapResponse {}",statusCode,errorSoapResponse);
					} else {
						addPersonResponse = exchange.getIn().getBody(AddPersonResponse.class);
					}
 					log.info("Response for AddPerson  ===> {}",addPersonResponse);
					exchange.getIn().setBody(addPersonResponse);
				})
				.endChoice()
				.end()
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.log("Service called .............. ");
	}
}
