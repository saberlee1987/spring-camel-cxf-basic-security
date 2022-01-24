package com.saber.spring_camel_cxf_soap_provider.soap.services;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthHeader",propOrder = {
        "username",
        "password",
})
public class AuthHeader {
    @NotBlank(message = "username is Required")
    @XmlElement(required = true)
    private String username;
    @NotBlank(message = "password is Required")
    @XmlElement(required = true)
    private String password;

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, AuthHeader.class);
    }
}
