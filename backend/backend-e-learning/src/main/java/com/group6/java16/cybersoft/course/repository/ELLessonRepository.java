package com.group6.java16.cybersoft.course.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.course.model.ELLesson;

@Repository
public interface ELLessonRepository extends JpaRepository<ELLesson, UUID> {
	
	boolean existsByName(String name);
	
	Optional<ELLesson> findByName(String name);

	@Query( value =  "Select u from ELLesson u where u.name like %:name% ")
	Page<ELLesson> searchByLessonName(@Param("name")String name, Pageable pageable);
}
