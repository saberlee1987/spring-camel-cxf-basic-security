package com.saber.spring_camel_service_provider.soap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
@XmlType(name = "DeleteSoapPersonDto",propOrder = {
        "code",
        "text",

})
public class DeleteSoapPersonDto {
    private Integer code;
    private String text;

    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("code", code)
                .append("text", text)
                .toString();
    }
}
