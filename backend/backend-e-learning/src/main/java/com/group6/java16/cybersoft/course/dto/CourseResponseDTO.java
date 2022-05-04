package com.group6.java16.cybersoft.course.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDTO {
	private UUID id;
	private String courseName;
	private int courseTime;
	private String description;
}
