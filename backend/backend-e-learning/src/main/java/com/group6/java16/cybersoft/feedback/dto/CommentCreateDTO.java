package com.group6.java16.cybersoft.feedback.dto;

import javax.validation.constraints.NotBlank;

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
	private String userId;
	
	@NotBlank(message="{comment.lesson.not-blank}")
	private String lessonId;
	

}
