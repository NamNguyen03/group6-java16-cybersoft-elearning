package com.group6.java16.cybersoft.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseUpdateDTO {
	private String courseName;
		
		private int courseTime;
	
	private String description;
}
