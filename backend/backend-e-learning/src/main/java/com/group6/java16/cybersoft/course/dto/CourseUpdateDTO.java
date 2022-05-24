package com.group6.java16.cybersoft.course.dto;

import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.LevelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseUpdateDTO {

	private String courseName;

	private String description;

	private String img;

	private LevelEnum level;

	private String skill1;

	private String skill2;

	private String skill3;

	private String skill4;

	private String skill5;

	private CategoryEnum category;
}
