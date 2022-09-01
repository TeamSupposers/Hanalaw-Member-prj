package com.member.exception;

@SuppressWarnings("serial")
public class KeyValidationException extends RuntimeException {

	public KeyValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public KeyValidationException(String message) {
		 super(message);
	}
}
