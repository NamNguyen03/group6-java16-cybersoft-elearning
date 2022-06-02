package com.group6.java16.cybersoft.feedback.service;

import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;

public interface RatingService {

	RatingResponseDTO create(RatingCreateDTO dto);

	RatingResponseDTO getMyRatingByLesson(String lessonId);

}
