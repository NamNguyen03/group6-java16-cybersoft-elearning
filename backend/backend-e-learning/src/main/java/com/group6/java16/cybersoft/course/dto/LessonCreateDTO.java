package com.group6.java16.cybersoft.course.dto;


import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.validation.annotation.UniqueLessonName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LessonCreateDTO {
	@NotBlank(message = "{lesson.name.not-blank}")
	@UniqueLessonName(message = "{lesson.name.existed}")
	private String name;
	
	@NotBlank(message = "{lesson.content.not-blank}")
	private String content;
	
	@NotBlank(message = "{lesson.description.not-blank}")
	private String description;
	
	@NotBlank(message = "{lesson.courseid.not-blank}")
	private String course_id;
}
