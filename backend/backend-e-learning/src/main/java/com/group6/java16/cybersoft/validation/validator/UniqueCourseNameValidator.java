package com.group6.java16.cybersoft.validation.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.validation.annotation.UniqueCourseName;



public class UniqueCourseNameValidator implements ConstraintValidator<UniqueCourseName, String> {

	private String message;
	
	@Autowired
	private ELCourseRepository repository;
	
	@Override
	public void initialize(UniqueCourseName uniqueCourseName) {
		message = uniqueCourseName.message();
	}
	
	@Override
	public boolean isValid(String courseName, ConstraintValidatorContext context) {
		Optional<ELCourse> courseOpt = repository.findByCourseName(courseName);
		
		if(courseOpt.isEmpty()) {
			return true;
		}
		
		context.buildConstraintViolationWithTemplate(message)
		.addConstraintViolation()
		.disableDefaultConstraintViolation();
		return false;
	}

}
