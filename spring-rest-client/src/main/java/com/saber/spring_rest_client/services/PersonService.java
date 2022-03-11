package com.saber.spring_rest_client.services;

import com.saber.spring_rest_client.dto.person.*;

public interface PersonService {
    PersonResponse findAll();
    PersonDto findPersonByNationalCode(String nationalCode);
    AddPersonResponseDto addPerson(PersonDto personDto);
    UpdatePersonResponseDto updatePersonByNationalCode(String nationalCode, PersonDto personDto);
    DeletePersonDto deletePersonByNationalCode(String nationalCode);
}
