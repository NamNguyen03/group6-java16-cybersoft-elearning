package com.group6.java16.cybersoft.feedback.service;

import java.util.List;


import com.group6.java16.cybersoft.feedback.dto.CommentResponseDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;

public interface RatingService {

	List<CommentResponseDTO> search(String lessonId);

	RatingResponseDTO create(RatingCreateDTO dto);

}
