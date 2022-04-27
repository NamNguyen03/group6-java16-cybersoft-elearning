package com.group6.java16.cybersoft.role.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.role.model.ELGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElGroupRepository extends JpaRepository<ELGroup, UUID> {
    
}
