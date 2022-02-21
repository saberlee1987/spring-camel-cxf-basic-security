package com.saber.spring_camel_rest_client.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.*;

@Data
public class PersonDto {
    
    @NotBlank(message = "firstname is Required")
    @ApiModelProperty(name = "firstname",value = "firstname",example = "saber",required = true)
    private String firstname;

    @NotBlank(message = "lastname is Required")
    @ApiModelProperty(name = "lastname",value = "lastname",example = "Azizi",required = true)
    private String lastname;

    @NotBlank(message = "nationalCode is Required")
    @Size(min = 10,max = 10,message = "nationalCode must be 10 digit")
    @Pattern(regexp = "\\d+",message = "Please Enter correct nationalCode")
    @ApiModelProperty(name = "nationalCode",value = "nationalCode",example = "0079028748",required = true)
    private String nationalCode;

    @NotNull(message = "age is Required")
    @Positive(message = "age must be > 0")
    @ApiModelProperty(name = "age",value = "age",example = "35",required = true)
    private Integer age;

    @NotBlank(message = "email is Required")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ,message = "please enter valid email")
    @ApiModelProperty(name = "email",value = "email",example = "saberazizi66@yahoo.com",required = true)
    private String email;

    @NotBlank(message = "mobile is Required")
    @Size(min = 1, max = 11, message = "mobile must be > 1 and < 11 digits")
    @Pattern(regexp = "09[0-9]{9}", message = "mobile is not valid")
    @ApiModelProperty(name = "mobile",value = "mobile",example = "09365627895",required = true)
    private String mobile;

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, PersonDto.class);
    }
}
