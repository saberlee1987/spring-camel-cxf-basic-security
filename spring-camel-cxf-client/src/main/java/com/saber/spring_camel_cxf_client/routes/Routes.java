package com.saber.spring_camel_cxf_client.routes;

public interface Routes {

    String FIND_ALL_PERSONS_ROUTE_GATEWAY = "find-all-persons-route-gateway";
    String FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT = "find-all-persons-route-gateway-out";
    String FIND_ALL_PERSONS_ROUTE_GROUP = "find-all-persons-route-group";

    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "find-person-by-nationalCode-route-gateway";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "find-person-by-nationalCode-route-gateway-out";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP = "find-person-by-nationalCode-route-group";

    String ADD_TOKEN_ROUTE = "add-token-route";

    String PERSON_CLIENT_ROUTE_GROUP = "person-client-route-group";
	
	String TIME_OUT_EXCEPTION_ROUTE = "timeout-exception-route" ;
	String EXCEPTION_ROUTE_GROUP = "exception-route-group" ;
}
