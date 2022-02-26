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
    public Mono<PersonResponse> findAll() {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.FIND_ALL_PERSON_ROUTE_GATEWAY), exchange -> {

        });
        checkException("findAll",responseExchange);

        PersonResponse personResponse = responseExchange.getIn().getBody(PersonResponse.class);
        log.info("Response for find All Person ====> {}", personResponse);
        return Mono.just(personResponse);
    }

    @Override
    public Mono<PersonDto> findPersonByNationalCode(String nationalCode) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
        });
        checkException("findPersonByNationalCode",responseExchange);
        PersonDto personDto = responseExchange.getIn().getBody(PersonDto.class);
        log.info("Response for findPersonByNationalCode ====> {}", personDto);
        return Mono.just(personDto);
    }

    @Override
    public Mono<AddPersonResponseDto> addPerson(PersonDto personDto) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personDto);

        });
        checkException("addPerson",responseExchange);
        AddPersonResponseDto response = responseExchange.getIn().getBody(AddPersonResponseDto.class);
        log.info("Response for addPerson ====> {}", response);
        return Mono.just(response);
    }

    @Override
    public Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode, PersonDto personDto) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setBody(personDto);
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
        });
        checkException("updatePersonByNationalCode",responseExchange);
        UpdatePersonResponseDto response = responseExchange.getIn().getBody(UpdatePersonResponseDto.class);
        log.info("Response for updatePersonByNationalCode  ====> {}", response);
        return Mono.just(response);
    }

    @Override
    public Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode) {
        Exchange responseExchange = this.producerTemplate.send(String.format("direct:%s", Routes.DELETE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY), exchange -> {
            exchange.getIn().setHeader(Headers.nationalCode, nationalCode);
        });
        checkException("deletePersonByNationalCode",responseExchange);
        DeletePersonDto response = responseExchange.getIn().getBody(DeletePersonDto.class);
        log.info("Response for deletePersonByNationalCode  ====> {}", response);
        return Mono.just(response);
    }

    private void checkException(String methodName, Exchange responseExchange) {
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
        if (statusCode != HttpStatus.OK.value()) {
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            log.error("Error for {} , statusCode {} , with body {}", methodName, statusCode, errorResponse);
            throw new GatewayException(statusCode, errorResponse);
        }
    }
}
