
package com.saber.spring_webflux_camel_soap_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;

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
 *         &lt;element name="response" type="{http://com.saber.spring_camel_cxf_soap_provider.soap.services/}PersonSoapDto" minOccurs="0"/&gt;
 *         &lt;element name="error" type="{http://com.saber.spring_camel_cxf_soap_provider.soap.services/}ErrorSoapResponse" minOccurs="0"/&gt;
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
public class PersonSoapResponse {

    protected PersonSoapDto response;
    protected ErrorSoapResponse error;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link PersonSoapDto }
     *     
     */
    public PersonSoapDto getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonSoapDto }
     *     
     */
    public void setResponse(PersonSoapDto value) {
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
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, PersonSoapResponse.class);
    }
}
