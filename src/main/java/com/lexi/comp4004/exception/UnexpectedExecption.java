package com.lexi.comp4004.exception;

public class UnexpectedExecption extends Exception {

	private static final long serialVersionUID = -213284073055013196L;

	public UnexpectedExecption() {
        super("Unexpected response from server");
    }
	
}
