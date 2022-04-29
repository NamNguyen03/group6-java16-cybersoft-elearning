package com.group6.java16.cybersoft.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseReponseDTO;
import com.group6.java16.cybersoft.course.model.ELCourse;

@Mapper
public interface CourseMapper {
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    
    ELCourse toModel(CourseCreateDTO course);

    CourseReponseDTO toCourseResponseDTO(ELCourse course);
}
