package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(name = "ValidationSoapDto",propOrder = {
        "fieldName",
        "detailMessage"
})
public class ValidationSoapDto {
    private String fieldName;
    private String detailMessage;
}
