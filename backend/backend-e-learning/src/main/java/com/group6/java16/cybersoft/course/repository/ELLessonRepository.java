package com.group6.java16.cybersoft.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.course.model.ELLesson;

@Repository
public interface ELLessonRepository extends JpaRepository<ELLesson, UUID> {

}
