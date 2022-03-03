package com.saber.spring_webflux_camel_soap_client.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import com.saber.spring_webflux_camel_soap_client.dto.soap.PersonSoapDto;
import lombok.Data;

import java.util.List;

@Data
public class FindAllResponseDto {
    protected List<PersonSoapDto> response;

    @Override
    public String toString() {
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setLenient()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create()
                .toJson(this, FindAllResponseDto.class);
    }
}
