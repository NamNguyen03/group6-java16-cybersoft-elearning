package com.group6.java16.cybersoft.course.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.group6.java16.cybersoft.course.dto.CourseCreateDTO;
import com.group6.java16.cybersoft.course.dto.CourseResponseDTO;
import com.group6.java16.cybersoft.course.dto.client.CardCourseReponseClientDTO;
import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELLesson;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    ELCourse toModel(CourseCreateDTO course);
    
    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryValue")
    CourseResponseDTO toCourseResponseDTO(ELCourse course);
    
    @Mapping(source = "lessons", target = "idFirstLesson", qualifiedByName = "toFirstLessonId")
    @Mapping(source = "category", target = "category", qualifiedByName = "toCategoryValue")
    CardCourseReponseClientDTO toCourseResponseClientDTO(ELCourse course);

    @Named("toCategoryValue")
    public static String toCategoryValue(CategoryEnum category) {
        return category.value();
    }
    
    @Named("toFirstLessonId")
    public static String toFirstLessonId(Collection<ELLesson> lessons) {
    	if(lessons.isEmpty()) {
    		return "";
    	}
    	ELLesson lesson = lessons.iterator().next();
        return lesson == null ? "" : lesson.getId().toString();
    }
}
