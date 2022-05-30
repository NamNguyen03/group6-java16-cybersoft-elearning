/**
 * 
 */
package com.group6.java16.cybersoft.feedback.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.group6.java16.cybersoft.feedback.validation.anotation.ValueRating;

public class ValueRatingValidator implements ConstraintValidator<ValueRating, Integer> {

	private String message;

	@Override
	public void initialize(ValueRating valueRating) {
		message = valueRating.message();
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(value >= 0 && value <= 5) {
			return true;
		}
		
		context.buildConstraintViolationWithTemplate(message)
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		return false;
	}
}