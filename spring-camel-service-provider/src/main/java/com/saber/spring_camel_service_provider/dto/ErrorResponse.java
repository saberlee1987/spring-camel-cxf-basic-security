package com.saber.spring_camel_service_provider.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class ErrorResponse {
    private Integer code;
    private String message;
    @JsonRawValue
    private String originalMessage;
    private List<ValidationDto> validations;
    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("code", code)
                .append("message", message)
                .append("originalMessage", originalMessage)
                .append("validations", validations)
                .toString();
    }
}
