/**
 * 
 */
package com.group6.java16.cybersoft.feedback.validation.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.group6.java16.cybersoft.feedback.validation.validator.ValueRatingValidator;


@Constraint(validatedBy = ValueRatingValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValueRating {

	String message() default "Session name existed.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}