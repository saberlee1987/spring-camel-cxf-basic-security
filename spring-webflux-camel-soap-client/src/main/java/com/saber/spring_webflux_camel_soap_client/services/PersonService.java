package com.saber.spring_webflux_camel_soap_client.services;


import com.saber.spring_webflux_camel_soap_client.dto.FindAllResponseDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.AddPersonSoapResponseDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.DeleteSoapPersonDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.PersonSoapDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.UpdatePersonSoapResponseDto;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<FindAllResponseDto> findAll(String correlation);
    Mono<PersonSoapDto> findPersonByNationalCode(String nationalCode,String correlation);
    Mono<AddPersonSoapResponseDto> addPerson(PersonSoapDto personSoapDto,String correlation);
    Mono<UpdatePersonSoapResponseDto> updatePersonByNationalCode(String nationalCode, PersonSoapDto personSoapDto,String correlation);
    Mono<DeleteSoapPersonDto> deletePersonByNationalCode(String nationalCode,String correlation);
}
