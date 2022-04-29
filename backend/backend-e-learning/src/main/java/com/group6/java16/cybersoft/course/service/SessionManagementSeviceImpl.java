package com.group6.java16.cybersoft.course.service;

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

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.common.util.ServiceHelper;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.dto.CourseUpdateDTO;
import com.group6.java16.cybersoft.course.dto.SessionCreateDTO;
import com.group6.java16.cybersoft.course.dto.SessionReponseDTO;
import com.group6.java16.cybersoft.course.dto.SessionUpdateDTO;
import com.group6.java16.cybersoft.course.mapper.CourseMapper;
import com.group6.java16.cybersoft.course.mapper.SessionMapper;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELSession;
import com.group6.java16.cybersoft.course.repository.ELSessionRepository;

@Service
@PropertySources({ @PropertySource("classpath:/validation/message.properties") })
public class SessionManagementSeviceImpl implements SessionManagementService {
	
	@Autowired
	private ELSessionRepository sessionRepository;

	@Autowired
	private ServiceHelper<ELSession> serviceSessionHelper;
	
	@Value("${session.not-found}")
	private String errorsSessionNotFound;

	@Override
	public SessionReponseDTO updateSession(SessionUpdateDTO rq, String id) {
			
		ELSession sessionCurrent = serviceSessionHelper.getEntityById(id, sessionRepository, errorsSessionNotFound);
		ELSession session = setUpdateSession(sessionCurrent, rq);
		return SessionMapper.INSTANCE.toSessionResponseDTO(sessionRepository.save(session));
	}
	
	private ELSession setUpdateSession(ELSession sessionCurrent, SessionUpdateDTO rq) {
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
	public SessionReponseDTO createSession(SessionCreateDTO dto) {
		

		// Map dto to session

		ELSession s = SessionMapper.INSTANCE.toModel(dto);

		// save session return session
		ELSession session = sessionRepository.save(s);

		// Map session to dto
		SessionReponseDTO srp = SessionMapper.INSTANCE.toSessionResponseDTO(session);

		return srp;
	}

	@Override
	public void deleteById(String id) {
		sessionRepository.delete(serviceSessionHelper.getEntityById(id, sessionRepository, errorsSessionNotFound));
	}

	@Override
	public PageResponseModel<SessionReponseDTO> search(PageRequestModel pageRequestModel) {
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

}
