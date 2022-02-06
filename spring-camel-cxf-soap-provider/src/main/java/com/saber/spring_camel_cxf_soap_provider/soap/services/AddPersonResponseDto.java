package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddPersonResponseDto",propOrder = {
        "response",
        "error",

})
@Data
public class AddPersonResponseDto {
    private AddPersonSoapResponseDto response;
    private ErrorSoapResponse error;

    public AddPersonResponseDto() {
    }

    public AddPersonResponseDto(AddPersonSoapResponseDto response) {
        this.response = response;
    }

    public AddPersonResponseDto(ErrorSoapResponse error) {
        this.error = error;
    }

    public AddPersonResponseDto(AddPersonSoapResponseDto response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }
    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, AddPersonResponseDto.class);
    }
}
