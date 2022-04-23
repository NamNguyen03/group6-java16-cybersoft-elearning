package com.group6.java16.cybersoft.course.service;

import java.util.List;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.model.ELCourse;


public interface CourseManagementService {
	List<ELCourse> findAllEntity();
	CourseReponseDTO createCourse(CourseCreateDTO dto);
}
