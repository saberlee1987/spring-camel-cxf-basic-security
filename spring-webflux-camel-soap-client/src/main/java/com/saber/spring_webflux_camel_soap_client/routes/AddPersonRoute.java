package com.saber.spring_webflux_camel_soap_client.routes;


import com.saber.spring_webflux_camel_soap_client.dto.soap.AddPersonResponseDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.PersonSoapDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AddPersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
                .to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .log("Request for correlation : ${in.header.correlation} ,  add person with body =====>  ${in.body}")
                .process(exchange -> {
                    PersonSoapDto personSoapDto = exchange.getIn().getBody(PersonSoapDto.class);
                    List<Object> params = new ArrayList<>();
                    params.add(personSoapDto);
                     exchange.getIn().setBody(params);
                })
                .setHeader(CxfConstants.OPERATION_NAME,constant("AddPerson"))
                .to("cxf:bean:personSoapClient")
                .process(exchange -> {
                    AddPersonResponseDto response = (AddPersonResponseDto) exchange.getIn().getBody(MessageContentsList.class).get(0);
                    log.info("Response for correlation : {} , add person  with body ===> {}"
                            ,exchange.getIn().getHeader(Headers.correlation)
                            ,  response);
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200 ));
    }
}
