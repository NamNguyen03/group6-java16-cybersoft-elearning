package com.group6.java16.cybersoft.feedback.dto;

import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.user.model.ELUser;

public class RatingCreateDTO {
	@NotBlank(message="{rating.value.not-blank}")
	private int value ;
	
	@NotBlank(message="{rating.user.not-blank}")
	private ELUser user;
	
	@NotBlank(message="{rating.lesson.not-blank}")
	private ELLesson lesson;

}
