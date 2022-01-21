
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
 * <p>Java class for DeletePersonByNationalCodeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeletePersonByNationalCodeResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DeletePersonResponse" type="{http://soap.spring_camel_service_provider.saber.com/}DeletePersonResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePersonByNationalCodeResponse", propOrder = {
    "deletePersonResponse"
})
@EqualsAndHashCode
public class DeletePersonByNationalCodeResponse {

    @XmlElement(name = "DeletePersonResponse", namespace = "http://soap.spring_camel_service_provider.saber.com/")
    protected DeletePersonResponse deletePersonResponse;

    /**
     * Gets the value of the deletePersonResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DeletePersonResponse }
     *     
     */
    public DeletePersonResponse getDeletePersonResponse() {
        return deletePersonResponse;
    }

    /**
     * Sets the value of the deletePersonResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeletePersonResponse }
     *     
     */
    public void setDeletePersonResponse(DeletePersonResponse value) {
        this.deletePersonResponse = value;
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
                .toJson(this, DeletePersonByNationalCodeResponse.class);

    }
}
