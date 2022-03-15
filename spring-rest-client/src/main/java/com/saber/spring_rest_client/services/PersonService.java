package com.saber.spring_rest_client.services;

import com.saber.spring_rest_client.dto.person.*;

public interface PersonService {
    PersonResponse findAll(String correlation);
    PersonDto findPersonByNationalCode(String nationalCode,String correlation);
    AddPersonResponseDto addPerson(PersonDto personDto,String correlation);
    UpdatePersonResponseDto updatePersonByNationalCode(String nationalCode, PersonDto personDto,String correlation);
    DeletePersonDto deletePersonByNationalCode(String nationalCode,String correlation);
}
