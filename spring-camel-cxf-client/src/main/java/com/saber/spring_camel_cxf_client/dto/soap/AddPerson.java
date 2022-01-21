
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
 * <p>Java class for AddPerson complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddPerson"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="personDto" type="{http://soap.spring_camel_service_provider.saber.com/}PersonSoapDto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddPerson", propOrder = {
        "personDto"
})
@EqualsAndHashCode
public class AddPerson {

    @XmlElement(namespace = "http://soap.spring_camel_service_provider.saber.com/", required = true)
    protected PersonSoapDto personDto;

    /**
     * Gets the value of the personDto property.
     *
     * @return possible object is
     * {@link PersonSoapDto }
     */
    public PersonSoapDto getPersonDto() {
        return personDto;
    }

    /**
     * Sets the value of the personDto property.
     *
     * @param value allowed object is
     *              {@link PersonSoapDto }
     */
    public void setPersonDto(PersonSoapDto value) {
        this.personDto = value;
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
                .toJson(this, AddPerson.class);

    }

}
