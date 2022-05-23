package com.group6.java16.cybersoft.feedback.dto;

import javax.validation.constraints.NotBlank;

import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.user.model.ELUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDTO {
	@NotBlank(message="{comment.content.not-blank}")
	private String content;
	
	@NotBlank(message="{comment.user.not-blank}")
	private ELUser user;
	
	@NotBlank(message="{comment.lesson.not-blank}")
	private ELLesson lesson;
	

}
