package com.group6.java16.cybersoft.role.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.group6.java16.cybersoft.role.model.ELProgram;

@Repository
public interface ELProgramRepository extends JpaRepository<ELProgram, UUID>{

	boolean existsByName(String name);

	@Query( value =  "Select u from ELProgram u where lower(u.name) like lower(concat('%', :name,'%'))")
	Page<ELProgram> searchByName(@Param("name") String name, Pageable pageable);

	@Query( value =  "Select u from ELProgram u where lower(u.module) like lower(concat('%', :module,'%'))")
	Page<ELProgram> searchByModule(@Param("module") String module, Pageable pageable);

	@Query( value =  "Select u from ELProgram u where lower(u.type) like lower(concat('%', :type,'%'))")
	Page<ELProgram> searchByType(@Param("type") String type, Pageable pageable);

    @Query( value = "Select count(p)>0 from ELProgram p left join p.roles r left join r.groups g left join g.users u where u.username = :username and p.name = :name and u.status = 'ACTIVE' ")
	boolean existsByNameProgramAndUsername(@Param("name") String name, @Param("username") String username);

}
