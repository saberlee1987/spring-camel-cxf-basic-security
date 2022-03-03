package com.saber.spring_camel_cxf_client.routes;

import com.saber.spring_camel_cxf_client.dto.soap.DeletePersonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DeletePersonByNationalCodeRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.DELETE_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.DELETE_PERSON_ROUTE_GROUP)
                .log("Request for correlation : ${in.header.correlation} , delete person by nationalCode")
                .to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
                .to(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.DELETE_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.DELETE_PERSON_ROUTE_GROUP)
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    List<Object> params = new ArrayList<>();
                    params.add(nationalCode);
                    exchange.getIn().setBody(params);
                })
                .setHeader(CxfConstants.OPERATION_NAME,constant("DeletePersonByNationalCode"))
                .to("cxf:bean:personSoapClient")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    DeletePersonResponse response = (DeletePersonResponse) exchange.getIn().getBody(MessageContentsList.class).get(0);
                    log.info("Response for  correlation : {} , delete person by nationalCode {} ,  with body ===> {}"
                            ,exchange.getIn().getHeader(Headers.correlation)
                            ,nationalCode,  response);
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
