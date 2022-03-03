
package com.saber.spring_webflux_camel_soap_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdatePersonByNationalCodeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdatePersonByNationalCodeResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UpdatePersonResponse" type="{http://com.saber.spring_camel_cxf_soap_provider.soap.services/}UpdatePersonResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdatePersonByNationalCodeResponse", namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", propOrder = {
    "updatePersonResponse"
})
public class UpdatePersonByNationalCodeResponse {

    @XmlElement(name = "UpdatePersonResponse")
    protected UpdatePersonResponse updatePersonResponse;

    /**
     * Gets the value of the updatePersonResponse property.
     * 
     * @return
     *     possible object is
     *     {@link UpdatePersonResponse }
     *     
     */
    public UpdatePersonResponse getUpdatePersonResponse() {
        return updatePersonResponse;
    }

    /**
     * Sets the value of the updatePersonResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdatePersonResponse }
     *     
     */
    public void setUpdatePersonResponse(UpdatePersonResponse value) {
        this.updatePersonResponse = value;
    }
    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, UpdatePersonByNationalCodeResponse.class);
    }
}
