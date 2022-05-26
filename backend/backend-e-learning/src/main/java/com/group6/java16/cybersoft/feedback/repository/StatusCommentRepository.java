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
    boolean existsByUserAndCourse(@Param("idUser") UUID idUser, @Param("idCourse") UUID idCourse);

    @Query( value = "Select t from ELStatusComment t where t.user.id = :idUser and t.course.id = :idCourse")
    Optional<ELStatusComment> findByUserAndCourse(String idUser, String idCourse);

    @Query( value =  "Select t from ELStatusComment t where t.course.id = :idCourse and t.user.id = :idUser ")
    Optional<ELStatusComment>  findByIdCourseAndIdUser(@Param("idCourse") UUID idCourse, @Param("idUser") UUID idUser);

    @Query( value =  "Select s from ELStatusComment s where s.course.createdBy = :username and lower(s.user.displayName) = lower(concat('%', :displayName, '%')) ")
    Page<ELStatusComment> searchByDisplayNameUser(@Param("username") String usernameCurrent, @Param("displayName") String displayName, Pageable pageable);

    @Query( value =  "Select s from ELStatusComment s where s.course.createdBy = :username and lower(s.course.courseName) = lower(concat('%', :courseName, '%')) ")
    Page<ELStatusComment> searchByNameCourse(@Param("username") String usernameCurrent, @Param("courseName") String courseName, Pageable pageable);
    
    @Query( value =  "Select s from ELStatusComment s where id = :id")
    Optional<ELStatusComment> findByAndUserId(@Param("id") UUID id);
    

}
