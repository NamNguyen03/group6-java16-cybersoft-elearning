package com.group6.java16.cybersoft.feedback.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RatingCreateDTO {
	private int value ;
	
	@NotBlank(message="{rating.lesson.not-blank}")
	private String lessonId;

}
