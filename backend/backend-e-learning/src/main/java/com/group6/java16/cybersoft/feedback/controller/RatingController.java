package com.group6.java16.cybersoft.feedback.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.feedback.dto.RatingCreateDTO;
import com.group6.java16.cybersoft.feedback.dto.RatingResponseDTO;
import com.group6.java16.cybersoft.feedback.service.RatingService;
import com.group6.java16.cybersoft.security.authorization.ELPermission;

@RestController
@RequestMapping("api/v1/ratings")
@CrossOrigin(origins = "*")
public class RatingController {
	@Autowired
	private RatingService service;

	@GetMapping("{lesson-id}")
	public Object findRatingIntoLesson(@PathVariable("lesson-id") String lessonId) {
		List<RatingResponseDTO> response = service.search(lessonId);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

	@ELPermission("create rating")
	@PostMapping
	public Object createRating(@Valid @RequestBody RatingCreateDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseHelper.getResponse(bindingResult, HttpStatus.BAD_REQUEST, true);
		}
		RatingResponseDTO response = service.create(dto);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}

	@ELPermission("find my rating into lesson")
	@GetMapping("me/{lesson-id}")
	public Object findMyRatingIntoLesson(@PathVariable("lesson-id") String lessonId) {
		RatingResponseDTO response = service.getMyRatingByLesson(lessonId);
		return ResponseHelper.getResponse(response, HttpStatus.OK, false);
	}
}
