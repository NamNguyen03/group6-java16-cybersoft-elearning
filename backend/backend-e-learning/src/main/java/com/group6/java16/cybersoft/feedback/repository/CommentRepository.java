package com.group6.java16.cybersoft.feedback.repository;

import java.util.List;
import java.util.UUID;

import com.group6.java16.cybersoft.feedback.model.ELComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<ELComment, UUID> {
	 @Query( value =  " Select c from ELComment c "
	 		+ " join c.lesson l  join l.course x join x.statuses s "
	 		+ " where l.id = :id "
	 		+ " and s.status = 'PUBLIC' ") 
	 
	List<ELComment> findByStatusComment(@Param("id")UUID idLesson);
    
}
