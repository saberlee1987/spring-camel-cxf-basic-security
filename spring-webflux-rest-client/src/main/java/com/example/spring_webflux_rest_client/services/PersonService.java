package com.example.spring_webflux_rest_client.services;

import com.example.spring_webflux_rest_client.dto.person.*;
import reactor.core.publisher.Mono;

public interface PersonService {
	Mono<PersonResponse> findAll();
	Mono<PersonDto> findPersonByNationalCode(String nationalCode);
	Mono<AddPersonResponseDto> addPerson(PersonDto personDto);
	Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode,PersonDto personDto);
	Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode);
}
