
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
 * <p>Java class for FindAllResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAllResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FindAllPersonsResponse" type="{http://com.saber.spring_camel_cxf_soap_provider.soap.services/}FindAllPersonsResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAllResponse", namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", propOrder = {
    "findAllPersonsResponse"
})
@EqualsAndHashCode
public class FindAllResponse {

    @XmlElement(name = "FindAllPersonsResponse")
    protected FindAllPersonsResponse findAllPersonsResponse;

    /**
     * Gets the value of the findAllPersonsResponse property.
     * 
     * @return
     *     possible object is
     *     {@link FindAllPersonsResponse }
     *     
     */
    public FindAllPersonsResponse getFindAllPersonsResponse() {
        return findAllPersonsResponse;
    }

    /**
     * Sets the value of the findAllPersonsResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindAllPersonsResponse }
     *     
     */
    public void setFindAllPersonsResponse(FindAllPersonsResponse value) {
        this.findAllPersonsResponse = value;
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
                .toJson(this, FindAllResponse.class);
    }
}
