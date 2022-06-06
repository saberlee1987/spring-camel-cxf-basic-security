package com.saber.spring.webservice.soap.client.controllers;


import com.saber.spring.webservice.soap.client.dto.ErrorResponse;
import com.saber.spring.webservice.soap.client.dto.FindAllResponseDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.AddPersonSoapResponseDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.DeleteSoapPersonDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.PersonSoapDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.UpdatePersonSoapResponseDto;
import com.saber.spring.webservice.soap.client.routes.Headers;
import com.saber.spring.webservice.soap.client.services.PersonSoapClient;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${service.api.base-path}/person")
@Validated
@Tag(name = "spring webservice soap client", description = "spring webservice soap client")
public class PersonController {

    private final PersonSoapClient personService;

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation( summary = "findAll person", description = "findAll person api", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FindAllResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<FindAllResponseDto> findAll(HttpServletRequest request) {
        String correlation = getCorrelation(request);
        FindAllResponseDto response = personService.findAllPersonsResponse(correlation);
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/find/{nationalCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation( summary = "find person by nationalCode", description = "find person by nationalCode", method = "GET",
            parameters = {
                    @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748", description = "nationalCode")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonSoapDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<PersonSoapDto> findPersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                  @NotBlank(message = "nationalCode is Required")
                                                                  @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                  @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
                                                                          String nationalCode,HttpServletRequest request) {
        String correlation = getCorrelation(request);
        PersonSoapDto response = personService.findByNationalCode(nationalCode,correlation);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation( summary = "add person", description = "add person", method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "add person",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "addPerson", title = "addPerson", implementation = PersonSoapDto.class)
                            , examples = @ExampleObject(name = "addPerson", summary = "addPerson",
                            value = "{\"firstname\": \"saber\",\"lastname\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")
                    )
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AddPersonSoapResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<AddPersonSoapResponseDto> addPerson(@RequestBody @NotNull(message = "body is Required") @Valid PersonSoapDto personSoapDto,HttpServletRequest request) {
        String correlation = getCorrelation(request);
        AddPersonSoapResponseDto response = personService.addPerson(personSoapDto,correlation);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{nationalCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation( summary = "update person", description = "update person", method = "PUT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "update person",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(name = "update person", title = "update person", implementation = PersonSoapDto.class)
                            , examples = @ExampleObject(name = "updatePerson", summary = "updatePerson",
                            value = "{\"firstname\": \"saber\",\"lastname\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"email\": \"saberazizi66@yahoo.com\",\"mobile\": \"09365627895\"}")
                    )
            ), parameters = {
            @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748")
    }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdatePersonSoapResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<UpdatePersonSoapResponseDto> updatePerson(@RequestBody
                                                                    @NotNull(message = "body is Required") @Valid
                                                                            PersonSoapDto personSoapDto,
                                                                    @PathVariable(name = "nationalCode")
                                                                    @NotBlank(message = "nationalCode is Required")
                                                                    @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                    @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
                                                                            String nationalCode,HttpServletRequest request) {
        String correlation = getCorrelation(request);

        UpdatePersonSoapResponseDto response = personService.updatePerson(personSoapDto,nationalCode,correlation);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{nationalCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation( summary = "delete person by nationalCode", description = "delete person by nationalCode", method = "GET",
            parameters = {
                    @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748", description = "nationalCode")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeleteSoapPersonDto.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<DeleteSoapPersonDto> deletePersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                          @NotBlank(message = "nationalCode is Required")
                                                                          @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                          @Pattern(regexp = "\\d+", message = "please enter valid nationalCode")
                                                                                  String nationalCode,HttpServletRequest request) {
        String correlation = getCorrelation(request);
        DeleteSoapPersonDto response = personService.deletePersonByNationalCode(nationalCode,correlation);
        return ResponseEntity.ok(response);
    }

    private String getCorrelation(HttpServletRequest request) {
        String correlation = "";
        if (request.getHeader(Headers.correlation) != null) {
            correlation = request.getHeader(Headers.correlation);
        } else {
            correlation = UUID.randomUUID().toString();
        }
        return correlation;
    }
}
