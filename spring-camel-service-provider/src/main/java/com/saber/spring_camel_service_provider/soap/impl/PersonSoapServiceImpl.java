package com.saber.spring_camel_service_provider.soap.impl;


import com.saber.spring_camel_service_provider.entity.PersonEntity;
import com.saber.spring_camel_service_provider.repositories.PersonRepository;
import com.saber.spring_camel_service_provider.soap.PersonSoapService;
import com.saber.spring_camel_service_provider.soap.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonSoapServiceImpl implements PersonSoapService {

    private final PersonRepository personRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public PersonSoapResponse addPerson(PersonSoapDto dto) {

        Set<ConstraintViolation<PersonSoapDto>> errorValidation = validatorAdapter.validate(dto);
        if (errorValidation.size() > 0) {
            return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                   null, getValidation(errorValidation)));
        } else {
            PersonEntity entity = createEntity(dto);
            if (this.personRepository.findByNationalCode(dto.getNationalCode()).isPresent()) {
                return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                        String.format("Person with nationalCode %s already exist",dto.getNationalCode()), null));
            }
            return new PersonSoapResponse(createSoapEntity(this.personRepository.save(entity)));
        }
    }

    @Override
    public PersonSoapResponse findByNationalCode(String nationalCode) {
            if (nationalCode!=null)
                  nationalCode = nationalCode.replaceAll("\\s+","");

        if (nationalCode == null || nationalCode.trim().length() < 10 || !nationalCode.trim().matches("\\d+")) {
            return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()
                   ,null , getValidation("nationalCode", "nationalCode invalid")));
        }
        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()){
            return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                    String.format("Person with nationalCode %s does not exist",nationalCode),null));
        }
        return new PersonSoapResponse(createSoapEntity(optionalPersonEntity.get()));
    }

    @Override
    public FindAllPersonsResponse findAll() {
        return new FindAllPersonsResponse(createPersons(this.personRepository.findAll()));
    }

    @Override
    public PersonSoapResponse updatePersonByNationalCode(String nationalCode, PersonSoapDto dto) {

        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Set<ConstraintViolation<PersonSoapDto>> errorValidation = validatorAdapter.validate(dto);
        if (errorValidation.size() > 0) {
            return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                   null, getValidation(errorValidation)));
        }
        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()){
            return new PersonSoapResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                    String.format("Person with nationalCode %s does not exist",nationalCode),null));
        }
        PersonEntity entity = createEntity(dto);
        return new PersonSoapResponse(createSoapEntity(this.personRepository.save(entity)));
    }

    @Override
    public DeletePersonResponse deletePersonByNationalCode(String nationalCode) {

        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        if (nationalCode == null || nationalCode.trim().length() < 10 || !nationalCode.trim().matches("\\d+")) {
            return new DeletePersonResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()
                   ,null , getValidation("nationalCode", "nationalCode invalid")));
        }
        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()){
            return new DeletePersonResponse(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                    String.format("Person with nationalCode %s does not exist",nationalCode),null));
        }
        PersonEntity entity = optionalPersonEntity.get();
        this.personRepository.delete(entity);
        DeleteSoapPersonDto deleteSoapPersonDto = new DeleteSoapPersonDto();
        deleteSoapPersonDto.setCode(0);
        deleteSoapPersonDto.setText("The operation was carried out successfully");
        return new DeletePersonResponse(deleteSoapPersonDto);
    }

    private List<ValidationSoapDto> getValidation(Set<ConstraintViolation<PersonSoapDto>> errorValidation) {
        List<ValidationSoapDto> validations = new ArrayList<>();
        for (ConstraintViolation<PersonSoapDto> v : errorValidation) {
            ValidationSoapDto validationSoapDto = new ValidationSoapDto();
            validationSoapDto.setFieldName(v.getPropertyPath().toString());
            validationSoapDto.setDetailMessage(v.getMessage());
            validations.add(validationSoapDto);
        }
        return validations;
    }

    private List<ValidationSoapDto> getValidation(String fieldName, String message) {
        List<ValidationSoapDto> validations = new ArrayList<>();
        ValidationSoapDto validationSoapDto = new ValidationSoapDto();
        validationSoapDto.setFieldName(fieldName);
        validationSoapDto.setDetailMessage(message);
        validations.add(validationSoapDto);
        return validations;
    }

    private ErrorSoapResponse getErrorResponse(Integer code, String message, String originalMessage,List<ValidationSoapDto> validationSoapDtoList) {
        ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
        errorSoapResponse.setCode(code);
        errorSoapResponse.setMessage(message);
        errorSoapResponse.setOriginalMessage(originalMessage);
        errorSoapResponse.setValidations(validationSoapDtoList);
        return errorSoapResponse;
    }

    private PersonEntity createEntity(PersonSoapDto personSoapDto) {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(personSoapDto.getFirstName());
        entity.setLastName(personSoapDto.getLastName());
        entity.setNationalCode(personSoapDto.getNationalCode().replaceAll("\\s+",""));
        entity.setEmail(personSoapDto.getEmail());
        entity.setAge(personSoapDto.getAge());
        entity.setMobile(personSoapDto.getMobile());
        return entity;
    }

    private PersonSoapEntity createSoapEntity(PersonEntity entity) {
        PersonSoapEntity personSoapEntity = new PersonSoapEntity();
        personSoapEntity.setId(entity.getId());
        personSoapEntity.setFirstName(entity.getFirstName());
        personSoapEntity.setLastName(entity.getLastName());
        personSoapEntity.setNationalCode(entity.getNationalCode().replaceAll("\\s+",""));
        personSoapEntity.setEmail(entity.getEmail());
        personSoapEntity.setAge(entity.getAge());
        personSoapEntity.setMobile(entity.getMobile());
        return personSoapEntity;
    }


    private List<PersonSoapEntity> createPersons(List<PersonEntity> entities) {
        List<PersonSoapEntity> persons = new ArrayList<>();
        for (PersonEntity entity : entities) {
            persons.add(createSoapEntity(entity));
        }
        return persons;
    }
}
