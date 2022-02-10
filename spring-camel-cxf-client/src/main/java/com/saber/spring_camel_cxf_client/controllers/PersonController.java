package com.saber.spring_camel_cxf_client.controllers;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_client.dto.soap.PersonSoapResponse;
import com.saber.spring_camel_cxf_client.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.core.MediaType;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${service.api.path}/person")
@Validated
@Tag(name = "person soap client", description = "person soap client")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/findAll",produces = MediaType.APPLICATION_JSON)
    @Operation(tags = {"findAll person"}, summary = "findAll person", description = "findAll person api", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FindAllPersonsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<FindAllPersonsResponse> findAll(){
        FindAllPersonsResponse response = personService.findAll();
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/findPersonByNationalCode/{nationalCode}",produces = MediaType.APPLICATION_JSON)
    @Operation(tags = {"find person by nationalCode"}, summary = "find person by nationalCode", description = "find person by nationalCode", method = "GET",
    parameters = {
            @Parameter(name = "nationalCode",in = ParameterIn.PATH,required = true,example = "0079028748",description = "nationalCode")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonSoapResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<PersonSoapResponse> findPersonByNationalCode(@PathVariable(name = "nationalCode") @NotBlank(message = "nationalCode is Required")@Size(min = 10,max = 10,message = "nationalCode must be 10 digit") @Pattern(regexp = "\\d+",message = "please enter valid nationalCode") String nationalCode){
        PersonSoapResponse response = personService.findPersonByNationalCode(nationalCode);
        return ResponseEntity.ok(response);
    }
}
