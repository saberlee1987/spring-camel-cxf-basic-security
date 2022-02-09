package com.saber.spring_camel_cxf_client.services.impl;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_client.exceptions.GatewayException;
import com.saber.spring_camel_cxf_client.routes.Routes;
import com.saber.spring_camel_cxf_client.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public FindAllPersonsResponse findAll() {

        Exchange responseExchange = producerTemplate.send(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY), exchange -> {

        });
        int statusCode = responseExchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE,Integer.class);
        if (statusCode!= HttpStatus.OK.value()){
            ErrorResponse errorResponse = responseExchange.getIn().getBody(ErrorResponse.class);
            throw new GatewayException(statusCode,errorResponse);
        }
        FindAllPersonsResponse response = responseExchange.getIn().getBody(FindAllPersonsResponse.class);

        log.info("Response for find All Persons ====> {}",response);
        return response;
    }
}
