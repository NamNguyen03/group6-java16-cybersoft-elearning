package com.group6.java16.cybersoft.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;

@Service
public class CourseManagementServiceImple implements CourseManagementService {
	
	@Autowired
	private ELCourseRepository courseRepository;

	@Override
	public CourseReponseDTO createCourse(CourseCreateDTO dto) {
		
		//Map dto to course
		
		ELCourse c = CourseMapper.INSTANCE.toModel(dto);
		
		//save course return user
		 ELCourse cour = courseRepository.save(c);
		 
		//Map userv to dto
		 CourseReponseDTO crp = CourseMapper.INSTANCE.toCourseResponseDTO(cour);
		
		return crp;
	}

}
