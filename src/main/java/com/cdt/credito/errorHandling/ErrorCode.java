package com.cdt.credito.errorHandling;

public enum ErrorCode {

	// Generic Error Codes
	INTERNAL_UNEXPECTED_ERROR(0),
	ARGUMENTS_NOT_VALID(1),
	DUPLICATE_RECORD(2),
	RECORD_NOT_FOUND(3),
	NOT_ALLOWED(4),
	FILE_SYSTEM_ERROR(5),
	INVALID_ACTION(6),
	READONLY_RECORD(7),
	ILLEGAL_ARGUMENT(8),
	INVALID_EVENT(9),

	// Security Error Codes
	UNAUTHORIZED(20),
	BAD_CREDENTIALS(21),
	INVALID_CAPTCHA(22),
	PASSWORD_EXPIRED(23),
	INVALID_USER_DETAILS(24),
	WEAK_PASSWORD(25),
	ACCESS_DENIED(26),
	SESSION_EXPIRED(27),
	INVALID_TOKEN(28),
	;

	private final Integer value;

	private ErrorCode(Integer value) {
		this.value = value;
	}

	public Integer getValue() { return value; }

	public static String errorCodeKey() { return "errorCode"; }

	public static String errorMessageKey() { return "errorMsg"; }

	public static String errorFieldKey() { return "errorField"; }

}
