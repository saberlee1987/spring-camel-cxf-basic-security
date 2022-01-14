package com.saber.spring_camel_service_provider.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;

@Slf4j
//@Component
public class CustomUsernameTokenValidator extends UsernameTokenValidator {
    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        String username = credential.getUsernametoken().getName();
        String password = credential.getUsernametoken().getPassword();
        if (username.equals("saber66") && password.equals("AdminSaber66")){
         log.info("{} is successfully validated",username);
        }else{
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
        }
        return credential;
    }
}
