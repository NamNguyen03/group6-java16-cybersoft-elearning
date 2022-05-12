package com.group6.java16.cybersoft.course.service;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class CourseManagementServiceImpl extends ServiceHelper<ELCourse> implements CourseManagementService {

	@Autowired
	private ELCourseRepository courseRepository;

	@Value("${entity.id.invalid}")
    private String errorsIdInvalid;

	@Value("${course.not-found}")
	private String errorsCourseNotFound;

	@Override
	public CourseResponseDTO createCourse(CourseCreateDTO dto) {

		// Map dto to course
		ELCourse c = CourseMapper.INSTANCE.toModel(dto);

		// save course return user
		ELCourse cour = courseRepository.save(c);

		// Map user to dto
		CourseResponseDTO crp = CourseMapper.INSTANCE.toCourseResponseDTO(cour);

		return crp;
	}

	@Override
	public CourseResponseDTO updateCourse(CourseUpdateDTO rq, String id) {
		ELCourse courseCurrent = getById(id);
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
		courseRepository.delete(getById(id));
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

	@Override
	public PageResponseModel<CourseResponseDTO> search(PageRequestModel pageRequestModel) {
		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELCourse> rp = null;

		if (null != fieldNameSort && fieldNameSort.matches("courseName")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());

		}

		// coursename
		if ("courseName".equals(fieldNameSearch)) {
			rp = courseRepository.searchByCourseName(valueSearch, pageable);
		}

		// if firstName not existed then search all
		if (rp == null) {
			rp = courseRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(), 
	            rp.getContent().stream().map(CourseMapper.INSTANCE::toCourseResponseDTO).collect(Collectors.toList()));
	}

	@Override
	protected String getMessageIdInvalid() {
		return errorsIdInvalid;
	}

	@Override
	protected JpaRepository<ELCourse, UUID> getRepository() {
		return courseRepository;
	}

	@Override
	protected String getErrorNotFound() {
		return errorsCourseNotFound;
	}

}
