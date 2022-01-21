package com.saber.spring_camel_cxf_client.routes;

import com.google.gson.Gson;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractRestRouteBuilder extends RouteBuilder {

    @Autowired
    protected Gson gson;

    @Override
    public void configure() throws Exception {

    }
}
