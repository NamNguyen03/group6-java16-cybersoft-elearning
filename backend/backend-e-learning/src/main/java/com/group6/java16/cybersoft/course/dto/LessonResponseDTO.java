package com.group6.java16.cybersoft.course.dto;

import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class LessonResponseDTO {
	
	private UUID id;
	
	private String name;
	
	private String content;
	
	private String description;
}
