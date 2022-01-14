package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePersonResponse",propOrder = {
        "response",
        "error",

})
@Data
public class DeletePersonResponse {
    private DeleteSoapPersonDto response;
    private ErrorSoapResponse error;

    public DeletePersonResponse() {
    }

    public DeletePersonResponse(DeleteSoapPersonDto response) {
        this.response = response;
    }

    public DeletePersonResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public DeletePersonResponse(DeleteSoapPersonDto response, ErrorSoapResponse error) {
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
