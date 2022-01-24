package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAllPersonsResponse",propOrder = {
        "response",
        "error",

})
@Data
public class FindAllPersonsResponse {
    private List<PersonSoapDto> response;
    private ErrorSoapResponse error;

    public FindAllPersonsResponse() {
    }

    public FindAllPersonsResponse(List<PersonSoapDto> response) {
        this.response = response;
    }

    public FindAllPersonsResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public FindAllPersonsResponse(List<PersonSoapDto> response, ErrorSoapResponse error) {
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
                .create().toJson(this, FindAllPersonsResponse.class);
    }
}
