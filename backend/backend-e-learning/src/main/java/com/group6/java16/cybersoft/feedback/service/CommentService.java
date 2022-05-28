package com.group6.java16.cybersoft.feedback.service;

import java.util.List;

import com.group6.java16.cybersoft.feedback.dto.CommentCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;

public interface CommentService {

	List<CommentResponseDTO> search(String lessonId);

	CommentResponseDTO create(CommentCreateDTO rq);

	CommentResponseDTO update(String commentId, String contentUpdate);

	void delete(String commentId);
}
