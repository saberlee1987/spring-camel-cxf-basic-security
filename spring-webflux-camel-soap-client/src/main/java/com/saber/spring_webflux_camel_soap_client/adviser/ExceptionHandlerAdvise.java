package com.saber.spring_webflux_camel_soap_client.adviser;


import com.saber.spring_webflux_camel_soap_client.dto.ErrorResponse;
import com.saber.spring_webflux_camel_soap_client.dto.ServiceErrorResponseEnum;
import com.saber.spring_webflux_camel_soap_client.dto.ValidationDto;
import com.saber.spring_webflux_camel_soap_client.dto.soap.ErrorSoapResponse;
import com.saber.spring_webflux_camel_soap_client.dto.soap.ValidationSoapDto;
import com.saber.spring_webflux_camel_soap_client.exceptions.GatewayException;
import com.saber.spring_webflux_camel_soap_client.exceptions.PersonSoapException;
import com.saber.spring_webflux_camel_soap_client.routes.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvise  {
	
	@ExceptionHandler(value = GatewayException.class)
	public ResponseEntity<Object> handleGatewayException(GatewayException gatewayException) {
		int statusCode = gatewayException.getStatusCode();
		ErrorResponse errorResponse = gatewayException.getErrorResponse();
		String correlation = gatewayException.getCorrelation();
		
		log.error("Error for correlation : {} ,  GatewayException with statusCode {} , body {} "
				,correlation, statusCode, errorResponse);
		return ResponseEntity.status(statusCode).body(errorResponse);
	}

	@ExceptionHandler(value = PersonSoapException.class)
	public ResponseEntity<Object> handlePersonSoapException(PersonSoapException personSoapException) {
		int statusCode = personSoapException.getStatusCode();
		ErrorSoapResponse errorSoapResponse = personSoapException.getErrorResponse();
		String correlation = personSoapException.getCorrelation();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(errorSoapResponse.getCode());
		errorResponse.setMessage(errorSoapResponse.getMessage());
		errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}",statusCode,errorSoapResponse.getOriginalMessage()));

		List<ValidationDto> validationDtoList = new ArrayList<>();
		if (errorSoapResponse.getValidations()!=null){
			for (ValidationSoapDto validation : errorSoapResponse.getValidations()) {
				ValidationDto validationDto = new ValidationDto();
				validationDto.setFieldName(validation.getFieldName());
				validationDto.setDetailMessage(validation.getDetailMessage());
				validationDtoList.add(validationDto);
			}
		}
		errorResponse.setValidations(validationDtoList);
		log.error("Error for correlation : {} , GatewayException with statusCode {} , body {} "
				,correlation, statusCode, errorResponse);
		return ResponseEntity.status(statusCode).body(errorResponse);
	}
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, ServerWebExchange exchange){
		String correlation =getCorrelation(exchange);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
		errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
		List<ValidationDto> validationDtoList = new ArrayList<>();
		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			ValidationDto validationDto  = new ValidationDto();
			validationDto.setFieldName(violation.getPropertyPath().toString());
			validationDto.setDetailMessage(violation.getMessage());
			validationDtoList.add(validationDto);
		}
		errorResponse.setValidations(validationDtoList);

		log.error("Error for correlation : {} , handleConstraintViolationException with body ===> {}"
				,correlation,errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(value = WebExchangeBindException.class)
	public ResponseEntity<Object> handleWebExchangeBindException(WebExchangeBindException exception, ServerWebExchange exchange) {
		String correlation =getCorrelation(exchange);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
		errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
		List<ValidationDto> validationDtoList = new ArrayList<>();
		for (FieldError fieldError : exception.getFieldErrors()) {
			ValidationDto validationDto  = new ValidationDto();
			validationDto.setFieldName(fieldError.getField());
			validationDto.setDetailMessage(fieldError.getDefaultMessage());
			validationDtoList.add(validationDto);
		}
		errorResponse.setValidations(validationDtoList);

		log.error("Error for correlation : {} , handleWebExchangeBindException with body ===> {}"
				,correlation,errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	private String getCorrelation(ServerWebExchange exchange){
		String correlation = "";
		HttpHeaders headers = exchange.getRequest().getHeaders();
		List<String> correlations = headers.get(Headers.correlation);
		if(correlations!=null && !correlations.isEmpty()){
			correlation = correlations.get(0);
		}else{
			correlation = UUID.randomUUID().toString();
		}
		return correlation;
	}

}