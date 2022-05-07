package com.group6.java16.cybersoft.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.group6.java16.cybersoft.validation.validator.UniqueLessonNameValidator;

@Constraint(validatedBy = UniqueLessonNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueLessonName {

	String message() default "Session name existed.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
