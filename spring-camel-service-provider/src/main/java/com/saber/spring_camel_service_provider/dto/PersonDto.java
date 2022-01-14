package com.saber.spring_camel_service_provider.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.*;

@Data
public class PersonDto {

    @NotBlank(message = "firstName is Required")
    private String firstName;

    @NotBlank(message = "lastName is Required")
    @Schema(title = "lastName",example = "Azizi",required = true)
    private String lastName;

    @NotBlank(message = "nationalCode is Required")
    @Size(min = 10,max = 10,message = "nationalCode must be 10 digit")
    @Pattern(regexp = "\\d+",message = "Please Enter correct nationalCode")
    private String nationalCode;

    @NotNull(message = "age is Required")
    @Positive(message = "age must be > 0")
    private Integer age;

    @NotBlank(message = "email is Required")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ,message = "please enter valid email")
    private String email;

    @NotBlank(message = "mobile is Required")
    @Size(min = 1, max = 11, message = "mobile must be > 1 and < 11 digits")
    @Pattern(regexp = "09[0-9]{9}", message = "mobile is not valid")
    private String mobile;

    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("nationalCode", nationalCode)
                .append("age", age)
                .append("email", email)
                .append("mobile", mobile)
                .toString();
    }
}
