package com.group6.java16.cybersoft.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonReponseDTO;
import com.group6.java16.cybersoft.course.model.ELSession;

@Mapper
public interface SessionMapper {
	SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);
    
    ELSession toModel(LessonCreateDTO session);

    LessonReponseDTO toSessionResponseDTO(ELSession session);
}
