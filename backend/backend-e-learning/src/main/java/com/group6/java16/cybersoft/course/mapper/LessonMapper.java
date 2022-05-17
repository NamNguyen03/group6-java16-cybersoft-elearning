package com.group6.java16.cybersoft.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.course.dto.LessonCreateDTO;
import com.group6.java16.cybersoft.course.dto.LessonResponseDTO;
import com.group6.java16.cybersoft.course.model.ELLesson;

@Mapper
public interface LessonMapper {
	LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);
    
    ELLesson toModel(LessonCreateDTO lesson);

    LessonResponseDTO toLessonResponseDTO(ELLesson lesson);
}
