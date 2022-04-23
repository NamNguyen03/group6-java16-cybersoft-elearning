package com.group6.java16.cybersoft.course.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.service.CourseManagementService;

@RestController
@RequestMapping("api/v1/courses")
@CrossOrigin(origins = "*")
public class CourseManagementController {
	@Autowired
	private CourseManagementService service;

	@PostMapping
	public Object createCourse(@Valid @RequestBody CourseCreateDTO rq, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
		}

		CourseReponseDTO rp = service.createCourse(rq);

		return ResponseHelper.getResponse(rp, HttpStatus.CREATED, false);
	}

	@PutMapping("{id}")
	public Object updateCourse(@PathVariable("id") String id, @Valid @RequestBody CourseUpdateDTO rq,
			BindingResult result) {

		if (result.hasErrors()) {
			return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
		}

		CourseReponseDTO rp = service.updateCourse(rq, id);

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}
}
