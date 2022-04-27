package com.group6.java16.cybersoft.role.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.role.model.ELRole;
@Repository
public interface ELRoleRepository extends JpaRepository<ELRole, UUID> {

	
	@Query( value =  "Select r from ELRole r where r.name like %:name% ")
	Page<ELRole> searchByName(@Param("name")String name, Pageable pageable);

	Optional<ELRole> findByName(String name);

	boolean existsByName(String name);

}
