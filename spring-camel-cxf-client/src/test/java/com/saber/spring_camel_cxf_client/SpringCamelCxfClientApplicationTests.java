package com.saber.spring_camel_cxf_client;

import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import com.saber.spring_camel_cxf_client.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringCamelCxfClientApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    void contextLoads() {
        FindAllPersonsResponse response = personService.findAll();

        System.out.println(response);
    }

}
