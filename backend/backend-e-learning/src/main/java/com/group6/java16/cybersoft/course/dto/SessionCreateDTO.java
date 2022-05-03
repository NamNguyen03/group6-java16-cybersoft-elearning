package com.group6.java16.cybersoft.course.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.group6.java16.cybersoft.validation.annotation.UniqueSessionName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SessionCreateDTO {
	@Size(min = 6,max = 100,message = "{session.name.size}")
	@NotBlank(message = "{session.name.not-blank}")
	@UniqueSessionName(message = "{session.name.existed}")
	private String sessionName;
	
	private String img;
	
	@NotBlank(message = "{session.description.not-blank}")
	private String sessionDescription;
	
	@NotBlank(message = "{session.courseid.not-blank}")
	private String course_id;
}
