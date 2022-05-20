package com.group6.java16.cybersoft.feedback.repository;

import java.util.Optional;
import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELStatusComment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusCommentRepository extends JpaRepository<ELStatusComment, UUID>  {

    @Query( value =  "Select t from ELStatusComment t where t.course.createdBy = :username and lower(t.status) like lower(concat('%', :status,'%')) ")
    Page<ELStatusComment> searchByStatus(@Param("username") String usernameCurrent,@Param("status") String status, Pageable pageable);

    @Query( value =  "Select t from ELStatusComment t where t.course.createdBy = :username ")
    Page<ELStatusComment> searchAll(@Param("username") String usernameCurrent, Pageable pageable);

    @Query( value = "Select count(t)>0  from ELStatusComment t where t.user.id = :idUser and t.course.id = :idCourse")
    boolean existsByUserAndCourse(@Param("idUser") UUID idUser,@Param("idCourse") UUID idCourse);

    @Query( value = "Select t from ELStatusComment t where t.user.id = :idUser and t.course.id = :idCourse")
    Optional<ELStatusComment> findByUserAndCourse(String idUser, String idCourse);
    

}
