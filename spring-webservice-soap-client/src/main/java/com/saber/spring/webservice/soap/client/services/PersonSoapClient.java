package com.saber.spring.webservice.soap.client.services;


import com.saber.spring.webservice.soap.client.dto.FindAllResponseDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.AddPersonSoapResponseDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.DeleteSoapPersonDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.PersonSoapDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.UpdatePersonSoapResponseDto;

public interface PersonSoapClient {
    FindAllResponseDto findAllPersonsResponse(String correlation);
    PersonSoapDto findByNationalCode(String nationalCode, String correlation);
    AddPersonSoapResponseDto addPerson(PersonSoapDto personSoapDto, String correlation);
    UpdatePersonSoapResponseDto updatePerson(PersonSoapDto personSoapDto, String nationalCode, String correlation);
    DeleteSoapPersonDto deletePersonByNationalCode(String nationalCode, String correlation);
}
