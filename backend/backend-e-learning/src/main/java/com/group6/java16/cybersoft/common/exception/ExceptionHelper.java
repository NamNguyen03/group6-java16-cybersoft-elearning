package com.group6.java16.cybersoft.common.exception;

import com.group6.java16.cybersoft.common.util.ResponseHelper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {

	@ExceptionHandler(UnauthorizedException.class)
	public Object handlerUnauthorizedException (UnauthorizedException ex) {
		ex.printStackTrace();
		return ResponseHelper.getResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, true);
	}

	@ExceptionHandler(BusinessException.class)
	public Object handlerBusinessException (BusinessException ex) {
		ex.printStackTrace();
		return ResponseHelper.getResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, true);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public Object handlerIllegalArgumentException (IllegalArgumentException ex) {
		ex.printStackTrace();
		return ResponseHelper.getResponse("Data type invalid", HttpStatus.BAD_REQUEST, true);
	}

}
