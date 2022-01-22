package com.saber.spring_camel_service_provider.soap;


import com.saber.spring_camel_service_provider.soap.dto.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "PersonSoapService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface PersonSoapService {

    @WebMethod(operationName = "AddPerson",action = "AddPerson")
    @WebResult(name = "PersonSoapResponse")
    PersonSoapResponse addPerson(@WebParam(name = "AuthHeader") @XmlElement(required = true) AuthHeader authHeader, @WebParam(name = "PersonSoapDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "FindByNationalCode",action = "FindByNationalCode")
    @WebResult(name = "PersonSoapResponse")
    PersonSoapResponse findByNationalCode(@WebParam(name = "AuthHeader") @XmlElement(required = true) AuthHeader authHeader, @WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "") String nationalCode);


    @WebMethod(operationName = "FindAll",action = "FindAll")
    @WebResult(name = "FindAllPersonsResponse")
    FindAllPersonsResponse findAll(@WebParam(name = "AuthHeader") @XmlElement(required = true) AuthHeader authHeader);

    @WebMethod(operationName = "UpdatePersonByNationalCode",action = "UpdatePersonByNationalCode")
    @WebResult(name = "PersonSoapResponse")
    PersonSoapResponse updatePersonByNationalCode(@WebParam(name = "AuthHeader") @XmlElement(required = true) AuthHeader authHeader, @WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode,@WebParam(name = "personDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "DeletePersonByNationalCode",action = "DeletePersonByNationalCode")
    @WebResult(name = "DeletePersonResponse")
    DeletePersonResponse deletePersonByNationalCode(@WebParam(name = "AuthHeader") @XmlElement(required = true) AuthHeader authHeader, @WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode);
}
