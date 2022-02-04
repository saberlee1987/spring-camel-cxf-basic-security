package com.saber.spring_camel_cxf_client.dto.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.5.0
 * 2022-01-22T13:01:50.879+03:30
 * Generated source version: 3.5.0
 *
 */
@WebService(targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", name = "PersonSoapService")
@XmlSeeAlso({ObjectFactory.class})
public interface PersonSoapService {

    @WebMethod(operationName = "AddPerson", action = "AddPerson")
    @RequestWrapper(localName = "AddPerson", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.AddPerson")
    @ResponseWrapper(localName = "AddPersonResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.AddPersonResponse")
    @WebResult(name = "PersonSoapResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
    public PersonSoapResponse addPerson(

        @WebParam(name = "AuthHeader", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        AuthHeader authHeader,
        @WebParam(name = "PersonSoapDto", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        PersonSoapDto personSoapDto
    );

    @WebMethod(operationName = "UpdatePersonByNationalCode", action = "UpdatePersonByNationalCode")
    @RequestWrapper(localName = "UpdatePersonByNationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.UpdatePersonByNationalCode")
    @ResponseWrapper(localName = "UpdatePersonByNationalCodeResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.UpdatePersonByNationalCodeResponse")
    @WebResult(name = "PersonSoapResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
    public PersonSoapResponse updatePersonByNationalCode(

        @WebParam(name = "AuthHeader", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        AuthHeader authHeader,
        @WebParam(name = "nationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        String nationalCode,
        @WebParam(name = "personDto", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        PersonSoapDto personDto
    );

    @WebMethod(operationName = "FindByNationalCode", action = "FindByNationalCode")
    @RequestWrapper(localName = "FindByNationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.FindByNationalCode")
    @ResponseWrapper(localName = "FindByNationalCodeResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.FindByNationalCodeResponse")
    @WebResult(name = "PersonSoapResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
    public PersonSoapResponse findByNationalCode(

        @WebParam(name = "AuthHeader", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        AuthHeader authHeader,
        @WebParam(name = "nationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        String nationalCode
    );

    @WebMethod(operationName = "DeletePersonByNationalCode", action = "DeletePersonByNationalCode")
    @RequestWrapper(localName = "DeletePersonByNationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.DeletePersonByNationalCode")
    @ResponseWrapper(localName = "DeletePersonByNationalCodeResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.DeletePersonByNationalCodeResponse")
    @WebResult(name = "DeletePersonResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
    public DeletePersonResponse deletePersonByNationalCode(

        @WebParam(name = "AuthHeader", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        AuthHeader authHeader,
        @WebParam(name = "nationalCode", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        String nationalCode
    );

    @WebMethod(operationName = "FindAll", action = "FindAll")
    @RequestWrapper(localName = "FindAll", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.FindAll")
    @ResponseWrapper(localName = "FindAllResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/", className = "com.saber.spring_camel_cxf_client.dto.soap.FindAllResponse")
    @WebResult(name = "FindAllPersonsResponse", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
    public FindAllPersonsResponse findAll(

        @WebParam(name = "AuthHeader", targetNamespace = "http://soap.spring_camel_service_provider.saber.com/")
        AuthHeader authHeader
    );
}