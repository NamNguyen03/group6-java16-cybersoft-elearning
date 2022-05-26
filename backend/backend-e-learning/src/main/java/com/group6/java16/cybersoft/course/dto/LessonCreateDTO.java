package com.group6.java16.cybersoft.course.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.validation.annotation.UniqueLessonName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class LessonCreateDTO {
	@Size(min = 6,max = 100,message = "{lesson.name.size}")
	@NotBlank(message = "{lesson.name.not-blank}")
	@UniqueLessonName(message = "{lesson.name.existed}")
	private String name;
	
	@NotBlank(message = "{lesson.content.not-blank}")
	private String content;
	
	@NotBlank(message = "{lesson.description.not-blank}")
	private String description;
	
	@NotBlank(message = "{lesson.courseid.not-blank}")
	private String courseId;
}
