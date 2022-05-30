package com.group6.java16.cybersoft.course.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.group6.java16.cybersoft.course.validation.validator.UniqueCourseNameValidator;


@Constraint(validatedBy = UniqueCourseNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueCourseName {
	
	String message() default "Course name existed.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
