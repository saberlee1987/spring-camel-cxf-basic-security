package com.saber.spring.webservice.soap.client.adviser;


import com.saber.spring.webservice.soap.client.dto.ErrorResponse;
import com.saber.spring.webservice.soap.client.dto.ServiceErrorResponseEnum;
import com.saber.spring.webservice.soap.client.dto.ValidationDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.ErrorSoapResponse;
import com.saber.spring.webservice.soap.client.dto.wsdl.ValidationSoapDto;
import com.saber.spring.webservice.soap.client.exceptions.GatewayException;
import com.saber.spring.webservice.soap.client.exceptions.PersonSoapException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
		
		log.error("Error for  correlation : {} , GatewayException with statusCode {} , body {} "
				,gatewayException.getCorrelation()
				, statusCode, errorResponse);
		return ResponseEntity.status(statusCode).body(errorResponse);
	}

	@ExceptionHandler(value = PersonSoapException.class)
	public ResponseEntity<Object> handlePersonSoapException(PersonSoapException personSoapException) {
		int statusCode = personSoapException.getStatusCode();
		ErrorSoapResponse errorSoapResponse = personSoapException.getErrorResponse();

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
		log.error("Error for  correlation : {} , GatewayException with statusCode {} , body {} "
				,personSoapException.getCorrelation()
				, statusCode, errorResponse);
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

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

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