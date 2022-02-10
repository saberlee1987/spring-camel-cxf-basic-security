package com.saber.spring_camel_cxf_client.services;

import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_client.dto.soap.PersonSoapResponse;

public interface PersonService {
    FindAllPersonsResponse findAll();
    PersonSoapResponse findPersonByNationalCode(String nationalCode);
}
