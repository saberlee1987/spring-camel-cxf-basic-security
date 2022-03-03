package com.saber.spring_webflux_camel_rest_client.services.impl;

import com.saber.spring_webflux_camel_rest_client.dto.ErrorResponse;
import com.saber.spring_webflux_camel_rest_client.dto.person.*;
import com.saber.spring_webflux_camel_rest_client.exceptions.GatewayException;
import com.saber.spring_webflux_camel_rest_client.routes.Headers;
import com.saber.spring_webflux_camel_rest_client.routes.Routes;
import com.saber.spring_webflux_camel_rest_client.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public Mono<PersonResponse> findAll(String correlation) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        checkException("findAll",responseExchange,correlation);

        PersonResponse personResponse = responseExchange.getIn().getBody(PersonResponse.class);
        log.info("Response for correlation : {} , find All Person ====> {}",correlation, personResponse);
        return Mono.just(personResponse);
    }

    @Override
    public Mono<PersonDto> findPersonByNationalCode(String nationalCode,String correlation) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        checkException("findPersonByNationalCode",responseExchange,correlation);
        PersonDto personDto = responseExchange.getIn().getBody(PersonDto.class);
        log.info("Response for correlation : {} , findPersonByNationalCode ====> {}",correlation, personDto);
        return Mono.just(personDto);
    }

    @Override
    public Mono<AddPersonResponseDto> addPerson(PersonDto personDto,String correlation) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personDto);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        checkException("addPerson",responseExchange,correlation);
        AddPersonResponseDto response = responseExchange.getIn().getBody(AddPersonResponseDto.class);
        log.info("Response for correlation : {} , addPerson ====> {}",correlation, response);
        return Mono.just(response);
    }

    @Override
    public Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode, PersonDto personDto,String correlation) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personDto);
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        checkException("updatePersonByNationalCode",responseExchange,correlation);
        UpdatePersonResponseDto response = responseExchange.getIn().getBody(UpdatePersonResponseDto.class);
        log.info("Response for correlation : {} , updatePersonByNationalCode  ====> {}",correlation, response);
        return Mono.just(response);
    }

    @Override
    public Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode,String correlation) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.DELETE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
            exchange.getIn().setHeader(Headers.correlation,correlation);
        });
        checkException("deletePersonByNationalCode",responseExchange,correlation);
        DeletePersonDto response = responseExchange.getIn().getBody(DeletePersonDto.class);
        log.info("Response for correlation : {} , deletePersonByNationalCode  ====> {}",correlation, response);
        return Mono.just(response);
    }

    private void checkException(String methodName, Exchange responseExchange,String correlation) {
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            log.error("Error for correlation : {} , {} , statusCode {} , with body {}",correlation, methodName, statusCode, errorResponse);
            throw new GatewayException(statusCode, correlation, errorResponse);
        }
    }
}
