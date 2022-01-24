package com.saber.spring_camel_service_provider.repositories;

import com.saber.spring_camel_service_provider.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Integer> {
    Optional<PersonEntity> findByNationalCode(String nationalCode);
}
