package com.saber.spring_camel_service_provider.dto;


import com.saber.spring_camel_service_provider.entity.PersonEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class PersonResponse {
    private List<PersonEntity> persons ;


    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("persons", persons)
                .toString();
    }
}
