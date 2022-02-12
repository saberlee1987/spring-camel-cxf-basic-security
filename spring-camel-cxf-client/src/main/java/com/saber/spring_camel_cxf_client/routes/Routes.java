package com.saber.spring_camel_cxf_client.routes;

public interface Routes {

    String FIND_ALL_PERSONS_ROUTE_GATEWAY = "find-all-persons-route-gateway";
    String FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT = "find-all-persons-route-gateway-out";
    String FIND_ALL_PERSONS_ROUTE_GROUP = "find-all-persons-route-group";

    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "find-person-by-nationalCode-route-gateway";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "find-person-by-nationalCode-route-gateway-out";
    String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP = "find-person-by-nationalCode-route-group";

    String ADD_PERSON_ROUTE_GATEWAY = "add-person-route-gateway";
    String ADD_PERSON_ROUTE_GATEWAY_OUT = "add-person-route-gateway-out";
    String ADD_PERSON_ROUTE_GROUP = "add-person-route-group";
    
    String UPDATE_PERSON_ROUTE_GATEWAY = "update-person-route-gateway";
    String UPDATE_PERSON_ROUTE_GATEWAY_OUT = "update-person-route-gateway-out";
    String UPDATE_PERSON_ROUTE_GROUP = "update-person-route-group";
    
    
    String DELETE_PERSON_ROUTE_GATEWAY = "delete-person-route-gateway";
    String DELETE_PERSON_ROUTE_GATEWAY_OUT = "delete-person-route-gateway-out";
    String DELETE_PERSON_ROUTE_GROUP = "delete-person-route-group";
    
    
    String ADD_TOKEN_ROUTE = "add-token-route";

    String PERSON_CLIENT_ROUTE_GROUP = "person-client-route-group";
	
	String TIME_OUT_EXCEPTION_ROUTE = "timeout-exception-route" ;
	String EXCEPTION_ROUTE_GROUP = "exception-route-group" ;
}
