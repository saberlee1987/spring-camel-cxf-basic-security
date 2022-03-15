package com.saber.spring_rest_client.services.impl;

import com.saber.spring_rest_client.dto.person.*;
import com.saber.spring_rest_client.routes.Headers;
import com.saber.spring_rest_client.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
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
    public PersonResponse findAll(String correlation) {
        String url = String.format("%s:%s%s%s"
                , personUrl, personPort, personBaseUrl, findAllUrl
        );

        log.info("Request for correlation {} findAll to url {}",correlation,url);

        ResponseEntity<PersonResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), PersonResponse.class);
        int statusCode = responseEntity.getStatusCodeValue();
        PersonResponse response = responseEntity.getBody();
        log.info("Response for correlation {} findAll with , statusCode {} ,  body {}"
                ,correlation,statusCode,response);
        return response;
    }

    @Override
    public PersonDto findPersonByNationalCode(String nationalCode,String correlation) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, findByNationalCodeUrl, nationalCode
        );
        log.info("Request for correlation {} findPersonByNationalCode to url {}",correlation,url);
        ResponseEntity<PersonDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null), PersonDto.class);
        int statusCode = responseEntity.getStatusCodeValue();
        PersonDto response = responseEntity.getBody();
        log.info("Response for correlation {} findPersonByNationalCode with , statusCode {} ,  body {}"
                ,correlation,statusCode,response);
        return response;
    }

    @Override
    public AddPersonResponseDto addPerson(PersonDto personDto,String correlation) {
        String url = String.format("%s:%s%s%s"
                , personUrl, personPort, personBaseUrl, addPersonUrl
        );
        log.info("Request for correlation {} addPerson with body {}  to url {}"
                ,correlation,personDto,url);
        ResponseEntity<AddPersonResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(personDto), AddPersonResponseDto.class);
        int statusCode = responseEntity.getStatusCodeValue();
        AddPersonResponseDto response = responseEntity.getBody();

        log.info("Response for correlation {} addPerson with , statusCode {} ,  body {}"
                ,correlation,statusCode,response);
        return response;
    }

    @Override
    public UpdatePersonResponseDto updatePersonByNationalCode(String nationalCode, PersonDto personDto,String correlation) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, updatePersonUrl, nationalCode
        );

        log.info("Request for correlation {} updatePersonByNationalCode with body {}  to url {}"
                ,correlation,personDto,url);

        ResponseEntity<UpdatePersonResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(personDto), UpdatePersonResponseDto.class);
        int statusCode = responseEntity.getStatusCodeValue();
        UpdatePersonResponseDto response = responseEntity.getBody();
        log.info("Response for correlation {} updatePersonByNationalCode with , statusCode {} ,  body {}"
                ,correlation,statusCode,response);
        return response;

    }

    @Override
    public DeletePersonDto deletePersonByNationalCode(String nationalCode,String correlation) {
        String url = String.format("%s:%s%s%s/%s"
                , personUrl, personPort, personBaseUrl, deletePersonUrl, nationalCode
        );

        log.info("Request for correlation {} deletePersonByNationalCode   to url {}"
                ,correlation,url);
        ResponseEntity<DeletePersonDto> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(null), DeletePersonDto.class);

        int statusCode = responseEntity.getStatusCodeValue();
        DeletePersonDto response = responseEntity.getBody();
        log.info("Response for correlation {} deletePersonByNationalCode with , statusCode {} ,  body {}"
                ,correlation,statusCode,response);
        return response;
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
