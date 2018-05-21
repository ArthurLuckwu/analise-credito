package com.cdt.credito.exceptions;

public class InvalidCaptchaException extends Exception {

	private static final long serialVersionUID = -3088274321581128026L;
	
	public InvalidCaptchaException() {
		super();
	}

	public InvalidCaptchaException(String message) {
		super(message);
	}

}
