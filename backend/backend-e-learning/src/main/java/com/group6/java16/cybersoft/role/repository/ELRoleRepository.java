package com.group6.java16.cybersoft.role.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.role.model.ELRole;
@Repository
public interface ELRoleRepository extends JpaRepository<ELRole, UUID> {

}
