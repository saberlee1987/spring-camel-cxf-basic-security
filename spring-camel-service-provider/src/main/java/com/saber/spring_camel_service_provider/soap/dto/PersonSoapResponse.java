package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapResponse",propOrder = {
        "response",
        "error",

})
@Data
public class PersonSoapResponse {
    private PersonSoapEntity response;
    private ErrorSoapResponse error;

    public PersonSoapResponse() {
    }

    public PersonSoapResponse(PersonSoapEntity response) {
        this.response = response;
    }

    public PersonSoapResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public PersonSoapResponse(PersonSoapEntity response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }
}
