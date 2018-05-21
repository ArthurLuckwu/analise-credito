package com.cdt.credito.exceptions;

public class WeakPasswordException extends Exception {

	private static final long serialVersionUID = 6789016195897183054L;

	public WeakPasswordException(String message) {
		super(message);
	}

}
