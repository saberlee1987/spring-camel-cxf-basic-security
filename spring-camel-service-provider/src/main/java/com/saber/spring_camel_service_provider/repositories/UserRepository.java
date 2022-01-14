package com.saber.spring_camel_service_provider.repositories;

import com.saber.spring_camel_service_provider.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
	Optional<Users> findByUsername(String username);
}