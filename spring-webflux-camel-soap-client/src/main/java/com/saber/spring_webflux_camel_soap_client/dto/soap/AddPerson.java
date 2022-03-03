
package com.saber.spring_webflux_camel_soap_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import com.saber.spring_webflux_camel_soap_client.dto.ErrorResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddPerson"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PersonSoapDto" type="{http://com.saber.spring_camel_cxf_soap_provider.soap.services/}PersonSoapDto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddPerson", namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", propOrder = {
    "personSoapDto"
})
public class AddPerson {

    @XmlElement(name = "PersonSoapDto", required = true)
    protected PersonSoapDto personSoapDto;

    /**
     * Gets the value of the personSoapDto property.
     * 
     * @return
     *     possible object is
     *     {@link PersonSoapDto }
     *     
     */
    public PersonSoapDto getPersonSoapDto() {
        return personSoapDto;
    }

    /**
     * Sets the value of the personSoapDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonSoapDto }
     *     
     */
    public void setPersonSoapDto(PersonSoapDto value) {
        this.personSoapDto = value;
    }

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, AddPerson.class);
    }

}
