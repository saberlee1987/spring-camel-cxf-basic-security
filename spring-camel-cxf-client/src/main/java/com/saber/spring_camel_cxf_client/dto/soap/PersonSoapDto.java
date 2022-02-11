
package com.saber.spring_camel_cxf_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonSoapDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonSoapDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nationalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapDto", propOrder = {
    "firstname",
    "lastname",
    "nationalCode",
    "age",
    "email",
    "mobile"
})
@EqualsAndHashCode
public class PersonSoapDto {
    
    @XmlElement(defaultValue = "")
    @NotBlank(message = "firstname is Required")
    protected String firstname;
    @XmlElement(defaultValue = "")
    @NotBlank(message = "lastname is Required")
    protected String lastname;
    @XmlElement(defaultValue = "")
    @NotBlank(message = "nationalCode is Required")
    @Size(min = 10,max = 10,message = "nationalCode must be 10 digit")
    @Pattern(regexp = "\\d+",message = "Please Enter correct nationalCode")
    protected String nationalCode;
    @XmlElement(defaultValue = "0")
    @NotNull(message = "age is Required")
    @Positive(message = "age must be > 0")
    protected Integer age;
    @XmlElement(defaultValue = "")
    @NotBlank(message = "email is Required")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ,message = "please enter valid email")
    protected String email;
    @XmlElement(defaultValue = "")
    @NotBlank(message = "mobile is Required")
    @Size(min = 1, max = 11, message = "mobile must be > 1 and < 11 digits")
    @Pattern(regexp = "09[0-9]{9}", message = "mobile is not valid")
    protected String mobile;
    
    /**
     * Gets the value of the firstName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFirstname() {
        return firstname;
    }
    
    /**
     * Sets the value of the firstName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFirstname(String value) {
        this.firstname = value;
    }
    
    /**
     * Gets the value of the lastname property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLastname() {
        return lastname;
    }
    
    /**
     * Sets the value of the lastname property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastname(String value) {
        this.lastname = value;
    }
    
    /**
     * Gets the value of the nationalCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNationalCode() {
        return nationalCode;
    }
    
    /**
     * Sets the value of the nationalCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNationalCode(String value) {
        this.nationalCode = value;
    }
    
    /**
     * Gets the value of the age property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getAge() {
        return age;
    }
    
    /**
     * Sets the value of the age property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAge(Integer value) {
        this.age = value;
    }
    
    /**
     * Gets the value of the email property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the value of the email property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmail(String value) {
        this.email = value;
    }
    
    /**
     * Gets the value of the mobile property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMobile() {
        return mobile;
    }
    
    /**
     * Sets the value of the mobile property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMobile(String value) {
        this.mobile = value;
    }
    
    @Override
    public String toString() {
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setLenient()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create()
                .toJson(this, PersonSoapDto.class);
        
    }
}
