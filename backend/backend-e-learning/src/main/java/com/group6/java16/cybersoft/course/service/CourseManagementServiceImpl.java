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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class CourseManagementServiceImpl implements CourseManagementService {

	@Autowired
	private ELCourseRepository courseRepository;
	
	@Autowired
	private MyFirebaseService firebaseFileService;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Value("${course.not-found}")
	private String errorsCourseNotFound;

	@Value("${lesson.id.not-found}")
	private String errorsLessonIdNotFound;
	
	@Value("${course.name.existed}")
	private String messageNameCouseExists;

	@Override
	public CourseResponseDTO createCourse(CourseCreateDTO dto) {
		// Map dto to course
		ELCourse c = CourseMapper.INSTANCE.toModel(dto);
		c.setStarAvg(0f);
		c.setTotalStar(0);
		c.setTotalRating(0);

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
			if (!courseCurrent.getCourseName().equals(rq.getCourseName()) && courseRepository.existsByCourseName(rq.getCourseName())) {
				throw new BusinessException(messageNameCouseExists);
			}
			courseCurrent.setCourseName(rq.getCourseName());
		}

		if (checkString(rq.getDescription())) {
			courseCurrent.setDescription(rq.getDescription());
		}
		if (checkString(rq.getImg())) {
			courseCurrent.setImg(rq.getImg());
		}
		if (rq.getLevel() != null) {
			courseCurrent.setLevel(rq.getLevel());
		}
		if (checkString(rq.getSkill1())) {
			courseCurrent.setSkill1(rq.getSkill1());
		}
		if (checkString(rq.getSkill2())) {
			courseCurrent.setSkill2(rq.getSkill2());
		}
		if (checkString(rq.getSkill3())) {
			courseCurrent.setSkill3(rq.getSkill3());
		}
		if (checkString(rq.getSkill4())) {
			courseCurrent.setSkill4(rq.getSkill4());
		}
		if (checkString(rq.getSkill5())) {
			courseCurrent.setSkill5(rq.getSkill5());
		}
		if (rq.getCategory() != null) {
			courseCurrent.setCategory(rq.getCategory());
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
		}else {
			pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		}

		if ("courseName".equals(fieldNameSearch)) {
			rp = courseRepository.searchByCourseName(valueSearch, pageable);
		}
		
		if ("category".equals(fieldNameSearch)) {
			rp = courseRepository.findByCategory(CategoryEnum.valueOf(valueSearch), pageable);
		}

		// if firstName not existed then search all
		if (rp == null) {
			rp = courseRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(),
				rp.getContent().stream().map(CourseMapper.INSTANCE::toCourseResponseDTO).collect(Collectors.toList()));
	}

	@Override
	public CourseResponseDTO getDetailCourse(String id) {
		ELCourse course = getById(id);
		return CourseMapper.INSTANCE.toCourseResponseDTO(course);
	}

	private ELCourse getById(String id) {
		return courseRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new BusinessException(errorsCourseNotFound));
	}

	@Override
	public String updateImg(MultipartFile file) {
		  String urlImg = firebaseFileService.saveFile(file);
		return urlImg;
	}

}
