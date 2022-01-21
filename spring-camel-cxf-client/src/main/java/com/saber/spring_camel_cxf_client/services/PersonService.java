package com.saber.spring_camel_cxf_client.services;

import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;

public interface PersonService {
    FindAllPersonsResponse findAll();
}
