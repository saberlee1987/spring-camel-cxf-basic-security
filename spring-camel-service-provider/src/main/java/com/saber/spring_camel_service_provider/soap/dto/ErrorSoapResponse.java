package com.saber.spring_camel_service_provider.soap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorSoapResponse",propOrder = {
        "code",
        "message",
        "originalMessage",
        "validations",

})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorSoapResponse {
    private Integer code;
    private String message;
    private String originalMessage;
    private List<ValidationSoapDto> validations;
}
