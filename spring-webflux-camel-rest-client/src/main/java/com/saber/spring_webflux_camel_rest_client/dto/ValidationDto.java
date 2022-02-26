package com.saber.spring_webflux_camel_rest_client.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

@Data
public class ValidationDto implements Cloneable {
    private String fieldName;
    private String detailMessage;

    @Override
    public ValidationDto clone()  {
        try{
            return (ValidationDto) super.clone();
        }catch (Exception ex){
            return null;
        }
    }
    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, ValidationDto.class);
    }
}
