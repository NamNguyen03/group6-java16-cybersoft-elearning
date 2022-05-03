package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.SessionCreateDTO;
import com.group6.java16.cybersoft.course.dto.SessionReponseDTO;
import com.group6.java16.cybersoft.course.dto.SessionUpdateDTO;

public interface SessionManagementService {
	SessionReponseDTO updateSession( SessionUpdateDTO rq, String id);
	SessionReponseDTO createSession(SessionCreateDTO dto);
	PageResponseModel<SessionReponseDTO> search(PageRequestModel pageRequestModel);
}
