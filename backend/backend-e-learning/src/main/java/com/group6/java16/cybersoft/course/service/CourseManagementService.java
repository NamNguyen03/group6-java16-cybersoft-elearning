package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;

public interface CourseManagementService {
	
	CourseReponseDTO createCourse(CourseCreateDTO dto);
}
