package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.AddPersonResponseDto;
import com.saber.spring_camel_cxf_soap_provider.soap.services.AddPersonSoapResponseDto;
import com.saber.spring_camel_cxf_soap_provider.soap.services.ErrorSoapResponse;
import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AddPersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .log("Request for add person with body ${in.body}")
                .to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .log("Request for add person with body ===> ${in.body}")
                .setHeader(Headers.request_body,simple("${in.body}"))
                .to(String.format("direct:%s", Routes.CHECK_BEAN_VALIDATION_ROUTE))
                .choice()
                .when(body().isInstanceOf(ErrorSoapResponse.class))
                    .log("bean validation exception with error body ===> ${in.body}")
                    .to(String.format("direct:%s", Routes.BEAN_VALIDATION_ROUTE))
                .otherwise()
                .process(exchange -> {
                    PersonSoapDto personSoapDto = exchange.getIn().getBody(PersonSoapDto.class);
                    String nationalCode = personSoapDto.getNationalCode();
                    exchange.getIn().setHeader(Headers.nationalCode,nationalCode);
                })
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
               .choice()
                .when(header(Exchange.HTTP_RESPONSE_CODE).convertTo(Integer.class).isEqualTo(200))
                    .log("person with nationalCode ${in.header.nationalCode} already exist")
                    .to(String.format("direct:%s", Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE))
                .otherwise()
                .process(exchange -> {
                    PersonSoapDto personSoapDto = exchange.getIn().getHeader(Headers.request_body,PersonSoapDto.class);
                    Map<String,Object> parameters = new HashMap<>();
                    parameters.put("firstname",personSoapDto.getFirstname());
                    parameters.put("lastname",personSoapDto.getLastname());
                    parameters.put("nationalCode",personSoapDto.getNationalCode());
                    parameters.put("age",personSoapDto.getAge());
                    parameters.put("email",personSoapDto.getEmail());
                    parameters.put("mobile",personSoapDto.getMobile());
                    exchange.getIn().setBody(parameters);
                })
                .to("sql:insert into persons (firstname,lastname,nationalCode,age,email,mobile) values (:#firstname,:#lastname,:#nationalCode,:#age,:#email,:#mobile)")
                .log("Response for find person by nationalCode  : ${in.header.nationalCode} with body  ===> ${in.body}")
                .process(exchange -> {
                    AddPersonResponseDto addPersonResponseDto = new AddPersonResponseDto();
                    AddPersonSoapResponseDto addPersonSoapResponseDto = new AddPersonSoapResponseDto();
                    addPersonSoapResponseDto.setCode(0);
                    addPersonSoapResponseDto.setText("your data saved successfully");
                    addPersonResponseDto.setResponse(addPersonSoapResponseDto);
                    exchange.getIn().setBody(addPersonResponseDto);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .end();


    }
}
