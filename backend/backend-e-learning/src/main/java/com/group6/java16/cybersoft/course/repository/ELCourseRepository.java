package com.group6.java16.cybersoft.course.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.course.model.CategoryEnum;
import com.group6.java16.cybersoft.course.model.ELCourse;

@Repository
public interface ELCourseRepository extends JpaRepository<ELCourse, UUID> {

	boolean existsByCourseName(String courseName);

	Optional<ELCourse> findByCourseName(String name);

	@Query(value = "Select u from ELCourse u where lower(u.courseName) like lower(concat('%', :courseName,'%')) and u.createdBy = :username ")
	Page<ELCourse> searchByCourseName(@Param("username")String username, @Param("courseName") String courseName, Pageable pageable);

	@Query(value = "Select u from ELCourse u where u.category = :category and u.createdBy = :username ")
	Page<ELCourse> findByCategory(@Param("username")String username, CategoryEnum category, Pageable pageable);

	@Query(value = "Select u from ELCourse u where u.createdBy = :username ")
    Page<ELCourse> searchAll(@Param("username")String username, Pageable pageable);

}
