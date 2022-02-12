package com.example.spring_webflux_rest_client.adviser;


import com.example.spring_webflux_rest_client.dto.ErrorResponse;
import com.example.spring_webflux_rest_client.dto.ServiceErrorResponseEnum;
import com.example.spring_webflux_rest_client.dto.ValidationDto;
import com.example.spring_webflux_rest_client.exceptions.GatewayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler  {
	
	@ExceptionHandler(GatewayException.class)
	public ResponseEntity<Object> handleGatewayException(GatewayException exception){
		
		log.error("Error for handleGatewayException with statusCode {} ===> {}",exception.getStatusCode(),exception.getErrorResponse());
		
		return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorResponse());
		
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
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		
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
		
		log.error("Error for handleMethodArgumentNotValid with body ===> {}",errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
