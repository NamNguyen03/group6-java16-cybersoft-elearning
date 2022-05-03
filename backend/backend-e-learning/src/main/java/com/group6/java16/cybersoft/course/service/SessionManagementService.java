package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonReponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;

public interface SessionManagementService {
	LessonReponseDTO updateSession( LessonUpdateDTO rq, String id);
	LessonReponseDTO createSession(LessonCreateDTO dto);
	PageResponseModel<LessonReponseDTO> search(PageRequestModel pageRequestModel);
	void deleteById(String id);
}
