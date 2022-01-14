package com.saber.spring_camel_service_provider.soap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
