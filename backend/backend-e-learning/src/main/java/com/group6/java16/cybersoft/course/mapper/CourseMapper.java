package com.group6.java16.cybersoft.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.ELCourse;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    ELCourse toModel(CourseCreateDTO course);

    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryValue")
    CourseResponseDTO toCourseResponseDTO(ELCourse course);

    @Named("toCategoryValue")
    public static String toCategoryValue(CategoryEnum category) {
        return category.value();
    }
}
