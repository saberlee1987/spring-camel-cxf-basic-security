package com.example.spring_webflux_rest_client.services.impl;

import com.example.spring_webflux_rest_client.dto.ErrorResponse;
import com.example.spring_webflux_rest_client.dto.person.PersonDto;
import com.example.spring_webflux_rest_client.dto.person.PersonResponse;
import com.example.spring_webflux_rest_client.exceptions.GatewayException;
import com.example.spring_webflux_rest_client.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
	@Value(value = "${service.person.baseUrl}")
	private String personBaseUrl;
	@Value(value = "${service.person.findAll}")
	private String personFindAllUrl;
	
	@Value(value = "${service.person.findByNationalCode}")
	private String personFindByNationalCodeUrl;
	@Autowired
	private WebClient webClient;
	
	@Override
	public Mono<PersonResponse> findAll() {
		String url = String.format("%s%s", personBaseUrl, personFindAllUrl);
		log.info("Request for findAll persons ");
		ParameterizedTypeReference<PersonResponse> responseParameterizedTypeReference = new ParameterizedTypeReference<PersonResponse>() {
		};
		Mono<PersonResponse> responseResponseEntity = webClient.get()
				.uri(url)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(responseParameterizedTypeReference);
		responseResponseEntity.subscribe(
				response -> log.info("Response for find all  person  with body {}", response),
				error -> log.error("Error ===> {}", error.getMessage())
		);
		return responseResponseEntity;
		
	}
	
	@Override
	public Mono<PersonDto> findPersonByNationalCode(String nationalCode) {
		
		String url = String.format("%s%s/%s", personBaseUrl, personFindByNationalCodeUrl, nationalCode);
		log.info("Request  for find person with nationalCode {} ",nationalCode);
		ParameterizedTypeReference<PersonDto> personDtoParameterizedTypeReference = new ParameterizedTypeReference<PersonDto>() {
		};
		Mono<PersonDto> personDtoMono = webClient.get()
				.uri(url)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(personDtoParameterizedTypeReference);
		
		personDtoMono.subscribe(
				response -> log.info("Response for find person with nationalCode {} with body {}", nationalCode, response)
		);
		return personDtoMono;
		
	}
}
