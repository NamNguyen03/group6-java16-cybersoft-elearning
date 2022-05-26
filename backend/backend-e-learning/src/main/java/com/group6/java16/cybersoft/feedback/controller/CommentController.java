package com.group6.java16.cybersoft.feedback.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.service.CommentService;

@RestController
@RequestMapping("api/v1/comments")
@CrossOrigin(origins = "*")
public class CommentController {
	@Autowired
	private CommentService service;

	@GetMapping("{lesson-id}")
	public Object findCommentIntoLesson(@PathVariable("lesson-id") String lessonId) {
		List<CommentResponseDTO> response = service.search(lessonId);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

	@PostMapping
	public Object createComment(@Valid @RequestBody CommentCreateDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseHelper.getResponse(bindingResult, HttpStatus.BAD_REQUEST, true);
		}
		CommentResponseDTO response = service.create(dto);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

	@DeleteMapping("{comment-id}")
	public Object deleteComment(@PathVariable("comment-id") String commentId) {

		service.delete(commentId);
		return ResponseHelper.getResponse("Delete Successfully", HttpStatus.OK, false);
	}

}
