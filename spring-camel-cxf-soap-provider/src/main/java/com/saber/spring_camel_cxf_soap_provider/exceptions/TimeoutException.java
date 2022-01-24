package com.saber.spring_camel_cxf_soap_provider.exceptions;

public class TimeoutException  extends RuntimeException{
	public TimeoutException() {
	}
	
	public TimeoutException(String message) {
		super(message);
	}
	
	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TimeoutException(Throwable cause) {
		super(cause);
	}
	
	public TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
