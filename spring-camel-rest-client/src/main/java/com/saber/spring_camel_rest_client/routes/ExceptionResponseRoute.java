package com.saber.spring_camel_rest_client.routes;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.saber.spring_camel_rest_client.dto.ErrorResponse;
import com.saber.spring_camel_rest_client.dto.ServiceErrorResponseEnum;
import com.saber.spring_camel_rest_client.dto.ValidationDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.support.processor.PredicateValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ExceptionResponseRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		
		
		from(String.format("direct:%s",Routes.TIMEOUT_EXCEPTION_HANDLER_ROUTE))
				.routeId(Routes.TIMEOUT_EXCEPTION_HANDLER_ROUTE)
				.routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(504))
				.process(exchange -> {
					Exception exception= exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
				
					String exceptionMessage = exception.getLocalizedMessage();
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.TIMEOUT_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.TIMEOUT_EXCEPTION.getMessage());
					errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.GATEWAY_TIMEOUT.value(),exceptionMessage));
				
					log.error("Error for timeout-exception statusCode {} ====> {}",HttpStatus.GATEWAY_TIMEOUT.value(),errorResponse);
					exchange.getIn().setBody(errorResponse);
				});
		
		
		from(String.format("direct:%s",Routes.JSON_EXCEPTION_HANDLER_ROUTE))
				.routeId(Routes.JSON_EXCEPTION_HANDLER_ROUTE)
				.routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(406))
				.process(exchange -> {
					UnrecognizedPropertyException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,UnrecognizedPropertyException.class);
					
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.JSON_ERROR_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.JSON_ERROR_EXCEPTION.getMessage());
					
					List<ValidationDto> validationErrors = new ArrayList<>();
					ValidationDto validationError=new ValidationDto();
					if (exception!=null){
						String exceptionMessage =exception.getMessage();
						log.error("Error Exception Message  correlation ===> {} error =======> {}",exchange.getIn().getHeader("correlation"), exceptionMessage );
						log.error("Error json-exception correlation ===> {} error =======> {} ",exchange.getIn().getHeader("correlation"), exceptionMessage );
						validationError.setFieldName(exception.getPropertyName());
						validationError.setDetailMessage(exception.getOriginalMessage());
						validationErrors.add(validationError);
						errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.NOT_ACCEPTABLE.value(),exceptionMessage));
					}else{
						Exception ex=exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
						String exceptionMessage =ex.getMessage();
						log.error("Error Exception Message correlation ===> {} error =======> {}",exchange.getIn().getHeader("correlation"), exceptionMessage );
						log.error("Error json-exception correlation ===> {} error =======> {} ",exchange.getIn().getHeader("correlation"), exceptionMessage );
						validationError.setDetailMessage(exceptionMessage);
						validationErrors.add(validationError);
						errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.NOT_ACCEPTABLE.value(),exceptionMessage));
					}
					errorResponse.setValidations(validationErrors);
					
					log.error("Error for json-exception statusCode {} ====> {}",HttpStatus.NOT_ACCEPTABLE.value(),errorResponse);
					exchange.getIn().setBody(errorResponse);
				});
		
		
		from(String.format("direct:%s",Routes.BEAN_VALIDATION_EXCEPTION_HANDLER_ROUTE))
				.routeId(Routes.BEAN_VALIDATION_EXCEPTION_HANDLER_ROUTE)
				.routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(406))
				.process(exchange -> {
					BeanValidationException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,BeanValidationException.class);
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
					
					List<ValidationDto> validationDtoList = new ArrayList<>();
					for (ConstraintViolation<Object> constraintViolation : exception.getConstraintViolations()) {
						ValidationDto validationDto = new ValidationDto();
						validationDto.setFieldName(constraintViolation.getPropertyPath().toString());
						validationDto.setDetailMessage(constraintViolation.getMessage());
						
						validationDtoList.add(validationDto);
					}
				
					errorResponse.setValidations(validationDtoList);
					
					log.error("Error for bean-validation statusCode {} ====> {}",HttpStatus.NOT_ACCEPTABLE.value(),errorResponse);
					
					exchange.getIn().setBody(errorResponse);
				});
		
		
		from(String.format("direct:%s",Routes.PREDICATE_EXCEPTION_HANDLER_ROUTE))
				.routeId(Routes.PREDICATE_EXCEPTION_HANDLER_ROUTE)
				.routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(406))
				.process(exchange -> {
					PredicateValidationException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,PredicateValidationException.class);
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
					
					List<ValidationDto> validationDtoList = new ArrayList<>();
				
					ValidationDto validationDto = new ValidationDto();
					validationDto.setFieldName(	exception.getPredicate().toString());
					validationDto.setDetailMessage(exception.getLocalizedMessage());
					
					validationDtoList.add(validationDto);
					
					errorResponse.setValidations(validationDtoList);
					
					log.error("Error for predicate-exception statusCode {} ====> {}",HttpStatus.NOT_ACCEPTABLE.value(),errorResponse);
					
					exchange.getIn().setBody(errorResponse);
				});
		
		from(String.format("direct:%s",Routes.HTTP_OPERATION_EXCEPTION_HANDLER_ROUTE))
				.routeId(Routes.HTTP_OPERATION_EXCEPTION_HANDLER_ROUTE)
				.routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
				.process(exchange -> {
					HttpOperationFailedException exception= exchange.getProperty(Exchange.EXCEPTION_CAUGHT,HttpOperationFailedException.class);
					int statusCode = exception.getHttpResponseCode();
					String responseBody = exception.getResponseBody();
					
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setCode(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getCode());
					errorResponse.setMessage(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getMessage());
					if (responseBody.trim().startsWith("{")){
						errorResponse.setOriginalMessage(responseBody);
					}
					else{
						errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}",statusCode,statusCode));
					}
					
					log.error("Error for httpOperationException statusCode {} ====> {}",statusCode,errorResponse);
					exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE,statusCode);
					exchange.getIn().setBody(errorResponse);
				});
	}
}
