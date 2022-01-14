package com.saber.spring_camel_service_provider.dto;

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
}
