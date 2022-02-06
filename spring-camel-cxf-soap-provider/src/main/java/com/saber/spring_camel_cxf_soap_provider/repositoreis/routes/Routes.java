package com.saber.spring_camel_cxf_soap_provider.repositoreis.routes;

public interface Routes {

	String FIND_ALL_PERSON_ROUTE_GATEWAY = "find-all-person-route-gateway";
	String FIND_ALL_PERSON_ROUTE_GROUP= "find-all-person-route-group";
	String FIND_ALL_PERSON_ROUTE_GATEWAY_OUT = "find-all-person-route-gateway-out";

	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "find-person-by-national-code-route-gateway";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GROUP= "find-person-by-national-code-route-group";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_RESULT= "find-person-by-national-code-route-result";
	String FIND_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "find-person-by-national-code-route-gateway-out";
	
	String ADD_PERSON_ROUTE_GATEWAY = "add-person-route-gateway";
	String ADD_PERSON_ROUTE_GROUP= "add-person-route-group";
	String ADD_PERSON_ROUTE_GATEWAY_OUT = "add-person-route-gateway-out";
	
	
	String UPDATE_PERSON_ROUTE_GATEWAY = "update-person-route-gateway";
	String UPDATE_PERSON_ROUTE_GROUP= "update-person-route-group";
	String UPDATE_PERSON_ROUTE_GATEWAY_OUT = "update-person-route-gateway-out";
	
	
	String DELETE_PERSON_ROUTE_GATEWAY = "delete-person-route-gateway";
	String DELETE_PERSON_ROUTE_GROUP= "delete-person-route-group";
	String DELETE_PERSON_ROUTE_GATEWAY_OUT = "delete-person-route-gateway-out";
	
	String CHECK_PERSON_EXIST_ROUTE = "check-person-exist-route";
	String BEAN_VALIDATION_ROUTE = "bean-validation-route";
	String CHECK_BEAN_VALIDATION_ROUTE = "check-bean-validation-route";
	String RESOURCE_NOT_FOUND_EXCEPTION_ROUTE = "resource-not-found-exception-route";
	String RESOURCE_DUPLICATION_EXCEPTION_ROUTE = "resource-duplication-exception-route";
	String BEAN_VALIDATION_EXCEPTION_ROUTE = "bean-validation-exception-route";
	String EXCEPTION_HANDLER_ROUTE_GROUP = "exception-handler-route-group";
}
