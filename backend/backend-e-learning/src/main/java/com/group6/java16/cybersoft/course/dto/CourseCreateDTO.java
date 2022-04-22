package com.group6.java16.cybersoft.course.dto;

import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.user.dto.UserCreateDTO;
import com.group6.java16.cybersoft.user.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CourseCreateDTO {
	@Size(min = 6,max = 100,message = "{}")
	private String courseName;
	
	private int courseTime;
	
	private String description;
}
