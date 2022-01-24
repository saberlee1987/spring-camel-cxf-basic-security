package com.saber.spring_camel_service_provider.services.impl;

import com.saber.spring_camel_service_provider.dto.DeletePersonDto;
import com.saber.spring_camel_service_provider.dto.PersonDto;
import com.saber.spring_camel_service_provider.dto.PersonResponse;
import com.saber.spring_camel_service_provider.entity.PersonEntity;
import com.saber.spring_camel_service_provider.exceptions.ResourceDuplicationException;
import com.saber.spring_camel_service_provider.exceptions.ResourceNotFoundException;
import com.saber.spring_camel_service_provider.repositories.PersonRepository;
import com.saber.spring_camel_service_provider.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public PersonEntity addPerson(PersonDto dto) {
        if (this.personRepository.findByNationalCode(dto.getNationalCode()).isPresent()) {
            throw new ResourceDuplicationException(String.format("Person with nationalCode %s exist"
                    , dto.getNationalCode()));
        }
        removeWhiteSpace(dto);
        PersonEntity entity = creatEntity(dto);
        return this.personRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse findAll() {
        List<PersonEntity> persons = this.personRepository.findAll();
        PersonResponse personResponse = new PersonResponse();
        personResponse.setPersons(persons);
        return personResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonEntity findByNationalCode(String nationalCode) {
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        return optionalPersonEntity.get();
    }

    @Override
    public PersonEntity updatePersonByNationalCode(String nationalCode, PersonDto dto) {
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        removeWhiteSpace(dto);
        PersonEntity personEntity = optionalPersonEntity.get();
        personEntity.setFirstName(dto.getFirstName());
        personEntity.setLastName(dto.getLastName());
        personEntity.setAge(dto.getAge());
        personEntity.setEmail(dto.getEmail());
        return this.personRepository.save(personEntity);
    }

    @Override
    public DeletePersonDto deletePersonByNationalCode(String nationalCode) {
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        PersonEntity personEntity = optionalPersonEntity.get();
        this.personRepository.delete(personEntity);
       return new DeletePersonDto(0,"successfully");
    }

    private PersonEntity creatEntity(PersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setNationalCode(dto.getNationalCode().replaceAll("\\s+",""));
        entity.setAge(dto.getAge());
        return entity;
    }

    private void removeWhiteSpace(PersonDto dto){
        dto.setFirstName(StringUtils.deleteWhitespace(dto.getFirstName()));
        dto.setLastName(StringUtils.deleteWhitespace(dto.getLastName()));
        dto.setEmail(StringUtils.deleteWhitespace(dto.getEmail()));
    }
}
