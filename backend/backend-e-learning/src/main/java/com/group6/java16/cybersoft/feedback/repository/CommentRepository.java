package com.group6.java16.cybersoft.feedback.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<ELComment, UUID> {
    
}
