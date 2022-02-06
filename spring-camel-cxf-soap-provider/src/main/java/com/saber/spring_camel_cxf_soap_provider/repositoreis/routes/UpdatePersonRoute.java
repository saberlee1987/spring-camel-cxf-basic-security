package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UpdatePersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
                .log("Request for update person with nationalCode ${in.header.nationalCode} ,  body ${in.body}")
                .to(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
                .log("Request for update person with nationalCode ${in.header.nationalCode} ,  body ===> ${in.body}")
                .setHeader(Headers.request_body,simple("${in.body}"))
                .to(String.format("direct:%s", Routes.CHECK_BEAN_VALIDATION_ROUTE))
                .choice()
                .when(body().isInstanceOf(ErrorSoapResponse.class))
                    .log("bean validation exception with error body ===> ${in.body}")
                    .to(String.format("direct:%s", Routes.BEAN_VALIDATION_ROUTE))
                .otherwise()
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
               .choice()
                .when(header(Exchange.HTTP_RESPONSE_CODE).convertTo(Integer.class).isNotEqualTo(200))
                    .log("person with nationalCode ${in.header.nationalCode} does not exist")
                    .to(String.format("direct:%s", Routes.RESOURCE_NOT_FOUND_EXCEPTION_ROUTE))
                .otherwise()
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    PersonSoapDto personSoapDto = exchange.getIn().getHeader(Headers.request_body,PersonSoapDto.class);
                    Map<String,Object> parameters = new HashMap<>();
                    parameters.put("firstname",personSoapDto.getFirstname());
                    parameters.put("lastname",personSoapDto.getLastname());
                    parameters.put("nationalCode",personSoapDto.getNationalCode());
                    parameters.put("age",personSoapDto.getAge());
                    parameters.put("email",personSoapDto.getEmail());
                    parameters.put("mobile",personSoapDto.getMobile());
                    parameters.put("originalNationalCode", nationalCode);
                    exchange.getIn().setBody(parameters);
                })
                .to("sql:update  persons set firstname=:#firstname ,lastname=:#lastname ,nationalCode=:#nationalCode, age=:#age ,email=:#email , mobile=:#mobile where nationalCode=:#originalNationalCode")
                .log("Response for update person by nationalCode  : ${in.header.nationalCode} with body  ===> ${in.body}")
                .process(exchange -> {
                    UpdatePersonResponse updatePersonResponse = new UpdatePersonResponse();
                    UpdatePersonSoapResponseDto updatePersonSoapResponseDto = new UpdatePersonSoapResponseDto();
                    updatePersonSoapResponseDto.setCode(0);
                    updatePersonSoapResponseDto.setText("your data updated successfully");
                    updatePersonResponse.setResponse(updatePersonSoapResponseDto);
                    exchange.getIn().setBody(updatePersonResponse);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .end();


    }
}
