package com.group6.java16.cybersoft.course.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.course.model.ELCourse;

@Repository
public interface ELCourseRepository extends JpaRepository<ELCourse, UUID>  {
	
	Optional<ELCourse> findByCourseName(String cousrseName);
}
