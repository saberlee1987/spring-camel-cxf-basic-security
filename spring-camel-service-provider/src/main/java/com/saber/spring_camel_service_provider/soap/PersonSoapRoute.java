package com.saber.spring_camel_service_provider.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.spring_camel_service_provider.soap.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.http.HttpStatus;

@Slf4j
public class PersonSoapRoute extends RouteBuilder {
    private String url;
    private final PersonSoapService personSoapService;
    private final ObjectMapper mapper;

    public PersonSoapRoute(PersonSoapService personSoapService, ObjectMapper mapper) {
        this.personSoapService = personSoapService;
        this.mapper = mapper;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void configure() throws Exception {

        from(url)
                .choice()
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("AddPerson"))
                .removeHeaders("*")
                .process(exchange -> {
                    MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
                    PersonSoapResponse response;
                    if (messageContentsList.size() == 2) {
                        AuthHeader authHeader = (AuthHeader) messageContentsList.get(0);
                        PersonSoapDto dto = (PersonSoapDto) messageContentsList.get(1);
                        log.info("Request for addPerson ====> {}", mapper.writeValueAsString(dto));

                        response = personSoapService.addPerson(authHeader, dto);
                        if (response.getResponse() != null) {
                            log.info("Response  for addPerson with statusCode {} ====> {}"
                                    , HttpStatus.OK.value()
                                    , mapper.writeValueAsString(response));

                        } else {
                            log.error("Error  for addPerson with statusCode {} ====> {}"
                                    , response.getError().getCode()
                                    , mapper.writeValueAsString(response.getError()));
                        }
                    } else {
                        response = new PersonSoapResponse(new ErrorSoapResponse(
                                400, "BAD_REQUEST", "Please send 2 parameters for AddPerson", null
                        ));
                    }

                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("UpdatePersonByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
                    PersonSoapResponse response;
                    if (messageContentsList.size() == 3) {
                        AuthHeader authHeader = (AuthHeader) messageContentsList.get(0);
                        String nationalCode = (String) messageContentsList.get(1);
                        PersonSoapDto dto = (PersonSoapDto) messageContentsList.get(2);
                        log.info("Request for addPerson ====> {}", mapper.writeValueAsString(dto));

                        response = personSoapService.updatePersonByNationalCode(authHeader, nationalCode, dto);
                        if (response.getResponse() != null) {
                            log.info("Response  for addPerson with statusCode {} ====> {}"
                                    , HttpStatus.OK.value()
                                    , mapper.writeValueAsString(response));

                        } else {
                            log.error("Error  for addPerson with statusCode {} ====> {}"
                                    , response.getError().getCode()
                                    , mapper.writeValueAsString(response.getError()));
                        }

                    } else {
                        response = new PersonSoapResponse(new ErrorSoapResponse(
                                400, "BAD_REQUEST", "Please send 3 parameters for updatePerson", null
                        ));
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
                    PersonSoapResponse response;
                    if (messageContentsList.size() == 2) {
                        AuthHeader authHeader = (AuthHeader) messageContentsList.get(0);
                        String nationalCode = (String) messageContentsList.get(1);
                        log.info("Request for FindByNationalCode ====> {}", nationalCode);
                        response = personSoapService.findByNationalCode(authHeader, nationalCode);
                        if (response.getResponse() != null) {
                            log.info("Response  for FindByNationalCode with statusCode {} ====> {}"
                                    , HttpStatus.OK.value()
                                    , mapper.writeValueAsString(response));

                        } else {
                            log.error("Error  for FindByNationalCode with statusCode {} ====> {}"
                                    , response.getError().getCode()
                                    , mapper.writeValueAsString(response));
                        }
                    } else {
                        response = new PersonSoapResponse(new ErrorSoapResponse(
                                400, "BAD_REQUEST", "Please send 2 parameters for AddPerson", null
                        ));
                    }

                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("DeletePersonByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
                    DeletePersonResponse response;
                    if (messageContentsList.size() == 2) {
                        AuthHeader authHeader = (AuthHeader) messageContentsList.get(0);
                        String nationalCode = (String) messageContentsList.get(1);
                        log.info("Request for DeletePersonByNationalCode ====> {}", nationalCode);
                        response = personSoapService.deletePersonByNationalCode(authHeader, nationalCode);
                        if (response.getResponse() != null) {
                            log.info("Response  for DeletePersonByNationalCode with statusCode {} ====> {}"
                                    , HttpStatus.OK.value()
                                    , mapper.writeValueAsString(response));

                        } else {
                            log.error("Error  for DeletePersonByNationalCode with statusCode {} ====> {}"
                                    , response.getError().getCode()
                                    , mapper.writeValueAsString(response));
                        }
                    } else {
                        response = new DeletePersonResponse(new ErrorSoapResponse(
                                400, "BAD_REQUEST", "Please send 2 parameters for DeletePerson", null
                        ));
                    }

                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindAll"))
                .removeHeaders("*")
                .process(exchange -> {
                    AuthHeader authHeader = exchange.getIn(AuthHeader.class);
                    FindAllPersonsResponse response = this.personSoapService.findAll(authHeader);
                    log.info("Response  for FindAll with statusCode {} ====> {}"
                            , HttpStatus.OK.value()
                            , mapper.writeValueAsString(response));
                    exchange.getMessage().setBody(response);
                })
                .endChoice()
                .end()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .log("Service called .............. ");
    }
}
