package com.saber.spring.webservice.soap.client.exceptions;

import com.saber.spring.webservice.soap.client.dto.wsdl.ErrorSoapResponse;

public class PersonSoapException extends RuntimeException{
	private final int statusCode;
	private final String correlation;
	private final ErrorSoapResponse errorResponse;

	public PersonSoapException(int statusCode, String correlation, ErrorSoapResponse errorResponse) {
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, int statusCode, String correlation, ErrorSoapResponse errorResponse) {
		super(message);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, Throwable cause, int statusCode, String correlation, ErrorSoapResponse errorResponse) {
		super(message, cause);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(Throwable cause, int statusCode, String correlation, ErrorSoapResponse errorResponse) {
		super(cause);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode, String correlation, ErrorSoapResponse errorResponse) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public ErrorSoapResponse getErrorResponse() {
		return errorResponse;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public String getCorrelation() {
		return correlation;
	}
}
