package com.group6.java16.cybersoft.course.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.service.LessonManagementService;

@RestController
@RequestMapping("api/v1/lessons")
@CrossOrigin(origins = "*")
public class LessonManagementController {
	@Autowired
	private LessonManagementService service;

	@PostMapping
	public Object createSession(@Valid @RequestBody LessonCreateDTO rq, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
		}

		LessonResponseDTO rp = service.createLesson(rq);

		return ResponseHelper.getResponse(rp, HttpStatus.CREATED, false);
	}

	@PutMapping("{id}")
	public Object updateSession(@PathVariable("id") String id, @Valid @RequestBody LessonUpdateDTO rq,
			BindingResult result) {

		if (result.hasErrors()) {
			return ResponseHelper.getResponse(result, HttpStatus.BAD_REQUEST, true);
		}

		LessonResponseDTO rp = service.updateLesson(rq, id);

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}

	@GetMapping()
	public Object search(@RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
			@RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
			@RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
			@RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
			@RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
			@RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch) {

		PageResponseModel<LessonResponseDTO> rp = service.search(new PageRequestModel(
				pageCurrent,
				itemPerPage,
				fieldNameSort,
				isIncrementSort,
				fieldNameSearch,
				valueFieldNameSearch));

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}

	@DeleteMapping("{id}")
	public Object delete(@PathVariable("id") String id) {

		service.deleteById(id);

		return ResponseHelper.getResponse("", HttpStatus.OK, false);
	}

	@GetMapping("{id}")
	public Object getInfoLesson(@PathVariable("id") String id) {

		LessonResponseDTO rp = service.getInfoLesson(id);

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}
}
