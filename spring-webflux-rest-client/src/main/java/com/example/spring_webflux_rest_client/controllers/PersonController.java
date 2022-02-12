package com.example.spring_webflux_rest_client.controllers;

import com.example.spring_webflux_rest_client.dto.ErrorResponse;
import com.example.spring_webflux_rest_client.dto.person.PersonDto;
import com.example.spring_webflux_rest_client.dto.person.PersonResponse;
import com.example.spring_webflux_rest_client.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@Validated
@Tag(name = "person webflux api", description = "person webflux client api")
@RequestMapping(value = "${service.api.base-path}/person",produces = MediaType.APPLICATION_JSON_VALUE)
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
	public Mono<PersonDto> findPersonByNationalCode(@PathVariable(name = "nationalCode")
																  @NotBlank(message = "nationalCode is Required")
																  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
																  @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
																  @Valid
																  @Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
																		  String nationalCode){
		
		
		return this.personService.findPersonByNationalCode(nationalCode);
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
	public Mono<PersonResponse> findAllPersons(){
	
		return this.personService.findAll();
	}
}
