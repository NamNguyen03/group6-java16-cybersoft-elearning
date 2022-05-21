package com.group6.java16.cybersoft.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonUpdateDTO {
	
	private String name;
	
	private String content;
	
	private String description;
}
