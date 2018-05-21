package com.cdt.credito.exceptions;

public class ReadOnlyRecordException extends Exception {

	private static final long serialVersionUID = 2781481950400951253L;

	public ReadOnlyRecordException(String message) {
		super(message);
	}

}
