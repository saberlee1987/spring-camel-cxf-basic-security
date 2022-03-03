package com.saber.spring_camel_cxf_client.exceptions;

import com.saber.spring_camel_cxf_client.dto.ErrorResponse;

public class GatewayException extends RuntimeException{
	private final int statusCode;
	private final String correlation;
	private final ErrorResponse errorResponse;
	
	public GatewayException(int statusCode, String correlation, ErrorResponse errorResponse) {
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, int statusCode, String correlation, ErrorResponse errorResponse) {
		super(message);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, Throwable cause, int statusCode, String correlation, ErrorResponse errorResponse) {
		super(message, cause);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(Throwable cause, int statusCode, String correlation, ErrorResponse errorResponse) {
		super(cause);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode, String correlation, ErrorResponse errorResponse) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
		this.correlation = correlation;
		this.errorResponse = errorResponse;
	}
	
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public String getCorrelation() {
		return correlation;
	}
}
