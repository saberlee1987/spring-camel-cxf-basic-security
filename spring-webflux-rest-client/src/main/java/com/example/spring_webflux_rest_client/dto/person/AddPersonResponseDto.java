package com.example.spring_webflux_rest_client.dto.person;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

@Data
public class AddPersonResponseDto {
    
    private Integer id;
    private String firstname;
    private String lastname;
    private String nationalCode;
    private Integer age;
    private String email;
    private String mobile;

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
