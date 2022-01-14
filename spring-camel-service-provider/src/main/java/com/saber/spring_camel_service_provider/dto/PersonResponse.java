package com.saber.spring_camel_service_provider.dto;


import com.saber.spring_camel_service_provider.entity.PersonEntity;
import lombok.Data;
import java.util.List;

@Data
public class PersonResponse {
    private List<PersonEntity> persons ;
}
