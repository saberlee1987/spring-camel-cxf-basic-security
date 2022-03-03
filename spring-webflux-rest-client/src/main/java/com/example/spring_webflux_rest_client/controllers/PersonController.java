package com.example.spring_webflux_rest_client.controllers;

import com.example.spring_webflux_rest_client.dto.ErrorResponse;
import com.example.spring_webflux_rest_client.dto.person.*;
import com.example.spring_webflux_rest_client.routes.Headers;
import com.example.spring_webflux_rest_client.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@Tag(name = "person webflux api", description = "person webflux client api")
@RequestMapping(value = "${service.api.base-path}/person", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping(value = "/find/{nationalCode}")
	@Operation(tags = {"findByNationalCode"}, summary = "findByNationalCode", description = "findByNationalCode api", method = "GET",
			parameters = {
					@Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748", description = "nationalCode")
			})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))}),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "401", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "500", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "504", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
		
	})
	public ResponseEntity<Mono<PersonDto>> findPersonByNationalCode(@PathVariable(name = "nationalCode")
													@NotBlank(message = "nationalCode is Required")
													@Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
													@Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
													@Valid
													@Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
															String nationalCode,ServerWebExchange exchange) {

		String correlation = getCorrelation(exchange);
		return ResponseEntity.ok(this.personService.findPersonByNationalCode(nationalCode,correlation));
	}
	
	@GetMapping(value = "/findAll")
	@Operation(tags = {"findAllPersons"}, summary = "findAll persons", description = "findAll persons api", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "401", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "500", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "504", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
		
	})
	public ResponseEntity<Mono<PersonResponse>> findAllPersons(ServerWebExchange exchange) {
		String correlation = getCorrelation(exchange);
		return ResponseEntity.ok(this.personService.findAll(correlation));
	}
	
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(tags = {"add person"}, summary = "add person", description = "add person", method = "POST",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "add person",
					content = @Content(mediaType = "application/json",
							schema = @Schema(name = "addPerson", title = "addPerson", implementation = PersonDto.class)
							, examples = @ExampleObject(name = "addPerson", summary = "addPerson",
							value = "{\"firstname\": \"saber\",\"lastname\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")
					)
			))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AddPersonResponseDto.class))}),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
	})
	public ResponseEntity<Mono<AddPersonResponseDto>> addPerson(@RequestBody @NotNull(message = "body is Required") @Valid PersonDto personDto,ServerWebExchange exchange) {
		String correlation = getCorrelation(exchange);
		return ResponseEntity.ok(personService.addPerson(personDto,correlation));
	}
	
	@PutMapping(value = "/update/{nationalCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(tags = {"update person"}, summary = "update person", description = "update person", method = "PUT",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "update person",
					content = @Content(mediaType = "application/json",
							schema = @Schema(name = "update person", title = "update person", implementation = PersonDto.class)
							, examples = @ExampleObject(name = "updatePerson", summary = "updatePerson",
							value = "{\"firstname\": \"saber\",\"lastname\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")
					)
			), parameters = {
			@Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748")
	}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdatePersonResponseDto.class))}),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
	})
	public ResponseEntity<Mono<UpdatePersonResponseDto>> updatePerson(@RequestBody
													  @NotNull(message = "body is Required") @Valid
															  PersonDto personDto,
													  @PathVariable(name = "nationalCode")
													  @NotBlank(message = "nationalCode is Required")
													  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
													  @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
															  String nationalCode,ServerWebExchange exchange) {
		String correlation = getCorrelation(exchange);
		return ResponseEntity.ok(personService.updatePersonByNationalCode(nationalCode, personDto,correlation));
	}
	
	@DeleteMapping(value = "/delete/{nationalCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(tags = {"delete person by nationalCode"}, summary = "delete person by nationalCode", description = "delete person by nationalCode", method = "GET",
			parameters = {
					@Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748", description = "nationalCode")
			})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeletePersonDto.class))}),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
			@ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
	})
	public ResponseEntity<Mono<DeletePersonDto>> deletePersonByNationalCode(@PathVariable(name = "nationalCode")
																		  @NotBlank(message = "nationalCode is Required")
																		  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
																		  @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
																				  String nationalCode,ServerWebExchange exchange) {
		String correlation = getCorrelation(exchange);
		return ResponseEntity.ok(personService.deletePersonByNationalCode(nationalCode,correlation));
	}

	private String getCorrelation(ServerWebExchange exchange){
		String correlation = "";
		HttpHeaders headers = exchange.getRequest().getHeaders();
		List<String> correlations = headers.get(Headers.correlation);
		if(correlations!=null && !correlations.isEmpty()){
			correlation = correlations.get(0);
		}else{
			correlation = UUID.randomUUID().toString();
		}
		return correlation;
	}
}