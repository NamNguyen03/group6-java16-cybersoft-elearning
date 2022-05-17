package com.group6.java16.cybersoft.course.dto;

import java.util.Set;
import java.util.UUID;

import com.group6.java16.cybersoft.course.model.LevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDTO {
	private UUID id;

	private String courseName;

	private String description;

	private String img;

	private LevelEnum level;

	private float starAvg;

	private int totalStar;

	private int totalRating;

	private String skill1;

	private String skill2;

	private String skill3;

	private String skill4;

	private String skill5;

	private String category;
	private Set<LessonResponseDTO> lessons;
}
