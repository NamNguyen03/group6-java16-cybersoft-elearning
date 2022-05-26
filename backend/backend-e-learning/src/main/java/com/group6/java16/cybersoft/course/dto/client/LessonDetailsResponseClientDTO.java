package com.group6.java16.cybersoft.course.dto.client;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonDetailsResponseClientDTO {
	private UUID id;
	
	private String name;
	
	private String content;
	
	private String description;
	
	private CourseDetailsReponseClientDTO course;
}
