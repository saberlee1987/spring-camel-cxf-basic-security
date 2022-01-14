package com.saber.spring_camel_service_provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
public class DeletePersonDto {
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
