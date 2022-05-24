package com.group6.java16.cybersoft.feedback.dto;

import java.util.UUID;

import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.user.dto.UserResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
	private UUID id;
	
	private String content;
	
	private UserResponseDTO user;
	
	private LessonResponseDTO lesson;
	

}
