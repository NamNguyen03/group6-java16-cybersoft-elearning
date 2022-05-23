package com.group6.java16.cybersoft.feedback.dto;

import java.util.UUID;

import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.user.model.ELUser;

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
	
	private ELUser user;
	
	private ELLesson lesson;
	

}
