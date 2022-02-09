package com.saber.spring_camel_cxf_client.adviser;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.exceptions.GatewayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvise extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = GatewayException.class)
	public ResponseEntity<?> handleGatewayException(GatewayException gatewayException) {
		int statusCode = gatewayException.getStatusCode();
		ErrorResponse errorResponse = gatewayException.getErrorResponse();
		
		log.error("Error for GatewayException with statusCode {} , body {} ", statusCode, errorResponse);
		return ResponseEntity.status(statusCode).body(errorResponse);
	}
}
