package com.saber.spring_webflux_camel_rest_client.routes;

public interface Routes {

    String FIND_ALL_PERSON_ROUTE_GROUP = "find-all-person-route-group";
    String FIND_ALL_PERSON_ROUTE_GATEWAY = "find-all-person-route-gateway";
    String FIND_ALL_PERSON_ROUTE_GATEWAY_OUT = "find-all-person-route-gateway-out";

    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP = "find-person-by-nationalCode-route-group";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "find-person-by-nationalCode-route-gateway";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "find-person-by-nationalCode-route-gateway-out";

    String ADD_PERSON_ROUTE_GROUP = "add-person-route-group";
    String ADD_PERSON_ROUTE_GATEWAY = "add-person-route-gateway";
    String ADD_PERSON_ROUTE_GATEWAY_OUT = "add-person-route-gateway-out";


    String UPDATE_PERSON_ROUTE_GROUP = "update-person-route-group";
    String UPDATE_PERSON_ROUTE_GATEWAY = "update-person-route-gateway";
    String UPDATE_PERSON_ROUTE_GATEWAY_OUT = "update-person-route-gateway-out";

    String DELETE_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP = "delete-person-by-nationalCode-route-group";
    String DELETE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "delete-person-by-nationalCode-route-gateway";
    String DELETE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "delete-person-by-nationalCode-route-gateway-out";

    String HTTP_OPERATION_EXCEPTION_HANDLER_ROUTE = "http-operation-exception-handler-route";
    String TIMEOUT_EXCEPTION_HANDLER_ROUTE = "timeout-exception-handler-route";
    String JSON_EXCEPTION_HANDLER_ROUTE = "json-exception-handler-route";
    String EXCEPTION_HANDLER_ROUTE_GROUP = "exception-handler-route-group";

    String CONSUL_REGISTRATION_ROUTE = "consul-registration-route";
    String CONSUL_REGISTRATION_ROUTE_GROUP = "consul-registration-route-group";

    String ADD_TOKEN_ROUTE = "add-token-route";
    String ADD_TOKEN_ROUTE_GROUP = "add-token-route-group";

}