package com.saber.spring_webflux_camel_soap_client.dto.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.5.0
 * 2022-03-03T17:32:29.718+03:30
 * Generated source version: 3.5.0
 *
 */
@WebService(targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "PersonSoapService")
@XmlSeeAlso({ObjectFactory.class})
public interface PersonSoapService {

    @WebMethod(operationName = "FindAll", action = "FindAll")
    @RequestWrapper(localName = "FindAll", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.FindAll")
    @ResponseWrapper(localName = "FindAllResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.FindAllResponse")
    @WebResult(name = "FindAllPersonsResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
    public FindAllPersonsResponse findAll()
;

    @WebMethod(operationName = "DeletePersonByNationalCode", action = "DeletePersonByNationalCode")
    @RequestWrapper(localName = "DeletePersonByNationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.DeletePersonByNationalCode")
    @ResponseWrapper(localName = "DeletePersonByNationalCodeResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.DeletePersonByNationalCodeResponse")
    @WebResult(name = "DeletePersonResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
    public DeletePersonResponse deletePersonByNationalCode(

        @WebParam(name = "nationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
        String nationalCode
    );

    @WebMethod(operationName = "FindByNationalCode", action = "FindByNationalCode")
    @RequestWrapper(localName = "FindByNationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.FindByNationalCode")
    @ResponseWrapper(localName = "FindByNationalCodeResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.FindByNationalCodeResponse")
    @WebResult(name = "PersonSoapResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
    public PersonSoapResponse findByNationalCode(

        @WebParam(name = "nationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
        String nationalCode
    );

    @WebMethod(operationName = "AddPerson", action = "AddPerson")
    @RequestWrapper(localName = "AddPerson", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.AddPerson")
    @ResponseWrapper(localName = "AddPersonResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.AddPersonResponse")
    @WebResult(name = "AddPersonResponseDto", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
    public AddPersonResponseDto addPerson(

        @WebParam(name = "PersonSoapDto", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
        PersonSoapDto personSoapDto
    );

    @WebMethod(operationName = "UpdatePersonByNationalCode", action = "UpdatePersonByNationalCode")
    @RequestWrapper(localName = "UpdatePersonByNationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.UpdatePersonByNationalCode")
    @ResponseWrapper(localName = "UpdatePersonByNationalCodeResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", className = "com.saber.spring_webflux_camel_soap_client.dto.soap.UpdatePersonByNationalCodeResponse")
    @WebResult(name = "UpdatePersonResponse", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
    public UpdatePersonResponse updatePersonByNationalCode(

        @WebParam(name = "nationalCode", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
        String nationalCode,
        @WebParam(name = "personDto", targetNamespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/")
        PersonSoapDto personDto
    );
}