package com.saber.spring_camel_cxf_client.services;

import com.saber.spring_camel_cxf_client.dto.FindAllResponseDto;
import com.saber.spring_camel_cxf_client.dto.soap.AddPersonSoapResponseDto;
import com.saber.spring_camel_cxf_client.dto.soap.DeleteSoapPersonDto;
import com.saber.spring_camel_cxf_client.dto.soap.PersonSoapDto;
import com.saber.spring_camel_cxf_client.dto.soap.UpdatePersonSoapResponseDto;

public interface PersonService {
    FindAllResponseDto findAll();
    PersonSoapDto findPersonByNationalCode(String nationalCode);
    AddPersonSoapResponseDto addPerson(PersonSoapDto personSoapDto);
    UpdatePersonSoapResponseDto updatePersonByNationalCode(String nationalCode,PersonSoapDto personSoapDto);
    DeleteSoapPersonDto deletePersonByNationalCode(String nationalCode);
}
