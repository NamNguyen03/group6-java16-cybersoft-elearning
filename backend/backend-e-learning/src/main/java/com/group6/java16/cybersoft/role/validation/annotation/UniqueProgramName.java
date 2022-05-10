package com.group6.java16.cybersoft.role.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.group6.java16.cybersoft.role.validation.validator.UniqueProgramNameValidator;
import com.group6.java16.cybersoft.role.validation.validator.UniqueRoleNameValidator;

@Constraint(validatedBy = UniqueProgramNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueProgramName {
String message() default "Program Name already used.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
