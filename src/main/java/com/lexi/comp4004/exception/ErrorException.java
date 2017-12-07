package com.lexi.comp4004.exception;

public class ErrorException extends Exception {

	private static final long serialVersionUID = -213284073055013196L;

	private String error;
	
	public ErrorException(String message) {
        super(message);
    }
	
	public ErrorException(String error, String message) {
		super(message);
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
	
	public String toString() {
		return getError() + ": " + getMessage();
	}
	
}
