
package com.saber.spring_camel_cxf_client.dto.soap;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAll", namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
public class FindAll {
	
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
