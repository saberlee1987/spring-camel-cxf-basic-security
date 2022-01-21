package com.saber.spring_camel_cxf_client.controllers;

import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_client.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${service.api.path}/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/findAll",produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<FindAllPersonsResponse> findAll(){
        FindAllPersonsResponse response = personService.findAll();
        return ResponseEntity.ok(response);
    }

}
