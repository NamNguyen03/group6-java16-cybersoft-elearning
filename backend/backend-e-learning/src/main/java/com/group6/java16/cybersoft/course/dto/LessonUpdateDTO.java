package com.group6.java16.cybersoft.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonUpdateDTO {
	
	private String name;
	
	private String content;
	
	private String description;
}
