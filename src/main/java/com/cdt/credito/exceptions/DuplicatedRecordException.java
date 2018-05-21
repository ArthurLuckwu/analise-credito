package com.cdt.credito.exceptions;

public class DuplicatedRecordException extends Exception {

	private static final long serialVersionUID = 5245066360590117422L;

	private final String field;
	
	/**
	 * @param message
	 */
	public DuplicatedRecordException(String message, String field) {
		super(message);
		this.field = field;
	}

	public String getField() {
		return field;
	}

}
