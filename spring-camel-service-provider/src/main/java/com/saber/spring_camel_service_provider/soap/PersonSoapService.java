package com.saber.spring_camel_service_provider.soap;


import com.saber.spring_camel_service_provider.soap.dto.DeletePersonResponse;
import com.saber.spring_camel_service_provider.soap.dto.FindAllPersonsResponse;
import com.saber.spring_camel_service_provider.soap.dto.PersonSoapDto;
import com.saber.spring_camel_service_provider.soap.dto.PersonSoapResponse;
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
    PersonSoapResponse addPerson(@WebParam(name = "personDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "FindByNationalCode",action = "FindByNationalCode")
    @WebResult(name = "PersonSoapResponse")
    PersonSoapResponse findByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "") String nationalCode);


    @WebMethod(operationName = "FindAll",action = "FindAll")
    @WebResult(name = "FindAllPersonsResponse")
    FindAllPersonsResponse findAll();

    @WebMethod(operationName = "UpdatePersonByNationalCode",action = "UpdatePersonByNationalCode")
    @WebResult(name = "PersonSoapResponse")
    PersonSoapResponse updatePersonByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode,@WebParam(name = "personDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "DeletePersonByNationalCode",action = "DeletePersonByNationalCode")
    @WebResult(name = "DeletePersonResponse")
    DeletePersonResponse deletePersonByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode);
}
