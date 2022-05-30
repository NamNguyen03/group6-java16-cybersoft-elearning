package com.group6.java16.cybersoft.feedback.dto;

import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.feedback.validation.anotation.ValueRating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RatingCreateDTO {
	
	@ValueRating(message="{rating.value.invalid}")
	private int value;
	
	@NotBlank(message="{rating.lesson.not-blank}")
	private String lessonId;

}
