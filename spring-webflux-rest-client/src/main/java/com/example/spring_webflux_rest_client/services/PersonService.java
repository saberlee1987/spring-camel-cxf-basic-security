package com.example.spring_webflux_rest_client.services;

import com.example.spring_webflux_rest_client.dto.person.AddPersonResponseDto;
import com.example.spring_webflux_rest_client.dto.person.PersonDto;
import com.example.spring_webflux_rest_client.dto.person.PersonResponse;
import reactor.core.publisher.Mono;

public interface PersonService {
	Mono<PersonResponse> findAll();
	Mono<PersonDto> findPersonByNationalCode(String nationalCode);
	Mono<AddPersonResponseDto> addPerson(PersonDto personDto);
}
