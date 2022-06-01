package com.group6.java16.cybersoft.course.dto.client;

import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CardLessonReponseClientDTO {
	
	private UUID id;
	
	private String name;
	
	private String content;
	
	private String description;
	
}
