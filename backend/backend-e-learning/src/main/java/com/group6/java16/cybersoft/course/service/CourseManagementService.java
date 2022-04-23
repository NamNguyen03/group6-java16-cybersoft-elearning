package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;


public interface CourseManagementService {
	CourseReponseDTO updateCourse( CourseUpdateDTO rq, String id);
	CourseReponseDTO createCourse(CourseCreateDTO dto);
	void deleteById(String id);
	PageResponseModel<CourseReponseDTO> search(PageRequestModel pageRequestModel);
}
