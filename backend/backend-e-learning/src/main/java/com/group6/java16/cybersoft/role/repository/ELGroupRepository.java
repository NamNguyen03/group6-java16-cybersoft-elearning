package com.group6.java16.cybersoft.role.repository;

import java.util.UUID;

import com.group6.java16.cybersoft.role.model.ELGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ELGroupRepository extends JpaRepository<ELGroup, UUID> {
    @Query(value = "Select u from ELGroup u where lower(u.name) like lower(concat('%', :name,'%'))")
    Page<ELGroup> searchByName(@Param("name") String name, Pageable pageable);

    boolean existsByName(String name);

    Optional<ELGroup> findByName(String name);

}
