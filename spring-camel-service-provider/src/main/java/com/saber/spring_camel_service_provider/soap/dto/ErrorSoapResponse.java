package com.saber.spring_camel_service_provider.soap.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
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

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, ErrorSoapResponse.class);
    }
}
