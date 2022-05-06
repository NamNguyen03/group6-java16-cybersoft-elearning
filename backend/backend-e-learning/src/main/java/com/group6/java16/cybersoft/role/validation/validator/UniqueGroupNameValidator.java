package com.group6.java16.cybersoft.role.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.role.repository.ELGroupRepository;
import com.group6.java16.cybersoft.role.validation.annotation.UniqueGroupName;

public class UniqueGroupNameValidator implements ConstraintValidator<UniqueGroupName, String>{
	private String message;
	@Autowired
    private ELGroupRepository repository;
	
	@Override
	public void initialize(UniqueGroupName uniqueGroupName) {
		message = uniqueGroupName.message();	
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
