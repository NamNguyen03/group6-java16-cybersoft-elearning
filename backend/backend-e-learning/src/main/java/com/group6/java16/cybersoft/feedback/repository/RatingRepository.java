package com.group6.java16.cybersoft.feedback.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<ELRating, UUID> {
    
}
