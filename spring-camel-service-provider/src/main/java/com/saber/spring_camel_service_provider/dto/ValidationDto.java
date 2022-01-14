package com.saber.spring_camel_service_provider.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("fieldName", fieldName)
                .append("detailMessage", detailMessage)
                .toString();
    }
}
