package com.saber.spring_camel_service_provider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.spring_camel_service_provider.soap.PersonSoapRoute;
import com.saber.spring_camel_service_provider.soap.PersonSoapService;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SoapConfig {

    @Value(value = "${cxf.endpoint}")
    private String endpoint;
    private final PersonSoapService personSoapService;
    private final ObjectMapper mapper;
   // private final CustomUsernameTokenValidator customUsernameTokenValidator;

    @Bean
    public PersonSoapRoute personSoapRoute(){
        PersonSoapRoute soapRoute = new PersonSoapRoute(personSoapService,mapper);
        soapRoute.setUrl(String.format("cxf:%s?serviceClass=%s",
                 endpoint,PersonSoapService.class.getName()));
        return soapRoute;
    }

   // @Bean
    public WSS4JInInterceptor wss4JInInterceptor(){
        WSS4JInInterceptor wss4JInInterceptor = new WSS4JInInterceptor();
        Map<String,Object> config = new HashMap<>();
        config.put("action","UsernameToken");
        config.put("passwordType","PasswordText");
        wss4JInInterceptor.setProperties(config);
        return wss4JInInterceptor;
    }

   // @Bean(value = "securing-webservice")
    public Bus cxfBus(){

        BusFactory busFactory = CXFBusFactory.newInstance();
        Bus bus = busFactory.createBus();
        Map<String,Object> config = new HashMap<>();
       // config.put("ws-security.ut.validator",customUsernameTokenValidator);
        bus.setProperties(config);
        bus.getInInterceptors().add(wss4JInInterceptor());
        return bus;
    }
}
