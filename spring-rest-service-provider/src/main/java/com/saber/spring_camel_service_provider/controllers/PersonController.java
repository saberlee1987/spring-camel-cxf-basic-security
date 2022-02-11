package com.saber.spring_camel_service_provider.controllers;

import com.saber.spring_camel_service_provider.dto.DeletePersonDto;
import com.saber.spring_camel_service_provider.dto.ErrorResponse;
import com.saber.spring_camel_service_provider.dto.PersonDto;
import com.saber.spring_camel_service_provider.dto.PersonResponse;
import com.saber.spring_camel_service_provider.entity.PersonEntity;
import com.saber.spring_camel_service_provider.services.PersonService;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "person api", description = "person service provide api")
@RequestMapping(value = "${service.api.path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {
    private final PersonService personService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(tags = {"addPerson"}, summary = "addPerson", description = "addPerson api", method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class)
                            ,examples = {@ExampleObject(name = "person",value = "{\"firstname\": \"saber\",\"lastname\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")})
            })
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonEntity.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<PersonEntity> addPerson(@RequestBody @Valid PersonDto personDto) {
        log.info("Request for addPerson  ====> {}", personDto);

        PersonEntity response = this.personService.addPerson(personDto);
        log.info("Response for addPerson  ====> {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/findByNationalCode/{nationalCode}")
    @Operation(tags = {"findByNationalCode"}, summary = "findByNationalCode", description = "findByNationalCode api", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<PersonEntity> findByNationalCode(@PathVariable(name = "nationalCode")
                                                           @NotBlank(message = "nationalCode is Required")
                                                           @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                           @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                           @Valid
                                                           @Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
                                                                   String nationalCode) {

        log.info("Request for findByNationalCode  ====> {}", nationalCode);

        PersonEntity response = this.personService.findByNationalCode(nationalCode);
        log.info("Response for findByNationalCode  ====> {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/findAll")
    @Operation(tags = {"findAll"}, summary = "findAll", description = "findAll api", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<PersonResponse> findAllPerson() {
        PersonResponse response = this.personService.findAll();
        log.info("Response for findAllPerson  ====> {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/updateByNationalCode/{nationalCode}")
    @Operation(tags = {"updateByNationalCode"}, summary = "updateByNationalCode", description = "updateByNationalCode api", method = "PUT"
            , requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class)
                    ,examples = {@ExampleObject(name = "person",value = "{\"firstName\": \"saber\",\"lastName\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")}
            )}
    ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<PersonEntity> updatePersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                   @NotBlank(message = "nationalCode is Required")
                                                                   @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                   @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                                   @Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
                                                                           String nationalCode,
                                                                   @RequestBody @Valid
                                                                           PersonDto dto) {
        log.info("Request for updatePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, dto);

        PersonEntity response = this.personService.updatePersonByNationalCode(nationalCode, dto);
        log.info("Response for updatePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/deleteByNationalCode/{nationalCode}")
    @Operation(tags = {"deletePersonByNationalCode"}, summary = "deletePersonByNationalCode", description = "deletePersonByNationalCode api", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<DeletePersonDto> deletePersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                      @NotBlank(message = "nationalCode is Required")
                                                                      @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                      @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                                      @Valid
                                                                      @Parameter(name = "nationalCode", in = ParameterIn.PATH, example = "0079028748", required = true)
                                                                              String nationalCode) {
        log.info("Request for deletePersonByNationalCode by  nationalCode {} ", nationalCode);

        DeletePersonDto response = this.personService.deletePersonByNationalCode(nationalCode);
        log.info("Response for deletePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, response);
        return ResponseEntity.ok(response);
    }
}
