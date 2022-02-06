
package com.saber.spring_camel_cxf_client.dto.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.saber.spring_camel_cxf_client.dto.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddPerson_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "AddPerson");
    private final static QName _AddPersonResponse_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "AddPersonResponse");
    private final static QName _DeletePersonByNationalCode_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "DeletePersonByNationalCode");
    private final static QName _DeletePersonByNationalCodeResponse_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "DeletePersonByNationalCodeResponse");
    private final static QName _FindAll_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "FindAll");
    private final static QName _FindAllResponse_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "FindAllResponse");
    private final static QName _FindByNationalCode_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "FindByNationalCode");
    private final static QName _FindByNationalCodeResponse_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "FindByNationalCodeResponse");
    private final static QName _UpdatePersonByNationalCode_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "UpdatePersonByNationalCode");
    private final static QName _UpdatePersonByNationalCodeResponse_QNAME = new QName("http://services.soap.spring_camel_cxf_soap_provider.saber.com/", "UpdatePersonByNationalCodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.saber.spring_camel_cxf_client.dto.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddPerson }
     * 
     */
    public AddPerson createAddPerson() {
        return new AddPerson();
    }

    /**
     * Create an instance of {@link AddPersonResponse }
     * 
     */
    public AddPersonResponse createAddPersonResponse() {
        return new AddPersonResponse();
    }

    /**
     * Create an instance of {@link DeletePersonByNationalCode }
     * 
     */
    public DeletePersonByNationalCode createDeletePersonByNationalCode() {
        return new DeletePersonByNationalCode();
    }

    /**
     * Create an instance of {@link DeletePersonByNationalCodeResponse }
     * 
     */
    public DeletePersonByNationalCodeResponse createDeletePersonByNationalCodeResponse() {
        return new DeletePersonByNationalCodeResponse();
    }

    /**
     * Create an instance of {@link FindAll }
     * 
     */
    public FindAll createFindAll() {
        return new FindAll();
    }

    /**
     * Create an instance of {@link FindAllResponse }
     * 
     */
    public FindAllResponse createFindAllResponse() {
        return new FindAllResponse();
    }

    /**
     * Create an instance of {@link FindByNationalCode }
     * 
     */
    public FindByNationalCode createFindByNationalCode() {
        return new FindByNationalCode();
    }

    /**
     * Create an instance of {@link FindByNationalCodeResponse }
     * 
     */
    public FindByNationalCodeResponse createFindByNationalCodeResponse() {
        return new FindByNationalCodeResponse();
    }

    /**
     * Create an instance of {@link UpdatePersonByNationalCode }
     * 
     */
    public UpdatePersonByNationalCode createUpdatePersonByNationalCode() {
        return new UpdatePersonByNationalCode();
    }

    /**
     * Create an instance of {@link UpdatePersonByNationalCodeResponse }
     * 
     */
    public UpdatePersonByNationalCodeResponse createUpdatePersonByNationalCodeResponse() {
        return new UpdatePersonByNationalCodeResponse();
    }

    /**
     * Create an instance of {@link FindAllPersonsResponse }
     * 
     */
    public FindAllPersonsResponse createFindAllPersonsResponse() {
        return new FindAllPersonsResponse();
    }

    /**
     * Create an instance of {@link PersonSoapDto }
     * 
     */
    public PersonSoapDto createPersonSoapDto() {
        return new PersonSoapDto();
    }

    /**
     * Create an instance of {@link ErrorSoapResponse }
     * 
     */
    public ErrorSoapResponse createErrorSoapResponse() {
        return new ErrorSoapResponse();
    }

    /**
     * Create an instance of {@link ValidationSoapDto }
     * 
     */
    public ValidationSoapDto createValidationSoapDto() {
        return new ValidationSoapDto();
    }

    /**
     * Create an instance of {@link DeletePersonResponse }
     * 
     */
    public DeletePersonResponse createDeletePersonResponse() {
        return new DeletePersonResponse();
    }

    /**
     * Create an instance of {@link DeleteSoapPersonDto }
     * 
     */
    public DeleteSoapPersonDto createDeleteSoapPersonDto() {
        return new DeleteSoapPersonDto();
    }

    /**
     * Create an instance of {@link PersonSoapResponse }
     * 
     */
    public PersonSoapResponse createPersonSoapResponse() {
        return new PersonSoapResponse();
    }

    /**
     * Create an instance of {@link AddPersonResponseDto }
     * 
     */
    public AddPersonResponseDto createAddPersonResponseDto() {
        return new AddPersonResponseDto();
    }

    /**
     * Create an instance of {@link AddPersonSoapResponseDto }
     * 
     */
    public AddPersonSoapResponseDto createAddPersonSoapResponseDto() {
        return new AddPersonSoapResponseDto();
    }

    /**
     * Create an instance of {@link UpdatePersonResponse }
     * 
     */
    public UpdatePersonResponse createUpdatePersonResponse() {
        return new UpdatePersonResponse();
    }

    /**
     * Create an instance of {@link UpdatePersonSoapResponseDto }
     * 
     */
    public UpdatePersonSoapResponseDto createUpdatePersonSoapResponseDto() {
        return new UpdatePersonSoapResponseDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPerson }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddPerson }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "AddPerson")
    public JAXBElement<AddPerson> createAddPerson(AddPerson value) {
        return new JAXBElement<AddPerson>(_AddPerson_QNAME, AddPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPersonResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddPersonResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "AddPersonResponse")
    public JAXBElement<AddPersonResponse> createAddPersonResponse(AddPersonResponse value) {
        return new JAXBElement<AddPersonResponse>(_AddPersonResponse_QNAME, AddPersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersonByNationalCode }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeletePersonByNationalCode }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "DeletePersonByNationalCode")
    public JAXBElement<DeletePersonByNationalCode> createDeletePersonByNationalCode(DeletePersonByNationalCode value) {
        return new JAXBElement<DeletePersonByNationalCode>(_DeletePersonByNationalCode_QNAME, DeletePersonByNationalCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePersonByNationalCodeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeletePersonByNationalCodeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "DeletePersonByNationalCodeResponse")
    public JAXBElement<DeletePersonByNationalCodeResponse> createDeletePersonByNationalCodeResponse(DeletePersonByNationalCodeResponse value) {
        return new JAXBElement<DeletePersonByNationalCodeResponse>(_DeletePersonByNationalCodeResponse_QNAME, DeletePersonByNationalCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "FindAll")
    public JAXBElement<FindAll> createFindAll(FindAll value) {
        return new JAXBElement<FindAll>(_FindAll_QNAME, FindAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "FindAllResponse")
    public JAXBElement<FindAllResponse> createFindAllResponse(FindAllResponse value) {
        return new JAXBElement<FindAllResponse>(_FindAllResponse_QNAME, FindAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByNationalCode }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindByNationalCode }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "FindByNationalCode")
    public JAXBElement<FindByNationalCode> createFindByNationalCode(FindByNationalCode value) {
        return new JAXBElement<FindByNationalCode>(_FindByNationalCode_QNAME, FindByNationalCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByNationalCodeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FindByNationalCodeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "FindByNationalCodeResponse")
    public JAXBElement<FindByNationalCodeResponse> createFindByNationalCodeResponse(FindByNationalCodeResponse value) {
        return new JAXBElement<FindByNationalCodeResponse>(_FindByNationalCodeResponse_QNAME, FindByNationalCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersonByNationalCode }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePersonByNationalCode }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "UpdatePersonByNationalCode")
    public JAXBElement<UpdatePersonByNationalCode> createUpdatePersonByNationalCode(UpdatePersonByNationalCode value) {
        return new JAXBElement<UpdatePersonByNationalCode>(_UpdatePersonByNationalCode_QNAME, UpdatePersonByNationalCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePersonByNationalCodeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdatePersonByNationalCodeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.soap.spring_camel_cxf_soap_provider.saber.com/", name = "UpdatePersonByNationalCodeResponse")
    public JAXBElement<UpdatePersonByNationalCodeResponse> createUpdatePersonByNationalCodeResponse(UpdatePersonByNationalCodeResponse value) {
        return new JAXBElement<UpdatePersonByNationalCodeResponse>(_UpdatePersonByNationalCodeResponse_QNAME, UpdatePersonByNationalCodeResponse.class, null, value);
    }

}
