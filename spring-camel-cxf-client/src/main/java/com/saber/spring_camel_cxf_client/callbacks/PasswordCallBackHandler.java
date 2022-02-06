package com.saber.spring_camel_cxf_client.callbacks;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

public class PasswordCallBackHandler  implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback wsPasswordCallback= (WSPasswordCallback) callbacks[0];
       if (wsPasswordCallback.getIdentifier().equals("saber66")){
           wsPasswordCallback.setPassword("saber@123");
           wsPasswordCallback.setAlgorithm("Basic");

       }
    }
}
