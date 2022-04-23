package com.group6.java16.cybersoft.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.user.model.ELUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ELUserRepository extends JpaRepository<ELUser, UUID>  {

    Optional<ELUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query( value =  "Select u from ELUser u where u.username like %:username% ")
    Page<ELUser> searchByUsername(@Param("username") String username, Pageable pageable);

    @Query( value =  "Select u from ELUser u where u.displayName like %:displayName% ")
    Page<ELUser> searchByDisplayName(@Param("displayName") String displayName, Pageable pageable);

    @Query( value =  "Select u from ELUser u where u.email like %:email% ")
    Page<ELUser> searchByEmail(@Param("email") String email, Pageable pageable);

    @Query( value =  "Select u from ELUser u where u.firstName like %:firstName% ")
    Page<ELUser> searchByFirstName(@Param("firstName") String firstName, Pageable pageable);

    @Query( value =  "Select u from ELUser u where u.lastName like %:lastName% ")
    Page<ELUser> searchByLastName(@Param("lastName") String lastName, Pageable pageable);

}
