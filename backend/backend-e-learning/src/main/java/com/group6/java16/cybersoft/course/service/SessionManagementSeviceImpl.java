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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.group6.java16.cybersoft.common.model.BaseEntity;
import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonReponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.mapper.SessionMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELSession;
import com.group6.java16.cybersoft.course.repository.ELCourseRepository;
import com.group6.java16.cybersoft.course.repository.ELSessionRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class SessionManagementSeviceImpl extends ServiceHelper<ELSession> implements SessionManagementService {
	
	@Autowired
	private ELSessionRepository sessionRepository;
	@Autowired
	private ELCourseRepository courseRepository;
	
	@Value("${session.not-found}")
	private String errorsSessionNotFound;
	
	@Value("${entity.id.invalid}")
    private String errorsIdInvalid;

	@Override
	public LessonReponseDTO updateSession(LessonUpdateDTO rq, String id) {
			
//		ELSession sessionCurrent = serviceSessionHelper.getEntityById(id, sessionRepository, errorsSessionNotFound);
//		ELSession session = setUpdateSession(sessionCurrent, rq);
//		return SessionMapper.INSTANCE.toSessionResponseDTO(sessionRepository.save(session));
		return null;
	}
	
	private ELSession setUpdateSession(ELSession sessionCurrent, LessonUpdateDTO rq) {
		if (checkString(rq.getSessionName())) {
			sessionCurrent.setSessionName(rq.getSessionName());
		}

		if (checkString(rq.getImg())) {
			sessionCurrent.setImg(rq.getImg());
		}

		if (checkString(rq.getSessionDescription())) {
			sessionCurrent.setSessionDescription(rq.getSessionDescription());
		}

		return sessionCurrent;
	}
	
	private boolean checkString(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public LessonReponseDTO createSession(LessonCreateDTO dto) {
		

		// Map dto to session

		ELSession s = SessionMapper.INSTANCE.toModel(dto);
		ELCourse c = courseRepository.findById(UUID.fromString(dto.getCourse_id())).get();

		// save session return session
		s.setCourse(c);
		ELSession session = sessionRepository.save(s);

		// Map session to dto
		LessonReponseDTO srp = SessionMapper.INSTANCE.toSessionResponseDTO(session);

		return srp;
	}

	@Override
	public PageResponseModel<LessonReponseDTO> search(PageRequestModel pageRequestModel) {
		int page = pageRequestModel.getPageCurrent() - 1;
		int size = pageRequestModel.getItemPerPage();
		boolean isAscending = pageRequestModel.isIncrementSort();
		String fieldNameSearch = pageRequestModel.getFieldNameSearch();
		String fieldNameSort = pageRequestModel.getFieldNameSort();
		String valueSearch = pageRequestModel.getValueSearch();
		Pageable pageable = PageRequest.of(page, size);
		Page<ELSession> rp = null;

		if (null != fieldNameSort && fieldNameSort.matches("sessionName")) {
			pageable = PageRequest.of(page, size,
					isAscending ? Sort.by(fieldNameSort).ascending() : Sort.by(fieldNameSort).descending());

		}

		// coursename
		if ("sessionName".equals(fieldNameSearch)) {
			rp = sessionRepository.searchBySessionName(valueSearch, pageable);
		}

		// if firstName not existed then search all
		if (rp == null) {
			rp = sessionRepository.findAll(pageable);
		}

		return new PageResponseModel<>(rp.getNumber() + 1, rp.getTotalPages(), 
	            rp.getContent().stream().map(SessionMapper.INSTANCE::toSessionResponseDTO).collect(Collectors.toList()));
	}

	@Override
	public void deleteById(String id) {
		sessionRepository.delete(getById(id));
	}

	@Override
	protected String getMessageIdInvalid() {
		return errorsIdInvalid;
	}

	@Override
	protected JpaRepository<ELSession, UUID> getRepository() {
		return sessionRepository;
	}

	@Override
	protected String getErrorNotFound() {
		return errorsSessionNotFound;
	}

}
