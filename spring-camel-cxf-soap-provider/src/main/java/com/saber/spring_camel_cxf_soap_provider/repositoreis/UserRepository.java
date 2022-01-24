package com.saber.spring_camel_cxf_soap_provider.repositoreis;

import com.saber.spring_camel_cxf_soap_provider.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);
}
