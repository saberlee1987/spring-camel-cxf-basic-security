package com.saber.spring_camel_cxf_soap_provider.config;

import com.saber.spring_camel_cxf_soap_provider.dto.User;
import com.saber.spring_camel_cxf_soap_provider.repositoreis.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUsernameTokenValidator extends UsernameTokenValidator {
	
	
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	@Override
	public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
		
		UsernameToken usernametoken = credential.getUsernametoken();
		String username = usernametoken.getName();
		String password = usernametoken.getPassword();
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
			log.info("{} is successfully validated", username);
			
		} else {
			log.error("username or password is invalid {username : {} , password: {} } ", username, password);
			throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, String.format("username or password is invalid , username %s , password %s", username, password));
		}
		return credential;
	}
}
