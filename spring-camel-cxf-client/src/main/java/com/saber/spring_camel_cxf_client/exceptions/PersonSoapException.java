package com.saber.spring_camel_cxf_client.exceptions;

import com.saber.spring_camel_cxf_client.dto.soap.ErrorSoapResponse;

public class PersonSoapException extends RuntimeException{
	private final int statusCode;
	private final ErrorSoapResponse errorResponse;

	public PersonSoapException(int statusCode, ErrorSoapResponse errorResponse) {
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, int statusCode, ErrorSoapResponse errorResponse) {
		super(message);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, Throwable cause, int statusCode, ErrorSoapResponse errorResponse) {
		super(message, cause);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(Throwable cause, int statusCode, ErrorSoapResponse errorResponse) {
		super(cause);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}

	public PersonSoapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode, ErrorSoapResponse errorResponse) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public ErrorSoapResponse getErrorResponse() {
		return errorResponse;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
