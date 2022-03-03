package com.example.spring_webflux_rest_client.services;

import com.example.spring_webflux_rest_client.dto.person.*;
import reactor.core.publisher.Mono;

public interface PersonService {
	Mono<PersonResponse> findAll(String correlation);
	Mono<PersonDto> findPersonByNationalCode(String nationalCode,String correlation);
	Mono<AddPersonResponseDto> addPerson(PersonDto personDto,String correlation);
	Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode,PersonDto personDto,String correlation);
	Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode,String correlation);
}
