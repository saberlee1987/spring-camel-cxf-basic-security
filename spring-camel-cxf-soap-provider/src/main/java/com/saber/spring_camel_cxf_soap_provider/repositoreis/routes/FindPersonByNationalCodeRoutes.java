package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.ErrorSoapResponse;
import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapDto;
import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class FindPersonByNationalCodeRoutes extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
                .routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY)
                .routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
                .log("Request for find person by nationalCode : ${in.header.nationalCode}")
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
                .routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.CHECK_BEAN_VALIDATION_ROUTE))
                .choice()
                .when(body().isInstanceOf(ErrorSoapResponse.class))
                .log("bean validation exception with error body ===> ${in.body}")
                .to(String.format("direct:%s", Routes.BEAN_VALIDATION_ROUTE))
                .otherwise()
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put(Headers.nationalCode, nationalCode);
                    exchange.getIn().setBody(parameters);
                })
                .to("sql:select * from persons where nationalCode=:#nationalCode?outputType=selectOne&outputClass=" + PersonSoapDto.class.getName())
                .log("Response for find person by nationalCode  : ${in.header.nationalCode} with body  ===> ${in.body}")
                .to(String.format("direct:%s", Routes.CHECK_PERSON_EXIST_ROUTE))
                .end();


        from(String.format("direct:%s", Routes.CHECK_PERSON_EXIST_ROUTE))
                .routeId(Routes.CHECK_PERSON_EXIST_ROUTE)
                .routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
                .choice()
                .when(body().isNull())
                .log("person with nationalCode ${in.header.nationalCode} does not exist")
                .to(String.format("direct:%s", Routes.RESOURCE_NOT_FOUND_EXCEPTION_ROUTE))
                .otherwise()
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_RESULT))
                .end();


        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_RESULT))
                .routeId(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_RESULT)
                .routeGroup(Routes.FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP)
                .process(exchange -> {
                    String response = exchange.getIn().getBody(String.class);
                    response = String.format("{\"response\":%s}", response);
                    exchange.getIn().setBody(response);
                })
                .log("Response for find person by nationalCode : ${in.header.nationalCode} with body ===> ${in.body}")
                .unmarshal().json(JsonLibrary.Jackson, PersonSoapResponse.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
