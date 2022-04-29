package com.group6.java16.cybersoft.course.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.course.model.ELCourse;
import com.group6.java16.cybersoft.course.model.ELSession;

@Repository
public interface ELSessionRepository extends JpaRepository<ELSession,UUID>{
	
boolean existsBySessionName(String sessionName);
	
	Optional<ELSession> findBySessionName(String sessionName);

	@Query( value =  "Select u from ELSession u where u.sessionName like %:sessionName% ")
	Page<ELSession> searchBySessionName(@Param("sessionName")String sessionName, Pageable pageable);

	

}
