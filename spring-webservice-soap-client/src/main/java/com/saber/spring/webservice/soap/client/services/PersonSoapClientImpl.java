package com.saber.spring.webservice.soap.client.services;


import com.saber.spring.webservice.soap.client.dto.ErrorResponse;
import com.saber.spring.webservice.soap.client.dto.FindAllResponseDto;
import com.saber.spring.webservice.soap.client.dto.wsdl.*;
import com.saber.spring.webservice.soap.client.exceptions.GatewayException;
import com.saber.spring.webservice.soap.client.exceptions.PersonSoapException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import javax.xml.bind.JAXBElement;

@Service
@Slf4j
public class PersonSoapClientImpl implements PersonSoapClient {
    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Override
    public FindAllResponseDto findAllPersonsResponse(String correlation) {

        FindAll findAll = new FindAll();

        JAXBElement<FindAllResponse> response = (JAXBElement<FindAllResponse>) webServiceTemplate
                .marshalSendAndReceive(findAll);
        log.info("Response for correlation {} , findAll Person ===> {} ", correlation, response.getValue());
        FindAllResponse responseValue = response.getValue();


        if (responseValue.getFindAllPersonsResponse() != null) {
            FindAllPersonsResponse findAllPersonsResponse = responseValue.getFindAllPersonsResponse();
            if (findAllPersonsResponse.getError() != null) {
                throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, findAllPersonsResponse.getError());
            }
            FindAllResponseDto findAllResponseDto = new FindAllResponseDto();
            findAllResponseDto.setResponse(findAllPersonsResponse.getResponse());
            return findAllResponseDto;
        } else {
            throw new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    correlation,
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "response is empty")
                    ));
        }
    }

    @Override
    public PersonSoapDto findByNationalCode(String nationalCode, String correlation) {

        FindByNationalCode findByNationalCode = new FindByNationalCode();
        findByNationalCode.setNationalCode(nationalCode);
        JAXBElement<FindByNationalCodeResponse> response = (JAXBElement<FindByNationalCodeResponse>) webServiceTemplate
                .marshalSendAndReceive(findByNationalCode);
        log.info("Response for correlation {} , findByNationalCode Person with nationalCode {} ===> {} ", correlation, nationalCode, response.getValue());
        FindByNationalCodeResponse responseValue = response.getValue();
        if (responseValue.getPersonSoapResponse() != null) {
            PersonSoapResponse personSoapResponse = responseValue.getPersonSoapResponse();
            if (personSoapResponse.getError() != null) {
                throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, personSoapResponse.getError());
            }
            return responseValue.getPersonSoapResponse().getResponse();
        } else {
            throw new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    correlation,
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "response is empty")
                    ));
        }
    }

    @Override
    public AddPersonSoapResponseDto addPerson(PersonSoapDto personSoapDto, String correlation) {

        AddPerson addPerson = new AddPerson();
        addPerson.setPersonSoapDto(personSoapDto);

        log.info("Request for correlation {} , addPerson with body ==> {}", correlation, addPerson);
        JAXBElement<AddPersonResponse> response = (JAXBElement<AddPersonResponse>) webServiceTemplate
                .marshalSendAndReceive(addPerson);
        log.info("Response for correlation {} , add Person ===> {} ", correlation, response.getValue());
        AddPersonResponse responseValue = response.getValue();
        if (responseValue.getAddPersonResponseDto() != null) {
            AddPersonResponseDto addPersonResponseDto = responseValue.getAddPersonResponseDto();
            if (addPersonResponseDto.getError() != null) {
                throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, addPersonResponseDto.getError());
            }
            return responseValue.getAddPersonResponseDto().getResponse();
        } else {
            throw new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    correlation,
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "response is empty")
                    ));
        }
    }

    @Override
    public UpdatePersonSoapResponseDto updatePerson(PersonSoapDto personSoapDto, String nationalCode, String correlation) {
        UpdatePersonByNationalCode updatePersonByNationalCode = new UpdatePersonByNationalCode();
        updatePersonByNationalCode.setNationalCode(nationalCode);
        updatePersonByNationalCode.setPersonDto(personSoapDto);
        log.info("Request for correlation {} , updatePersonByNationalCode with body ==> {} and nationalCode ===> {}"
                , correlation, updatePersonByNationalCode, nationalCode);
        JAXBElement<UpdatePersonByNationalCodeResponse> response = (JAXBElement<UpdatePersonByNationalCodeResponse>) webServiceTemplate
                .marshalSendAndReceive(updatePersonByNationalCode);
        log.info("Response for correlation {} , updatePersonByNationalCode  ===> {} ", correlation, response.getValue());
        UpdatePersonByNationalCodeResponse responseValue = response.getValue();

        if (responseValue.getUpdatePersonResponse() != null) {
            UpdatePersonResponse updatePersonResponse = responseValue.getUpdatePersonResponse();
            if (updatePersonResponse.getError() != null) {
                throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, updatePersonResponse.getError());
            }
            return responseValue.getUpdatePersonResponse().getResponse();
        } else {
            throw new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    correlation,
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "response is empty")
                    ));
        }
    }

    @Override
    public DeleteSoapPersonDto deletePersonByNationalCode(String nationalCode, String correlation) {
        DeletePersonByNationalCode deletePersonByNationalCode = new DeletePersonByNationalCode();
        deletePersonByNationalCode.setNationalCode(nationalCode);
        JAXBElement<DeletePersonByNationalCodeResponse> response = (JAXBElement<DeletePersonByNationalCodeResponse>) webServiceTemplate
                .marshalSendAndReceive(deletePersonByNationalCode);
        log.info("Response for correlation {} , deletePersonByNationalCode Person with nationalCode {} ===> {} "
                , correlation, nationalCode, response.getValue());
        DeletePersonByNationalCodeResponse responseValue = response.getValue();

        if (responseValue.getDeletePersonResponse() != null) {
            DeletePersonResponse deletePersonResponse = responseValue.getDeletePersonResponse();
            if (deletePersonResponse.getError() != null) {
                throw new PersonSoapException(HttpStatus.NOT_ACCEPTABLE.value(), correlation, deletePersonResponse.getError());
            }
            return responseValue.getDeletePersonResponse().getResponse();
        } else {
            throw new GatewayException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    correlation,
                    new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            String.format("{\"code\":%d,\"text\":\"%s\"}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "response is empty")
                    ));
        }
    }

}
