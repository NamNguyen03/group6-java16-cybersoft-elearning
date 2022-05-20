package com.group6.java16.cybersoft.course.dto;

import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.LevelEnum;
import com.group6.java16.cybersoft.validation.annotation.UniqueCourseName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CourseCreateDTO {
	@NotBlank(message = "{course.name.not-blank}")
	@UniqueCourseName(message = "{course.coursename.existed}")
	private String courseName;

	@NotBlank(message = "{course.img.not-blank}")
	private String img;

	@NotBlank(message = "{course.description.not-blank}")
	private String description;

	private LevelEnum level = LevelEnum.BEGINNER;

	private CategoryEnum category;

	@NotBlank(message = "{course.skill.not-blank}")
	private String skill1;

	@NotBlank(message = "{course.skill.not-blank}")
	private String skill2;

	@NotBlank(message = "{course.skill.not-blank}")
	private String skill3;

	private String skill4;

	private String skill5;

}
