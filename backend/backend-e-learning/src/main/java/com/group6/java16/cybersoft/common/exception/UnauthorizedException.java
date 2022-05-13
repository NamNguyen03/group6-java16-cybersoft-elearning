package com.group6.java16.cybersoft.common.exception;

public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException() {
		super("Unauthorized");
	}
}
