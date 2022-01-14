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
                    PersonSoapDto dto = exchange.getIn().getBody(PersonSoapDto.class);
                    log.info("Request for addPerson ====> {}", mapper.writeValueAsString(dto));

                    PersonSoapResponse response = personSoapService.addPerson(dto);
                    if (response.getResponse() != null) {
                        log.info("Response  for addPerson with statusCode {} ====> {}"
                                , HttpStatus.OK.value()
                                , mapper.writeValueAsString(response));

                    } else {
                        log.error("Error  for addPerson with statusCode {} ====> {}"
                                , response.getError().getCode()
                                , mapper.writeValueAsString(response.getError()));
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("UpdatePersonByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    MessageContentsList messageContentsList = exchange.getIn().getBody(MessageContentsList.class);
                    PersonSoapResponse response;
                    if (messageContentsList.size() == 2) {
                        String nationalCode = (String) messageContentsList.get(0);
                        PersonSoapDto dto = (PersonSoapDto) messageContentsList.get(1);
                        log.info("Request for addPerson ====> {}", mapper.writeValueAsString(dto));

                        response = personSoapService.updatePersonByNationalCode(nationalCode, dto);
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
                                400, "BAD_REQUEST", "Please send 2 parameters for updatePerson", null
                        ));
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getBody(String.class);
                    log.info("Request for FindByNationalCode ====> {}", nationalCode);
                    PersonSoapResponse response = personSoapService.findByNationalCode(nationalCode);
                    if (response.getResponse() != null) {
                        log.info("Response  for FindByNationalCode with statusCode {} ====> {}"
                                , HttpStatus.OK.value()
                                , mapper.writeValueAsString(response));

                    } else {
                        log.error("Error  for FindByNationalCode with statusCode {} ====> {}"
                                , response.getError().getCode()
                                , mapper.writeValueAsString(response));
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("DeletePersonByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getBody(String.class);
                    log.info("Request for DeletePersonByNationalCode ====> {}", nationalCode);
                    DeletePersonResponse response = personSoapService.deletePersonByNationalCode(nationalCode);
                    if (response.getResponse() != null) {
                        log.info("Response  for DeletePersonByNationalCode with statusCode {} ====> {}"
                                , HttpStatus.OK.value()
                                , mapper.writeValueAsString(response));

                    } else {
                        log.error("Error  for DeletePersonByNationalCode with statusCode {} ====> {}"
                                , response.getError().getCode()
                                , mapper.writeValueAsString(response));
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindAll"))
                .removeHeaders("*")
                .process(exchange -> {
                    FindAllPersonsResponse response = this.personSoapService.findAll();
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
