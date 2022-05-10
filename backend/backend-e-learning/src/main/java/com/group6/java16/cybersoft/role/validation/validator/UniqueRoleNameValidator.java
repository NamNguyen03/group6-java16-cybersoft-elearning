package com.group6.java16.cybersoft.role.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.role.repository.ELRoleRepository;
import com.group6.java16.cybersoft.role.validation.annotation.UniqueRoleName;

public class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName, String> {
	
	private String message;
	@Autowired
	private ELRoleRepository repository;

	@Override
	public void initialize(UniqueRoleName uniqueRoleName) {
	message = uniqueRoleName.message();	
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
