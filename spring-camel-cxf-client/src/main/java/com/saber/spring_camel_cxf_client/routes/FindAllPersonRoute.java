package com.saber.spring_camel_cxf_client.routes;

import com.saber.spring_camel_cxf_client.dto.soap.AuthHeader;
import com.saber.spring_camel_cxf_client.dto.soap.FindAllPersonsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FindAllPersonRoute extends AbstractRestRouteBuilder {

    @Value(value = "${service.person-api.username}")
    private String username;
    @Value(value = "${service.person-api.password}")
    private String password;

    @Override
    public void configure() throws Exception {
        super.configure();

        from(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY))
                .routeId(Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY)
                .routeGroup(Routes.FIND_ALL_PERSONS_ROUTE_GROUP)
                .log("Request for find All persons")
                .to(String.format("direct:%s", Routes.ADD_TOKEN_ROUTE))
                .to(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT))
                .routeId(Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.FIND_ALL_PERSONS_ROUTE_GROUP)
                .process(exchange -> {
                    List<Object> params = new ArrayList<>();
                    AuthHeader authHeader = new AuthHeader();
                    authHeader.setUsername(username);
                    authHeader.setPassword(password);
                    params.add(authHeader);
                    exchange.getIn().setBody(params);
                })
                .setHeader(CxfConstants.OPERATION_NAME,constant("FindAll"))
                .to("cxf:bean:personSoapClient")
                .process(exchange -> {
                    FindAllPersonsResponse response = (FindAllPersonsResponse) exchange.getIn().getBody(MessageContentsList.class).get(0);
                    log.info("Response for find All Persons with body ===> {}", gson.toJson(response, FindAllPersonsResponse.class));
                    exchange.getIn().setBody(response);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200 ));
    }
}
