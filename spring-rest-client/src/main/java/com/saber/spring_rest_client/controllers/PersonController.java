package com.saber.spring_rest_client.controllers;


import com.saber.spring_rest_client.dto.ErrorResponse;
import com.saber.spring_rest_client.dto.person.*;
import com.saber.spring_rest_client.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@Validated
@Tag(name = "spring-rest-client", description = "spring-rest-client")
@RequestMapping(value = "${service.api.base-path}/person", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class PersonController {


    private final PersonService personService;

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
	public ResponseEntity<PersonDto> findPersonByNationalCode(@PathVariable(name = "nationalCode")
													@NotBlank(message = "nationalCode is Required")
													@Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
													@Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
													@Valid
													@Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
															String nationalCode) {


		return ResponseEntity.ok(this.personService.findPersonByNationalCode(nationalCode));
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
    public ResponseEntity<PersonResponse> findAllPersons(HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(this.personService.findAll());
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
	public ResponseEntity<AddPersonResponseDto> addPerson(@RequestBody @NotNull(message = "body is Required") @Valid PersonDto personDto) {
		return ResponseEntity.ok(personService.addPerson(personDto));
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
	public ResponseEntity<UpdatePersonResponseDto> updatePerson(@RequestBody
													  @NotNull(message = "body is Required") @Valid
															  PersonDto personDto,
													  @PathVariable(name = "nationalCode")
													  @NotBlank(message = "nationalCode is Required")
													  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
													  @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
															  String nationalCode) {
		return ResponseEntity.ok(personService.updatePersonByNationalCode(nationalCode, personDto));
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
	public ResponseEntity<DeletePersonDto> deletePersonByNationalCode(@PathVariable(name = "nationalCode")
																		  @NotBlank(message = "nationalCode is Required")
																		  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
																		  @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
																				  String nationalCode) {
		return ResponseEntity.ok(personService.deletePersonByNationalCode(nationalCode));
	}


}