package com.saber.spring_rest_client.services.impl;

import com.saber.spring_rest_client.dto.person.*;
import com.saber.spring_rest_client.routes.Headers;
import com.saber.spring_rest_client.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final RestTemplate restTemplate;
    @Value(value = "${service.person.url}")
    private String personUrl;
    @Value(value = "${service.person.port}")
    private Integer personPort;
    @Value(value = "${service.person.baseUrl}")
    private String personBaseUrl;
    @Value(value = "${service.person.findAll}")
    private String findAllUrl;
    @Value(value = "${service.person.findByNationalCode}")
    private String findByNationalCodeUrl;
    @Value(value = "${service.person.addPerson}")
    private String addPersonUrl;
    @Value(value = "${service.person.updatePerson}")
    private String updatePersonUrl;
    @Value(value = "${service.person.deletePerson}")
    private String deletePersonUrl;

    @Value(value = "${service.authorization.username}")
    private String authorizationUsername;
    @Value(value = "${service.authorization.password}")
    private String authorizationPassword;

    @Override
    public PersonResponse findAll() {
        String url = String.format("%s:%s%s%s"
                , personUrl, personPort, personBaseUrl, findAllUrl
        );

        ResponseEntity<PersonResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), PersonResponse.class);
        return responseEntity.getBody();
    }

    @Override
    public PersonDto findPersonByNationalCode(String nationalCode) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, findByNationalCodeUrl, nationalCode
        );
        return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), PersonDto.class).getBody();
    }

    @Override
    public AddPersonResponseDto addPerson(PersonDto personDto) {
        String url = String.format("%s:%s%s%s"
                , personUrl, personPort, personBaseUrl, addPersonUrl
        );
        return restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(personDto), AddPersonResponseDto.class).getBody();
    }

    @Override
    public UpdatePersonResponseDto updatePersonByNationalCode(String nationalCode, PersonDto personDto) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, updatePersonUrl, nationalCode
        );
        return restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(personDto), UpdatePersonResponseDto.class).getBody();
    }

    @Override
    public DeletePersonDto deletePersonByNationalCode(String nationalCode) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, deletePersonUrl, nationalCode
        );
        return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(null), DeletePersonDto.class).getBody();
    }

    private String authorization(){
        return String.format("Basic %s", Base64.getEncoder()
                .encodeToString(String.format("%s:%s",authorizationUsername,authorizationPassword).getBytes(StandardCharsets.UTF_8)));
    }
    private HttpEntity<?> getHttpEntity(Object dto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Headers.Authorization,authorization());
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(dto,httpHeaders);
    }
}
