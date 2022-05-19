package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;


public interface CourseManagementService {
	CourseResponseDTO updateCourse( CourseUpdateDTO rq, String id);
	CourseResponseDTO createCourse(CourseCreateDTO dto);
	void deleteById(String id);
	PageResponseModel<CourseResponseDTO> search(PageRequestModel pageRequestModel);
	CourseResponseDTO getDetailCourse(String id);
}
