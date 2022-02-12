package com.saber.spring_camel_cxf_client.routes;

import com.saber.spring_camel_cxf_client.dto.soap.PersonSoapDto;
import com.saber.spring_camel_cxf_client.dto.soap.UpdatePersonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UpdatePersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
                .to(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.UPDATE_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.UPDATE_PERSON_ROUTE_GROUP)
                .log("Request for update person with nationalCode ${in.header.nationalCode} with body =====>  ${in.body}")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode,String.class);
                    PersonSoapDto personSoapDto = exchange.getIn().getBody(PersonSoapDto.class);
                    List<Object> params = new ArrayList<>();
                    params.add(nationalCode);
                    params.add(personSoapDto);
                    exchange.getIn().setBody(params);
                })
                .setHeader(CxfConstants.OPERATION_NAME,constant("UpdatePersonByNationalCode"))
                .to("cxf:bean:personSoapClient")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode,String.class);
                    UpdatePersonResponse response = (UpdatePersonResponse) exchange.getIn().getBody(MessageContentsList.class).get(0);
                    log.info("Response for add person with nationalCode {}  with body ===> {}",nationalCode,  response);
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200 ));
    }
}
