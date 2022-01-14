package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAllPersonsResponse",propOrder = {
        "response",
        "error",

})
@Data
public class FindAllPersonsResponse {
    private List<PersonSoapEntity> response;
    private ErrorSoapResponse error;

    public FindAllPersonsResponse() {
    }

    public FindAllPersonsResponse(List<PersonSoapEntity> response) {
        this.response = response;
    }

    public FindAllPersonsResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public FindAllPersonsResponse(List<PersonSoapEntity> response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }
    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("response", response)
                .append("error", error)
                .toString();
    }
}
