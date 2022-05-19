package com.group6.java16.cybersoft.role.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.role.repository.ELProgramRepository;
import com.group6.java16.cybersoft.role.validation.annotation.UniqueProgramName;

public class UniqueProgramNameValidator implements ConstraintValidator<UniqueProgramName, String>{
	private String message;
	@Autowired
	private ELProgramRepository repository;

	@Override
	public void initialize(UniqueProgramName uniqueProgramName) {
	message = uniqueProgramName.message();	
}
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		if(!repository.existsByName(name)) {
			return true;
		}

		context.buildConstraintViolationWithTemplate(message)
		.addConstraintViolation()
		.disableDefaultConstraintViolation();
		return false;

	}
}
