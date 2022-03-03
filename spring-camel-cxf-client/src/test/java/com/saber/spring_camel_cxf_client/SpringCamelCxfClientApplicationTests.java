package com.saber.spring_camel_cxf_client;

import com.saber.spring_camel_cxf_client.dto.FindAllResponseDto;
import com.saber.spring_camel_cxf_client.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class SpringCamelCxfClientApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() {
        String correlation = UUID.randomUUID().toString();
        FindAllResponseDto response = personService.findAll(correlation);

        System.out.println(response);
    }

}
