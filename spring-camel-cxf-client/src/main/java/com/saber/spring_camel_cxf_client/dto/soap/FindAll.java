
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
 * <p>Java class for FindAll complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindAll"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AuthHeader" type="{http://soap.spring_camel_service_provider.saber.com/}AuthHeader"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAll", propOrder = {
    "authHeader"
})
@EqualsAndHashCode
public class FindAll {

    @XmlElement(name = "AuthHeader", namespace = "http://soap.spring_camel_service_provider.saber.com/", required = true)
    protected AuthHeader authHeader;

    /**
     * Gets the value of the authHeader property.
     * 
     * @return
     *     possible object is
     *     {@link AuthHeader }
     *     
     */
    public AuthHeader getAuthHeader() {
        return authHeader;
    }

    /**
     * Sets the value of the authHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthHeader }
     *     
     */
    public void setAuthHeader(AuthHeader value) {
        this.authHeader = value;
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
                .toJson(this, FindAll.class);
    }
}
