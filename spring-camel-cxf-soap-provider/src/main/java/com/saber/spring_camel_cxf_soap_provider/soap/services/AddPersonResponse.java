package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddPersonResponse",propOrder = {
        "response",
        "error",

})
@Data
public class AddPersonResponse {
    private AddPersonSoapResponseDto response;
    private ErrorSoapResponse error;

    public AddPersonResponse() {
    }

    public AddPersonResponse(AddPersonSoapResponseDto response) {
        this.response = response;
    }

    public AddPersonResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public AddPersonResponse(AddPersonSoapResponseDto response, ErrorSoapResponse error) {
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
                .create().toJson(this, AddPersonResponse.class);
    }
}
