package com.saber.spring_camel_service_provider.dto;


import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import com.saber.spring_camel_service_provider.entity.PersonEntity;
import lombok.Data;

import java.util.List;

@Data
public class PersonResponse {
    private List<PersonEntity> persons ;


    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, PersonResponse.class);
    }
}
