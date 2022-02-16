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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PersonEntity addPerson(PersonDto dto) {
        log.info("Request for add person with body ===> {}",dto);
        if (this.personRepository.personExist(dto.getNationalCode()).isPresent()) {
            log.error("Person with nationalCode {} already exist ",dto.getNationalCode());
            throw new ResourceDuplicationException(String.format("Person with nationalCode %s already exist"
                    , dto.getNationalCode()));
        }
        removeWhiteSpace(dto);
        PersonEntity entity = creatEntity(dto);
        PersonEntity response = this.personRepository.save(entity);
        log.info("Response for add person with body ===> {}",response);
        
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse findAll() {
        List<PersonEntity> persons = this.personRepository.findAll();
        PersonResponse personResponse = new PersonResponse();
        List<PersonDto> personDtoList = new ArrayList<>();
        for (PersonEntity entity : persons) {
            personDtoList.add(createPersonDto(entity));
        }
        personResponse.setPersons(personDtoList);
        return personResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByNationalCode(String nationalCode) {
        log.info("Request for findByNationalCode with nationalCode ===> {}",nationalCode);
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            log.error("Person with nationalCode {} does not exist ",nationalCode);
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        PersonDto personDto = createPersonDto(optionalPersonEntity.get());
        log.info("Response for findByNationalCode with nationalCode {} ,  body ===> {}",nationalCode,personDto);
        
        return personDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PersonDto updatePersonByNationalCode(String nationalCode, PersonDto dto) {
        log.info("Request for updatePersonByNationalCode with nationalCode ===> {} , body {}",nationalCode,dto);
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            log.error("Person with nationalCode {} does not exist ",nationalCode);
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        removeWhiteSpace(dto);
        PersonEntity personEntity = optionalPersonEntity.get();
        personEntity.setFirstname(dto.getFirstname());
        personEntity.setLastname(dto.getLastname());
        personEntity.setAge(dto.getAge());
        personEntity.setEmail(dto.getEmail());
        personEntity.setNationalCode(dto.getNationalCode());
        PersonDto personDto = createPersonDto(this.personRepository.save(personEntity));
        log.info("Response for updatePersonByNationalCode with nationalCode {} ,  body ===> {}",nationalCode,personDto);
        return personDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DeletePersonDto deletePersonByNationalCode(String nationalCode) {
        log.info("Request for deletePersonByNationalCode with nationalCode ===> {}",nationalCode);
        
        if (nationalCode!=null)
            nationalCode = nationalCode.replaceAll("\\s+","");

        Optional<Boolean> optionalPersonEntity = this.personRepository.personExist(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            log.error("Person with nationalCode {} does not exist ",nationalCode);
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        Optional<Boolean> deleteResult = this.personRepository.deleteByNationalCode(nationalCode);
        if (deleteResult.isPresent()){
            log.info("Response for delete person by nationalCode {} ===> {}",nationalCode,deleteResult.get());
        }else {
            log.info("Error for delete person by nationalCode {} ",nationalCode);
        }
        return new DeletePersonDto(0,"successfully");
    }

    private PersonEntity creatEntity(PersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setNationalCode(dto.getNationalCode().replaceAll("\\s+",""));
        entity.setAge(dto.getAge());
        entity.setMobile(dto.getMobile());
        entity.setEmail(dto.getEmail());
        return entity;
    }
    private PersonDto createPersonDto(PersonEntity entity) {
        PersonDto personDto = new PersonDto();
        personDto.setFirstname(entity.getFirstname());
        personDto.setLastname(entity.getLastname());
        personDto.setEmail(entity.getEmail());
        personDto.setNationalCode(entity.getNationalCode().replaceAll("\\s+",""));
        personDto.setAge(entity.getAge());
        personDto.setMobile(entity.getMobile());
        return personDto;
    }
    

    private void removeWhiteSpace(PersonDto dto){
        dto.setFirstname(StringUtils.deleteWhitespace(dto.getFirstname()));
        dto.setLastname(StringUtils.deleteWhitespace(dto.getLastname()));
        dto.setEmail(StringUtils.deleteWhitespace(dto.getEmail()));
    }
}
