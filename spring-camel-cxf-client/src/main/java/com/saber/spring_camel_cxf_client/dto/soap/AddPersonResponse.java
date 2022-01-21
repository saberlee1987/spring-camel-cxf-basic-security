
package com.saber.spring_camel_cxf_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddPersonResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddPersonResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PersonSoapResponse" type="{http://soap.spring_camel_service_provider.saber.com/}PersonSoapResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddPersonResponse", propOrder = {
    "personSoapResponse"
})
@EqualsAndHashCode
public class AddPersonResponse {

    @XmlElement(name = "PersonSoapResponse", namespace = "http://soap.spring_camel_service_provider.saber.com/")
    protected PersonSoapResponse personSoapResponse;

    /**
     * Gets the value of the personSoapResponse property.
     * 
     * @return
     *     possible object is
     *     {@link PersonSoapResponse }
     *     
     */
    public PersonSoapResponse getPersonSoapResponse() {
        return personSoapResponse;
    }

    /**
     * Sets the value of the personSoapResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonSoapResponse }
     *     
     */
    public void setPersonSoapResponse(PersonSoapResponse value) {
        this.personSoapResponse = value;
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
