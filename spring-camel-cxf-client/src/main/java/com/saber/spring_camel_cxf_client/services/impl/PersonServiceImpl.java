package com.saber.spring_camel_cxf_client.services.impl;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.dto.FindAllResponseDto;
import com.saber.spring_camel_cxf_client.dto.soap.*;
import com.saber.spring_camel_cxf_client.exceptions.GatewayException;
import com.saber.spring_camel_cxf_client.exceptions.PersonSoapException;
import com.saber.spring_camel_cxf_client.routes.Headers;
import com.saber.spring_camel_cxf_client.routes.Routes;
import com.saber.spring_camel_cxf_client.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public FindAllResponseDto findAll(String correlation) {

        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
        FindAllPersonsResponse response = responseExchange.getIn().getBody(FindAllPersonsResponse.class);

        if (response.getError()!=null){
            throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, response.getError());
        }
        FindAllResponseDto findAllResponseDto = new FindAllResponseDto();
        findAllResponseDto.setResponse(response.getResponse());
        log.info("Response for  correlation : {} , find All Persons ====> {}",correlation, findAllResponseDto);
        return findAllResponseDto;
    }

    @Override
    public PersonSoapDto findPersonByNationalCode(String nationalCode,String correlation) {
        log.info("Request for find person by nationalCode with nationalCode ==>  {} ", nationalCode);
        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
        PersonSoapResponse response = responseExchange.getIn().getBody(PersonSoapResponse.class);
         if (response.getError()!=null){
             throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, response.getError());
         }
        log.info("Response for  correlation : {} , findPersonByNationalCode with nationalCode {} ====> {}"
                ,correlation, nationalCode, response);
        return response.getResponse();
    }

    @Override
    public AddPersonSoapResponseDto addPerson(PersonSoapDto personSoapDto,String correlation) {
        log.info("Request for  correlation : {} , add person with body ==>  {} ",correlation, personSoapDto);
        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personSoapDto);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
        AddPersonResponseDto response = responseExchange.getIn().getBody(AddPersonResponseDto.class);
        if (response.getError()!=null){
            throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, response.getError());
        }
        log.info("Response for  correlation : {} , add person with body ====> {}",correlation, response);
        return response.getResponse();
    }
    
    @Override
    public UpdatePersonSoapResponseDto updatePersonByNationalCode(String nationalCode, PersonSoapDto personSoapDto,String correlation) {
        log.info("Request for  correlation : {} , update person with nationalCode {} , with body ==>  {} "
                ,correlation,nationalCode, personSoapDto);
        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personSoapDto);
            exchange.getIn().setHeader(Headers.nationalCode,nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
        UpdatePersonResponse response = responseExchange.getIn().getBody(UpdatePersonResponse.class);
        if (response.getError()!=null){
            throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, response.getError());
        }
        log.info("Response for  correlation : {} , update person with nationalCode {} ,  with body ====> {}"
                ,correlation,nationalCode, response);
        return response.getResponse();
    }
    
    @Override
    public DeleteSoapPersonDto deletePersonByNationalCode(String nationalCode,String correlation) {
        log.info("Request for correlation : {} , delete person by nationalCode with nationalCode ==>  {} ",correlation, nationalCode);
        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
        DeletePersonResponse response = responseExchange.getIn().getBody(DeletePersonResponse.class);
        if (response.getError()!=null){
            throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, response.getError());
        }
        log.info("Response for correlation : {} , delete Person  with nationalCode {} ====> {}",correlation, nationalCode, response);
        return response.getResponse();
    }
}
