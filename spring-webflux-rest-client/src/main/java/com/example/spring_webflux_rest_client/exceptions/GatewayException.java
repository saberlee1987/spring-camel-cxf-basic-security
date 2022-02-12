package com.example.spring_webflux_rest_client.exceptions;


import com.example.spring_webflux_rest_client.dto.ErrorResponse;

public class GatewayException extends RuntimeException{
	private final int statusCode;
	private final ErrorResponse errorResponse;
	
	public GatewayException(int statusCode, ErrorResponse errorResponse) {
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, int statusCode, ErrorResponse errorResponse) {
		super(message);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, Throwable cause, int statusCode, ErrorResponse errorResponse) {
		super(message, cause);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(Throwable cause, int statusCode, ErrorResponse errorResponse) {
		super(cause);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public GatewayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode, ErrorResponse errorResponse) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
		this.errorResponse = errorResponse;
	}
	
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
