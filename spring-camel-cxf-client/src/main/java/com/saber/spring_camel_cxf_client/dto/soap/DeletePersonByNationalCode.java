
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
 * <p>Java class for DeletePersonByNationalCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeletePersonByNationalCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AuthHeader" type="{http://soap.spring_camel_service_provider.saber.com/}AuthHeader"/&gt;
 *         &lt;element name="nationalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePersonByNationalCode", propOrder = {
    "authHeader",
    "nationalCode"
})
@EqualsAndHashCode
public class DeletePersonByNationalCode {

    @XmlElement(name = "AuthHeader", namespace = "http://soap.spring_camel_service_provider.saber.com/", required = true)
    protected AuthHeader authHeader;
    @XmlElement(namespace = "http://soap.spring_camel_service_provider.saber.com/", required = true, defaultValue = "")
    protected String nationalCode;

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

    /**
     * Gets the value of the nationalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalCode() {
        return nationalCode;
    }

    /**
     * Sets the value of the nationalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalCode(String value) {
        this.nationalCode = value;
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
                .toJson(this, DeletePersonByNationalCode.class);

    }
}
