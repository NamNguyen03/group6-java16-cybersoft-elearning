package com.group6.java16.cybersoft.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;

@Service
public class CourseManagementServiceImple implements CourseManagementService {

	@Autowired
	private ELCourseRepository courseRepository;

	@Autowired
	private ServiceHelper<ELCourse> serviceCourseHelper;

	@Value("${user.not-found}")
	private String errorsCourseNotFound;

	@Override
	public CourseReponseDTO createCourse(CourseCreateDTO dto) {

		// Map dto to course

		ELCourse c = CourseMapper.INSTANCE.toModel(dto);

		// save course return user
		ELCourse cour = courseRepository.save(c);

		// Map userv to dto
		CourseReponseDTO crp = CourseMapper.INSTANCE.toCourseResponseDTO(cour);

		return crp;
	}

	@Override
	public CourseReponseDTO updateCourse(CourseUpdateDTO rq, String id) {
		ELCourse courseCurrent = serviceCourseHelper.getEntityById(id, courseRepository, errorsCourseNotFound);
		ELCourse course = setUpdateCourse(courseCurrent, rq);
		return CourseMapper.INSTANCE.toCourseResponseDTO(courseRepository.save(course));
	}

	private ELCourse setUpdateCourse(ELCourse courseCurrent, CourseUpdateDTO rq) {
		if (checkString(rq.getCourseName())) {
			courseCurrent.setCourseName(rq.getCourseName());
		}

		if (checkInt(rq.getCourseTime())) {
			courseCurrent.setCourseTime(rq.getCourseTime());
		}

		if (checkString(rq.getDescription())) {
			courseCurrent.setDescription(rq.getDescription());
		}

		return courseCurrent;
	}

	@Override
	public void deleteById(String id) {
		
	}

	private boolean checkString(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		return true;
	}

	private boolean checkInt(int s) {
		if (s <= 0) {
			return false;
		}
		return true;
	}


}
