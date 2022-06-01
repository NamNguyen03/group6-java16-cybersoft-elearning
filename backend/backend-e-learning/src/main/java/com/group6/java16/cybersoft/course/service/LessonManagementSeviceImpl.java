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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.group6.java16.cybersoft.common.exception.BusinessException;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.service.storage.MyFirebaseService;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.dto.client.Author;
import com.group6.java16.cybersoft.course.dto.client.LessonDetailsResponseClientDTO;
import com.group6.java16.cybersoft.course.mapper.LessonMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELLessonRepository;
import com.group6.java16.cybersoft.user.model.ELUser;
import com.group6.java16.cybersoft.user.repository.ELUserRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class LessonManagementSeviceImpl implements LessonManagementService {

	@Autowired
	private ELLessonRepository lessonRepository;

	@Autowired
	private ELCourseRepository courseRepository;
	
	@Autowired
	private ELUserRepository userRepository;

	@Value("${lesson.not-found}")
	private String errorslessonNotFound;
	
	@Value("${author.not-found}")
	private String authorNotFoud;

	@Value("${entity.id.invalid}")
	private String errorsIdInvalid;

	@Autowired
	private MyFirebaseService firebaseFileService;

	@Value("${course.not-found}")
	private String errorscourseNotFound;

	@Override
	public LessonResponseDTO updateLesson(LessonUpdateDTO rq, String id) {

		ELLesson lessonCurrent = getById(id);
		ELLesson lesson = setUpdatelesson(lessonCurrent, rq);
		return LessonMapper.INSTANCE.toLessonResponseDTO(lessonRepository.save(lesson));
	}

	private ELLesson setUpdatelesson(ELLesson lessonCurrent, LessonUpdateDTO rq) {
		if (checkString(rq.getName())) {
			lessonCurrent.setName(rq.getName());
		}

		if (checkString(rq.getContent())) {
			lessonCurrent.setContent(rq.getContent());
		}

		if (checkString(rq.getDescription())) {
			lessonCurrent.setDescription(rq.getDescription());
		}

		return lessonCurrent;
	}

	private boolean checkString(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public LessonResponseDTO createLesson(LessonCreateDTO dto) {

		// Map dto to lesson

		ELLesson s = LessonMapper.INSTANCE.toModel(dto);
		ELCourse c = courseRepository.findById(UUID.fromString(dto.getCourseId())).orElseThrow(
				() -> new BusinessException(errorscourseNotFound));

		s.setStarAvg(0f);
		s.setTotalStar(0);
		s.setTotalRating(0);
		c.setCourseTime(c.getCourseTime()+1);

		courseRepository.save(c);

		// save lesson return lesson
		s.setCourse(c);
		ELLesson lesson = lessonRepository.save(s);


		return LessonMapper.INSTANCE.toLessonResponseDTO(lesson);
	}

	@Override
	public PageResponseModel<LessonResponseDTO> search(PageRequestModel pageRequestModel) {
		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELLesson> rp = null;

		if (null != fieldNameSort && fieldNameSort.matches("name")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());
		}else{
			pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
		}

		// lessonName
		if ("name".equals(fieldNameSearch)) {
			rp = lessonRepository.searchByLessonName(valueSearch, pageable);
		}

		// if firstName not existed then search all
		if (rp == null) {
			rp = lessonRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(),
				rp.getContent().stream().map(LessonMapper.INSTANCE::toLessonResponseDTO).collect(Collectors.toList()));
	}

	@Override
	public void deleteById(String id) {
		ELCourse c = getById(id).getCourse();

		c.setCourseTime(c.getCourseTime()-1);

		courseRepository.save(c);
		
		lessonRepository.delete(getById(id));

		
	}

	@Override
	public LessonResponseDTO getInfoLesson(String id) {
		ELLesson lesson = getById(id);
		return LessonMapper.INSTANCE.toLessonResponseDTO(lesson);
	}

	private ELLesson getById(String id) {
		return lessonRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new BusinessException(errorslessonNotFound));
	}

	@Override
	public String postImg(MultipartFile file) {
		return firebaseFileService.saveFile(file);
	}

	@Override
	public LessonDetailsResponseClientDTO getLessonDetail(String id) {
		
		ELLesson lesson = lessonRepository.getDetailsById(UUID.fromString(id));
		ELUser user = userRepository.findByUsername(lesson.getCourse().getCreatedBy()).orElseThrow(()-> new BusinessException(authorNotFoud));
		LessonDetailsResponseClientDTO clientDTO = LessonMapper.INSTANCE.toLessonDetailsClientDTO(lesson);
		clientDTO.setAuthor(new Author(user.getId(),user.getAvatar(),user.getDisplayName(),user.getUsername()));
		return clientDTO;
		
	}

}
