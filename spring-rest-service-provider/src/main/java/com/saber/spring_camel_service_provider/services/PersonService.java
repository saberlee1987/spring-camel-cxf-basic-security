package com.saber.spring_camel_service_provider.services;


import com.saber.spring_camel_service_provider.dto.DeletePersonDto;
import com.saber.spring_camel_service_provider.dto.PersonDto;
import com.saber.spring_camel_service_provider.dto.PersonResponse;
import com.saber.spring_camel_service_provider.entity.PersonEntity;

public interface PersonService {
    PersonEntity addPerson(PersonDto dto);
    PersonResponse findAll();
    PersonDto findByNationalCode(String nationalCode);
    PersonDto updatePersonByNationalCode(String nationalCode,PersonDto dto);
    DeletePersonDto deletePersonByNationalCode(String nationalCode);
}
