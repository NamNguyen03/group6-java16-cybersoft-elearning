package com.group6.java16.cybersoft.course.service;

import com.group6.java16.cybersoft.common.model.PageRequestModel;
import com.group6.java16.cybersoft.common.model.PageResponseModel;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.course.dto.LessonUpdateDTO;

public interface LessonManagementService {
	LessonResponseDTO updateLesson( LessonUpdateDTO rq, String id);
	LessonResponseDTO createLesson(LessonCreateDTO dto);
	PageResponseModel<LessonResponseDTO> search(PageRequestModel pageRequestModel);
	void deleteById(String id);
	LessonResponseDTO getInfoLesson(String id);
}
