package com.saber.spring_camel_cxf_soap_provider.config;

import com.saber.spring_camel_cxf_soap_provider.soap.services.PersonSoapService;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfConfig {
	
	@Autowired
	private CustomUsernameTokenValidator customUsernameTokenValidator;
	
	@Value(value = "${cxf.endpoint}")
	private String cxfEndpointPath;
	@Value(value = "${cxf.endpointName}")
	private String cxfEndpointName;
	
	@Bean
	public CxfEndpoint cxfEndpoint(){
		CxfEndpoint cxf = new CxfEndpoint();
		cxf.setAddress(cxfEndpointPath);
		cxf.setEndpointName(cxfEndpointName);
		cxf.setServiceClass(PersonSoapService.class);
		cxf.setProperties(config());
		cxf.getInInterceptors().add(new LoggingInInterceptor());
		cxf.getInInterceptors().add(wss4JInInterceptor());
		cxf.getInFaultInterceptors().add(new LoggingInInterceptor());
		cxf.getOutInterceptors().add(new LoggingOutInterceptor());
		cxf.getOutFaultInterceptors().add(new LoggingOutInterceptor());
		return cxf;
	}
	
	private WSS4JInInterceptor wss4JInInterceptor(){
		WSS4JInInterceptor wss4JInInterceptor = new WSS4JInInterceptor();
		Map<String,Object> config = new HashMap<>();
		config.put("action","UsernameToken");
		config.put("passwordType","PasswordText");
		wss4JInInterceptor.setProperties(config);
		return wss4JInInterceptor;
	}
	
	private Map<String,Object> config(){
		Map<String,Object> config = new HashMap<>();
		config.put("ws-security.ut.validator",customUsernameTokenValidator);
		return config;
	}
}
