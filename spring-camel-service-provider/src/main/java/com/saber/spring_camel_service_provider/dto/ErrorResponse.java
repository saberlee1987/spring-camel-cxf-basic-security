package com.saber.spring_camel_service_provider.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private Integer code;
    private String message;
    @JsonRawValue
    private String originalMessage;
    private List<ValidationDto> validations;
}
