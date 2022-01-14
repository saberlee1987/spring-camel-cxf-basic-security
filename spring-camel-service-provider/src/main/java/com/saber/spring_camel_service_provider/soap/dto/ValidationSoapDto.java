package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(name = "ValidationSoapDto",propOrder = {
        "fieldName",
        "detailMessage"
})
public class ValidationSoapDto {
    private String fieldName;
    private String detailMessage;

    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("fieldName", fieldName)
                .append("detailMessage", detailMessage)
                .toString();
    }
}
