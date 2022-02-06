package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

import com.saber.spring_camel_cxf_soap_provider.soap.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DeletePersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.DELETE_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.DELETE_PERSON_ROUTE_GROUP)
                .log("Request for delete person with nationalCode ${in.header.nationalCode} ")
                .to(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.DELETE_PERSON_ROUTE_GROUP)
                .log("Request for delete person with nationalCode ${in.header.nationalCode}")
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
                    Map<String,Object> parameters = new HashMap<>();
                    parameters.put("nationalCode",nationalCode);
                    exchange.getIn().setBody(parameters);
                })
                .to("sql:delete from  persons  where nationalCode=:#nationalCode")
                .log("Response for delete person by nationalCode  : ${in.header.nationalCode} with body  ===> ${in.body}")
                .process(exchange -> {
                    DeletePersonResponse deletePersonResponse = new DeletePersonResponse();
                    DeleteSoapPersonDto deleteSoapPersonDto = new DeleteSoapPersonDto();
                    deleteSoapPersonDto.setCode(0);
                    deleteSoapPersonDto.setText("your data deleted successfully");
                    deletePersonResponse.setResponse(deleteSoapPersonDto);
                    exchange.getIn().setBody(deletePersonResponse);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .end();


    }
}
