package com.saber.spring_camel_rest_client.routes;

public interface Routes {
	String FIND_ALL_PERSON_ROUTE_ID = "find-all-person-route-id";
	String FIND_ALL_PERSON_ROUTE = "find-all-person-route";
	String FIND_ALL_PERSON_ROUTE_GROUP = "find-all-person-route-group";
	String FIND_ALL_PERSON_ROUTE_GATEWAY = "find-all-person-route-gateway";
	String FIND_ALL_PERSON_ROUTE_GATEWAY_OUT = "find-all-person-route-gateway-out";
	
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_ID = "find-person-by-nationalCode-route-id";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE = "find-person-by-nationalCode-route";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP = "find-person-by-nationalCode-route-group";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "find-person-by-nationalCode-route-gateway";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "find-person-by-nationalCode-route-gateway-out";
	
	String HTTP_OPERATION_EXCEPTION_HANDLER_ROUTE = "http-operation-exception-handler-route";
	String TIMEOUT_EXCEPTION_HANDLER_ROUTE = "timeout-exception-handler-route";
	String JSON_EXCEPTION_HANDLER_ROUTE = "json-exception-handler-route";
	String EXCEPTION_HANDLER_ROUTE_GROUP = "exception-handler-route-group";
	
	String ADD_TOKEN_ROUTE = "add-token-route";
	String ADD_TOKEN_ROUTE_GROUP = "add-token-route-group";
}
