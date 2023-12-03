package com.saber.apigateway;

public enum ServiceErrorResponseEnum {
	JSON_ERROR_EXCEPTION(1,"json خطای پردازش "),
	SERVICE_PROVIDER_EXCEPTION(2,"خطای سرویس دهنده"),
	INPUT_VALIDATION_EXCEPTION(3,"خطای اعتبار سنجی ورودی"),
	AUTHENTICATION_EXCEPTION(4,"خطای اهراز هویت"),
	TIMEOUT_EXCEPTION(5,"خطا در ارتباط با سرویس دهنده");
	int code;
	String message;
	
	ServiceErrorResponseEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getCode() {
		return code;
	}
}
