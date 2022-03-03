package com.saber.spring_webflux_camel_rest_client.services;


import com.saber.spring_webflux_camel_rest_client.dto.person.*;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonResponse> findAll(String correlation);

    Mono<PersonDto> findPersonByNationalCode(String nationalCode,String correlation);

    Mono<AddPersonResponseDto> addPerson(PersonDto personDto,String correlation);

    Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode, PersonDto personDto,String correlation);

    Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode,String correlation);
}
