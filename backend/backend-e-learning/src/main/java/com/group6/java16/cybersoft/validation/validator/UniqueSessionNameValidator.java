package com.group6.java16.cybersoft.validation.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.course.model.ELSession;
import com.group6.java16.cybersoft.course.repository.ELSessionRepository;
import com.group6.java16.cybersoft.validation.annotation.UniqueSessionName;

public class UniqueSessionNameValidator implements ConstraintValidator<UniqueSessionName, String> {
private String message;
	
	@Autowired
	private ELSessionRepository repository;
	
	@Override
	public void initialize(UniqueSessionName uniqueSessionName) {
		message = uniqueSessionName.message();
	}
	
	
	@Override
	public boolean isValid(String sessionName, ConstraintValidatorContext context) {
		Optional<ELSession> sessionOpt = repository.findBySessionName(sessionName);
		
		if(sessionOpt.isEmpty()) {
			return true;
		}
		
		context.buildConstraintViolationWithTemplate(message)
		.addConstraintViolation()
		.disableDefaultConstraintViolation();
		return false;
	}
}
