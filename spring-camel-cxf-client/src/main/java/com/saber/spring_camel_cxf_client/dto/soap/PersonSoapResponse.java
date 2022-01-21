
package com.saber.spring_camel_cxf_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonSoapResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonSoapResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="response" type="{http://soap.spring_camel_service_provider.saber.com/}PersonSoapEntity" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="error" type="{http://soap.spring_camel_service_provider.saber.com/}ErrorSoapResponse" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapResponse", propOrder = {
    "response",
    "error"
})
@EqualsAndHashCode
public class PersonSoapResponse {

    protected PersonSoapEntity response;
    protected ErrorSoapResponse error;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link PersonSoapEntity }
     *     
     */
    public PersonSoapEntity getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonSoapEntity }
     *     
     */
    public void setResponse(PersonSoapEntity value) {
        this.response = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorSoapResponse }
     *     
     */
    public ErrorSoapResponse getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorSoapResponse }
     *     
     */
    public void setError(ErrorSoapResponse value) {
        this.error = value;
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
                .toJson(this, PersonSoapResponse.class);

    }
}
