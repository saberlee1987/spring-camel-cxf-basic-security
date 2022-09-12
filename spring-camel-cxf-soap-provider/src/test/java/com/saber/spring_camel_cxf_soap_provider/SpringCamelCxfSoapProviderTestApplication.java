package com.saber.spring_camel_cxf_soap_provider;

import com.saber.spring_camel_cxf_soap_provider.dto.User;
import com.saber.spring_camel_cxf_soap_provider.dto.UserAuthority;
import com.saber.spring_camel_cxf_soap_provider.repositoreis.UserRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
public class SpringCamelCxfSoapProviderTestApplication {

	//@Autowired
	private  BCryptPasswordEncoder passwordEncoder;
	//@Autowired
	private  UserRepository userRepository;

//	@Test
	void testFindUserByUsername(){

		User user = new User();
		user.setUsername("saber66");
		user.setPassword(passwordEncoder.encode("saber@123"));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);

		List<UserAuthority> userEntityAuthorities= new ArrayList<>();
		userEntityAuthorities.add(new UserAuthority("admin"));
		userEntityAuthorities.add(new UserAuthority("user"));
		user.setUserEntityAuthorities(userEntityAuthorities);
		User response = userRepository.save(user);

		Assertions.assertThat(response).isNotNull();
	}
}
