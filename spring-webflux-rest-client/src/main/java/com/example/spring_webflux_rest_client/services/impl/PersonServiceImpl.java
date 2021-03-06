package com.example.spring_webflux_rest_client.services.impl;

import com.example.spring_webflux_rest_client.dto.ErrorResponse;
import com.example.spring_webflux_rest_client.dto.person.*;
import com.example.spring_webflux_rest_client.exceptions.GatewayException;
import com.example.spring_webflux_rest_client.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	@Value(value = "${service.person.addPerson}")
	private String addPersonUrl;
	@Value(value = "${service.person.updatePerson}")
	private String updatePersonUrl;
	@Value(value = "${service.person.deletePerson}")
	private String deletePersonUrl;
	@Autowired
	private WebClient webClient;
	
	@Override
	public Mono<PersonResponse> findAll(String correlation) {
		String url = String.format("%s%s", personBaseUrl, personFindAllUrl);
		log.info("Request for correlation : {} , findAll persons ",correlation);
		ParameterizedTypeReference<PersonResponse> parameterizedTypeReference = new ParameterizedTypeReference<PersonResponse>() {
		};
		Mono<PersonResponse> responseResponseEntity = sendGetRequest(url, parameterizedTypeReference);
		
		responseResponseEntity = responseResponseEntity.flatMap(response -> {
			log.info("Response for correlation : {} , findAll persons with body {}",correlation, response);
			return Mono.just(response);
		});
		return responseResponseEntity;
		
	}
	
	@Override
	public Mono<PersonDto> findPersonByNationalCode(String nationalCode,String correlation) {
		
		String url = String.format("%s%s/%s", personBaseUrl, personFindByNationalCodeUrl, nationalCode);
		log.info("Request  for correlation : {} , find person with nationalCode {} ",correlation, nationalCode);
		ParameterizedTypeReference<PersonDto> parameterizedTypeReference = new ParameterizedTypeReference<PersonDto>() {
		};
		Mono<PersonDto> personDtoMono = sendGetRequest(url, parameterizedTypeReference);
		
		personDtoMono = personDtoMono.flatMap(response -> {
			log.info("Response for correlation : {} , find person by nationalCode {} with body {}",correlation, nationalCode, response);
			return Mono.just(response);
		});
		
		return personDtoMono;
		
	}
	
	@Override
	public Mono<AddPersonResponseDto> addPerson(PersonDto personDto,String correlation) {
		String url = String.format("%s%s", personBaseUrl, addPersonUrl);
		log.info("Request for correlation : {} , add person with body {} ",correlation, personDto);
		ParameterizedTypeReference<AddPersonResponseDto> parameterizedTypeReference = new ParameterizedTypeReference<AddPersonResponseDto>() {
		};
		Mono<AddPersonResponseDto> responseResponseEntity = webClient.post()
				.uri(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(personDto), PersonDto.class)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(parameterizedTypeReference);
		
		responseResponseEntity = responseResponseEntity.flatMap(response -> {
			log.info("Response for correlation : {} , add person  with body {}",correlation, response);
			return Mono.just(response);
		});
		
		return responseResponseEntity;
	}
	
	@Override
	public Mono<UpdatePersonResponseDto> updatePersonByNationalCode(String nationalCode, PersonDto personDto,String correlation) {
		String url = String.format("%s%s/%s", personBaseUrl, updatePersonUrl, nationalCode);
		log.info("Request for correlation : {} , update person by nationalCode {} with body {} ",correlation, nationalCode, personDto);
		ParameterizedTypeReference<UpdatePersonResponseDto> parameterizedTypeReference = new ParameterizedTypeReference<>() {
		};
		Mono<UpdatePersonResponseDto> responseResponseEntity = webClient.put()
				.uri(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(personDto), PersonDto.class)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(parameterizedTypeReference);
		
		responseResponseEntity = responseResponseEntity.flatMap(response -> {
			log.info("Response for correlation : {} , update person by nationalCode {} with body {}",correlation,nationalCode, response);
			return Mono.just(response);
		});
		
		return responseResponseEntity;
	}
	
	@Override
	public Mono<DeletePersonDto> deletePersonByNationalCode(String nationalCode,String correlation) {
		String url = String.format("%s%s/%s", personBaseUrl, deletePersonUrl, nationalCode);
		log.info("Request  for correlation : {} , delete person with nationalCode {} ",correlation, nationalCode);
		ParameterizedTypeReference<DeletePersonDto> parameterizedTypeReference = new ParameterizedTypeReference<>() {
		};
		Mono<DeletePersonDto> responseResponseEntity = webClient.delete()
				.uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(parameterizedTypeReference);
		
		responseResponseEntity = responseResponseEntity.flatMap(response -> {
			log.info("Response for correlation : {} , delete person with nationalCode {}  with body {}",correlation,nationalCode, response);
			return Mono.just(response);
		});
		
		return responseResponseEntity;
	}
	
	
	private <T> Mono<T> sendGetRequest(String url, ParameterizedTypeReference<T> parameterizedTypeReference) {
		return webClient.get()
				.uri(url)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					int statusCode = clientResponse.statusCode().value();
					Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
					return errorMessage.flatMap(message -> {
						return Mono.error(new GatewayException(statusCode, message));
					});
				})
				.bodyToMono(parameterizedTypeReference);
	}
	
}
