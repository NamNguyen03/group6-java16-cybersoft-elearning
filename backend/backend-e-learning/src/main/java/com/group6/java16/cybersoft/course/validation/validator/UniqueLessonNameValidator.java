package com.group6.java16.cybersoft.course.validation.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.course.validation.annotation.UniqueLessonName;

public class UniqueLessonNameValidator implements ConstraintValidator<UniqueLessonName, String> {
	
	private String message;
	
	@Autowired
	private ELLessonRepository repository;
	
	@Override
	public void initialize(UniqueLessonName uniquelessonName) {
		message = uniquelessonName.message();
	}
	
	
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		Optional<ELLesson> lessonOpt = repository.findByName(name);
		
		if(lessonOpt.isEmpty()) {
			return true;
		}
		
		context.buildConstraintViolationWithTemplate(message)
		.addConstraintViolation()
		.disableDefaultConstraintViolation();
		return false;
	}
}
