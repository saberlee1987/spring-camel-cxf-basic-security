package com.saber.spring_rest_client.advicer;


import com.saber.spring_rest_client.dto.ErrorResponse;
import com.saber.spring_rest_client.dto.ServiceErrorResponseEnum;
import com.saber.spring_rest_client.dto.ValidationDto;
import com.saber.spring_rest_client.routes.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class PersonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        String correlation =getCorrelation(request);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
        errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
        List<ValidationDto> validationDtoList = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(violation.getPropertyPath().toString());
            validationDto.setDetailMessage(violation.getMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);

        log.error("Error for correlation : {} , handleConstraintViolationException with body ===> {}", correlation, errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientException(HttpClientErrorException exception) {

        String responseBody = exception.getResponseBodyAsString();
        int statusCode = exception.getStatusCode().value();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getCode());
        errorResponse.setMessage(ServiceErrorResponseEnum.SERVICE_PROVIDER_EXCEPTION.getMessage());
        if (responseBody.trim().startsWith("{")) {
            errorResponse.setOriginalMessage(responseBody);
        } else {
            errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", statusCode, responseBody));
        }
        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String correlation =getCorrelation(headers);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getCode());
        errorResponse.setMessage(ServiceErrorResponseEnum.INPUT_VALIDATION_EXCEPTION.getMessage());
        List<ValidationDto> validationDtoList = new ArrayList<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(fieldError.getField());
            validationDto.setDetailMessage(fieldError.getDefaultMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);

        log.error("Error for correlation : {} , handleMethodArgumentNotValid with body ===> {}", correlation, errorResponse);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private String getCorrelation(HttpHeaders headers) {
        String correlation = "";
        List<String> correlations = headers.get(Headers.correlation);
        if (correlations == null || correlations.isEmpty()) {
            correlation = UUID.randomUUID().toString();
        }else{
            correlation = correlations.get(0);
        }
        return correlation;
    }

    private String getCorrelation(HttpServletRequest httpServletRequest) {
        String correlation = "";
        correlation = httpServletRequest.getHeader(Headers.correlation);
        if (correlation == null || correlation.isEmpty()) {
            correlation = UUID.randomUUID().toString();
        }
        return correlation;
    }
}
