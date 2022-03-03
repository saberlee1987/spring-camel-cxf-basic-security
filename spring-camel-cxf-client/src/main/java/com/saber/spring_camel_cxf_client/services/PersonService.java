package com.saber.spring_camel_cxf_client.services;

import com.saber.spring_camel_cxf_client.dto.FindAllResponseDto;
import com.saber.spring_camel_cxf_client.dto.soap.AddPersonSoapResponseDto;
import com.saber.spring_camel_cxf_client.dto.soap.DeleteSoapPersonDto;
import com.saber.spring_camel_cxf_client.dto.soap.PersonSoapDto;
import com.saber.spring_camel_cxf_client.dto.soap.UpdatePersonSoapResponseDto;

public interface PersonService {
    FindAllResponseDto findAll(String correlation);
    PersonSoapDto findPersonByNationalCode(String nationalCode,String correlation);
    AddPersonSoapResponseDto addPerson(PersonSoapDto personSoapDto,String correlation);
    UpdatePersonSoapResponseDto updatePersonByNationalCode(String nationalCode,PersonSoapDto personSoapDto,String correlation);
    DeleteSoapPersonDto deletePersonByNationalCode(String nationalCode,String correlation);
}
