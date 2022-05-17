package com.group6.java16.cybersoft.user.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.group6.java16.cybersoft.user.repository.ELUserRepository;
import com.group6.java16.cybersoft.user.validation.annotation.NotFoundUsername;

import org.springframework.beans.factory.annotation.Autowired;

public class NotFoundUsernameValidator  implements ConstraintValidator<NotFoundUsername, String> {
	private String message;
	
	@Autowired
	private ELUserRepository repository;
	
	@Override
	public void initialize(NotFoundUsername notFoundUsername) {
		message = notFoundUsername.message();
	}
	
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {

		if(repository.existsByUsername(username)) {
			return true;
		}
		
		context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
		return false;
	}

}
