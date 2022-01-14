package com.saber.spring_camel_service_provider.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapEntity",propOrder = {
        "id",
        "firstName",
        "lastName",
        "nationalCode",
        "age",
        "email",
        "mobile",
})
@Data
public class PersonSoapEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Integer age;
    private String email;
    private String mobile;
}
