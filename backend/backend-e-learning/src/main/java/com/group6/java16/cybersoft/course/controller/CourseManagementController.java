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
import org.springframework.web.multipart.MultipartFile;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ResponseHelper;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
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

		CourseResponseDTO rp = service.createCourse(rq);

		return ResponseHelper.getResponse(rp, HttpStatus.CREATED, false);
	}

	@PutMapping("{id}")
	public Object updateCourse(@PathVariable("id") String id,@RequestBody CourseUpdateDTO rq) {


		CourseResponseDTO rp = service.updateCourse(rq, id);

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}

	@GetMapping()
	public Object search(@RequestParam(value = "pageCurrent", defaultValue = "1") int pageCurrent,
			@RequestParam(value = "itemPerPage", defaultValue = "10") int itemPerPage,
			@RequestParam(value = "fieldNameSort", required = false) String fieldNameSort,
			@RequestParam(value = "isIncrementSort", defaultValue = "true") boolean isIncrementSort,
			@RequestParam(value = "fieldNameSearch", required = false) String fieldNameSearch,
			@RequestParam(value = "valueFieldNameSearch", required = false) String valueFieldNameSearch) {

		PageResponseModel<CourseResponseDTO> rp = service.search(new PageRequestModel(
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
	public Object getDetailCourse(@PathVariable("id") String id) {

		CourseResponseDTO rp = service.getDetailCourse(id);

		return ResponseHelper.getResponse(rp, HttpStatus.OK, false);
	}
	@PostMapping("{img}")
	public Object updateImg(@RequestParam(name = "file") MultipartFile file) {
		String urlImg = service.updateImg(file);
		return ResponseHelper.getResponse(urlImg, HttpStatus.OK, false);
	}

}
