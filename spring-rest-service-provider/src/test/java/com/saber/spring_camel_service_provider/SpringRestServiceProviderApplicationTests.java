package com.saber.spring_camel_service_provider;

import com.saber.spring_camel_service_provider.entity.UserAuthority;
import com.saber.spring_camel_service_provider.entity.Users;
import com.saber.spring_camel_service_provider.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SpringRestServiceProviderApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//@Test
	void contextLoads() {
		
		String username = "saber66";
		String password = "saber@123";
		password = bCryptPasswordEncoder.encode(password);
		
		Users users = new Users();
		users.setUsername(username);
		users.setPassword(password);
		users.setAccountNonExpired(true);
		users.setAccountNonLocked(true);
		users.setCredentialsNonExpired(true);
		users.setEnabled(true);
		
		List<UserAuthority> authorities = new ArrayList<>();
		authorities.add(new UserAuthority("user"));
		authorities.add(new UserAuthority("admin"));
		users.setUserEntityAuthorities(authorities);
		
		userRepository.save(users);
		
		Optional<Users> optionalUsers = userRepository.findByUsername(username);
        optionalUsers.ifPresent(System.out::println);
		
	}
	
}
