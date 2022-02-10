package com.saber.spring_camel_cxf_client.adviser;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;
import com.saber.spring_camel_cxf_client.dto.ServiceErrorResponseEnum;
import com.saber.spring_camel_cxf_client.dto.ValidationDto;
import com.saber.spring_camel_cxf_client.exceptions.GatewayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvise extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = GatewayException.class)
	public ResponseEntity<Object> handleGatewayException(GatewayException gatewayException) {
		int statusCode = gatewayException.getStatusCode();
		ErrorResponse errorResponse = gatewayException.getErrorResponse();
		
		log.error("Error for GatewayException with statusCode {} , body {} ", statusCode, errorResponse);
		return ResponseEntity.status(statusCode).body(errorResponse);
	}
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception){

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

		log.error("Error for handleConstraintViolationException with body ===> {}",errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

 }
