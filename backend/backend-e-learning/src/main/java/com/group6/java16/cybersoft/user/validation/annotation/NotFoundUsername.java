package com.group6.java16.cybersoft.user.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.group6.java16.cybersoft.user.validation.validator.NotFoundUsernameValidator;

@Constraint(validatedBy = NotFoundUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NotFoundUsername {
    String message() default "Username not existed.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

