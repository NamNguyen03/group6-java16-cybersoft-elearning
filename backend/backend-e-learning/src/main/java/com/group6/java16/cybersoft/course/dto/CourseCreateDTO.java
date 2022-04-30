package com.group6.java16.cybersoft.course.dto;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.validation.annotation.UniqueCourseName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CourseCreateDTO {
	@Size(min = 6,max = 100,message = "{course.name.size}")
	@NotBlank(message = "{course.name.not-blank}")
	@UniqueCourseName(message = "course.coursename.existed")
	private String courseName;
	
	private int courseTime;
	
	private String description;
}
