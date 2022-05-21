package com.group6.java16.cybersoft.course.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class CourseResponseDTO {
	private UUID id;

	private String courseName;

	private String description;

	private String img;

	private String level;

	private float starAvg;
	
	private int courseTime;

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
